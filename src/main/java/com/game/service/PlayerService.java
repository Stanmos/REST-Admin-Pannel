package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {

    List<Player> getPlayerList(
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
            Integer maxLevel);

    Player getPlayer(Long id);

    Integer getPlayerCount(
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
    );


    List<Player> sortPlayers(List<Player> players, PlayerOrder order);

    List<Player> getPage(List<Player> sortedPlayers, Integer pageNumber, Integer pageSize);

    Integer getLevel(Integer experience);

    Integer getUntilNextLevel(Integer experience, Integer level);


    Player createPlayer(Player player);

    boolean isPlayerValid(Player player);

    Player updatePlayer(Long id, Player player);

    boolean isValidId(Long id);

    void deletePlayer(Long id);

    boolean isEmptyPlayer(Player player);

    boolean isValidDate(Player player);

    boolean isValidExperience(Player player);
}
