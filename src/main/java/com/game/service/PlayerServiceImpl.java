package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception_handling.NoSuchPlayerException;
import com.game.exception_handling.NoValidIDException;
import com.game.exception_handling.NoValidPlayerException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    public PlayerServiceImpl() {

    }

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        super();
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getPlayerList(
            String name,
            String title,
            Race race,
            Profession profession,
            Long before,
            Long after,
            Boolean banned,
            Integer minExperience,
            Integer maxExperience,
            Integer minLevel,
            Integer maxLevel) {
        final Date afterDate = after == null ? null : new Date(after);
        final Date beforeDate = before == null ? null : new Date(before);
        final List<Player> list = new ArrayList<>();
        playerRepository.findAll().forEach((player) -> {
            if (name != null && !player.getName().contains(name)) return;
            if (title != null && !player.getTitle().contains(title)) return;
            if (race != null && player.getRace() != race) return;
            if (profession != null && player.getProfession() != profession) return;
            if (afterDate != null && player.getBirthday().before(afterDate)) return;
            if (beforeDate != null && player.getBirthday().after(beforeDate)) return;
            if (banned != null && player.getBanned().booleanValue() != banned.booleanValue()) return;
            if (minExperience != null && player.getExperience().compareTo(minExperience) < 0) return;
            if (maxExperience != null && player.getExperience().compareTo(maxExperience) > 0) return;
            if (minLevel != null && player.getLevel().compareTo(minLevel) < 0) return;
            if (maxLevel != null && player.getLevel().compareTo(maxLevel) > 0) return;

            list.add(player);
        });

        return list;
    }

    @Override
    public Player getPlayer(Long id) {
        if (!isValidId(id)) return null;
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null) {
            throw new NoSuchPlayerException("There is no player with ID = " +
                    id + " in Database");
        }
        return player;

    }

    @Override
    public Integer getPlayerCount(
            String name,
            String title,
            Race race,
            Profession profession,
            Long before,
            Long after,
            Boolean banned,
            Integer minExperience,
            Integer maxExperience,
            Integer minLevel,
            Integer maxLevel
    ) {
        return getPlayerList(name, title, race, profession,
                before, after, banned, minExperience, maxExperience, minLevel, maxLevel
        ).size();
    }

    @Override
    public List<Player> sortPlayers(List<Player> players, PlayerOrder order) {
        if (order != null) {
            players.sort((player1, player2) -> {
                switch (order) {
                    case NAME:
                        return player1.getName().compareTo(player2.getName());
                    case EXPERIENCE:
                        return player1.getExperience().compareTo(player2.getExperience());
                    case BIRTHDAY:
                        return player1.getBirthday().compareTo(player2.getBirthday());
                    case LEVEL:
                        return player1.getLevel().compareTo(player2.getLevel());

                    default:
                        return player1.getId().compareTo(player2.getId());
                }
            });
        }
        return players;
    }

    @Override
    public List<Player> getPage(List<Player> sortedPlayers, Integer pageNumber, Integer pageSize) {
        final Integer page = pageNumber == null ? 0 : pageNumber;
        final Integer size = pageSize == null ? 3 : pageSize;
        final int from = page * size;
        int to = from + size;
        if (to > sortedPlayers.size()) to = sortedPlayers.size();
        return sortedPlayers.subList(from, to);
    }

    @Override
    public Integer getLevel(Integer experience) {
        Integer level = ((int) (Math.sqrt(2500 + 200 * experience)) - 50) / 100;
        return level;
    }

    @Override
    public Integer getUntilNextLevel(Integer experience, Integer level) {
        return 50 * (level + 1) * (level + 2) - experience;
    }

    @Override
    public Player createPlayer(Player player) {
        if (!isPlayerValid(player)) return null;

        Player createdPlayer = new Player();
        createdPlayer.setName(player.getName());
        createdPlayer.setTitle(player.getTitle());
        createdPlayer.setRace(player.getRace());
        createdPlayer.setProfession(player.getProfession());
        createdPlayer.setBirthday(player.getBirthday());

        if (player.getBanned() == null) {
            createdPlayer.setBanned(false);
        } else {
            createdPlayer.setBanned(player.getBanned());
        }

        createdPlayer.setExperience(player.getExperience());
        createdPlayer.setLevel(getLevel(createdPlayer.getExperience()));
        createdPlayer.setUntilNextLevel(getUntilNextLevel(createdPlayer.getExperience(), createdPlayer.getLevel()));

        playerRepository.save(createdPlayer);
        return createdPlayer;
    }

    @Override
    public boolean isPlayerValid(Player player) {
        if (player.getName() == null
                || player.getTitle() == null
                || player.getRace() == null
                || player.getProfession() == null
                || player.getBirthday() == null
                || player.getExperience() == null
                || player.getName().equals("")
                || player.getTitle().equals("")
                || player.getName().length() > 12
                || player.getTitle().length() > 30
                || player.getExperience() < 0
                || player.getExperience() > 10_000_000
        ) {
            throw new NoValidPlayerException("Not valid data");
        }
        isValidDate(player);

        return true;
    }

    @Override
    public boolean isValidDate(Player player) {
        Date dateBegin;
        Date dateEnd;
        try {
            dateBegin = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
            dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.3001");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (player.getBirthday().getTime() < 0
                || player.getBirthday().before(dateBegin)
                || player.getBirthday().getTime() >= dateEnd.getTime()
        ) {
            throw new NoValidPlayerException("Not valid data");
        }
        return true;
    }

    @Override
    public boolean isValidExperience(Player player) {
        if (player.getExperience() < 0 || player.getExperience() > 10_000_000) throw new NoValidPlayerException(
                "No such experience"
        );
        return true;
    }

    @Override
    public Player updatePlayer(Long id, Player player) {
        if (!isValidId(id)) return null;

        if (!isEmptyPlayer(player)) {
            if (player.getExperience()!=null){
                isValidExperience(player);
            }
            if (player.getBirthday() != null) {
                isValidDate(player);
            }
        }

        Player updatedPlayer = getPlayer(id);

        if (player.getName() != null) updatedPlayer.setName(player.getName());
        if (player.getTitle() != null) updatedPlayer.setTitle(player.getTitle());
        if (player.getRace() != null) updatedPlayer.setRace(player.getRace());
        if (player.getProfession() != null) updatedPlayer.setProfession(player.getProfession());
        if (player.getBirthday() != null) updatedPlayer.setBirthday(player.getBirthday());
        if (player.getBanned() != null) updatedPlayer.setBanned(player.getBanned());
        if (player.getExperience() != null) {
            updatedPlayer.setExperience(player.getExperience());
            updatedPlayer.setLevel(getLevel(updatedPlayer.getExperience()));
            updatedPlayer.setUntilNextLevel(getUntilNextLevel(updatedPlayer.getExperience(), updatedPlayer.getLevel()));
        }

        playerRepository.save(updatedPlayer);
        return updatedPlayer;
    }

    @Override
    public boolean isEmptyPlayer(Player player) {
        if (player.getName() == null
                && player.getTitle() == null
                && player.getRace() == null
                && player.getProfession() == null
                && player.getBirthday() == null
                && player.getBanned() == null
                && player.getExperience() == null
        )
            return true;
        else return false;
    }


    @Override
    public boolean isValidId(Long id) {
        if (id instanceof Number && (id % 1 != 0 || id <= 0)) {
            throw new NoValidIDException("Not valid id");
        }
        return true;
    }

    @Override
    public void deletePlayer(Long id) {
        if (!isValidId(id)) return;
        if (!playerRepository.existsById(id)) {
            throw new NoSuchPlayerException("There is no player with ID = " +
                    id + " in Database");
        } else {
            playerRepository.deleteById(id);
        }

    }
}
