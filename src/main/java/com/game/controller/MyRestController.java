package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getPlayerList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false) PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        List<Player> players = playerService.getPlayerList(name, title, race, profession, before, after
            , banned, minExperience, maxExperience, minLevel, maxLevel);

        List<Player> sortedPlayers = playerService.sortPlayers(players, order);

        return playerService.getPage(sortedPlayers, pageNumber, pageSize);
    }

    @PostMapping("/players/{id}")
    @ResponseBody
    public Player upDatePlayer(
            @PathVariable Long id,
            @RequestBody Player player
    ){
        Player updatedPlayer = playerService.updatePlayer(id, player);
        return updatedPlayer;
    }


    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable Long id){
        Player player = playerService.getPlayer(id);
        return player;
    }

    @GetMapping("/players/count")
    public Integer getPlayerCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false) PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        return playerService.getPlayerCount(name, title, race, profession, before, after
                , banned, minExperience, maxExperience, minLevel, maxLevel);
    }

    @PostMapping("/players")
    @ResponseBody
    public Player createPlayer(@RequestBody Player player){
        Player createdPlayer = playerService.createPlayer(player);
        return createdPlayer;
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id){
        playerService.deletePlayer(id);
    }

}
