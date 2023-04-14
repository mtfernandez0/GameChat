package com.mati.gamechat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mati.gamechat.Region;
import com.mati.gamechat.dto.lolDto.LeagueEntryDTO;
import com.mati.gamechat.dto.lolDto.LolStatsDTO;
import com.mati.gamechat.dto.lolDto.SummonerDTO;
import com.mati.gamechat.entity.Friend;
import com.mati.gamechat.entity.LolStats;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.service.LolStatsService;
import com.mati.gamechat.service.UserService;
import com.mati.gamechat.service.WebClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final WebClientService webClientService;
    private final LolStatsService lolStatsService;

    public UserController(UserService userService,
                          WebClientService webClientService,
                          LolStatsService lolStatsService) {
        this.userService = userService;
        this.webClientService = webClientService;
        this.lolStatsService = lolStatsService;
    }

    @GetMapping("/")
    public String mainPage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username.equals("anonymousUser")){
            model.addAttribute("authenticated", false);

        } else {
            model.addAttribute("authenticated", true);
            model.addAttribute("username", username);
            model.addAttribute("user", userService.findByUsername(username));
        }
        return "index";
    }

    @GetMapping("/account")
    public String account(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username.equals("anonymousUser")){
            model.addAttribute("authenticated", false);
        } else {
            User user = userService.findByUsername(username).get();
            model.addAttribute("authenticated", true);
            model.addAttribute("user", user);
            if (lolStatsService.findByUserId(user.getId()).isPresent()){
                model.addAttribute("lolProfileExists", true);
                model.addAttribute("lolProfile", lolStatsService.findByUserId(user.getId()).get());
            }
        }
        return "account";
    }

    @PostMapping("/saveLolProfile")
    public ResponseEntity<?> saveLolProfile(@RequestParam("nickname") String nickname,
                                         @RequestParam("region") Region region) throws JsonProcessingException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User userDB = userService.findByUsername(username).get();

        if (lolStatsService.findByUserId(userDB.getId()).isPresent())
            return ResponseEntity.badRequest().build();

        SummonerDTO summonerDTO;
        Set<LeagueEntryDTO> leagueEntryDTOSet;

        try {
            summonerDTO = webClientService.getMonoSummoner(nickname, region);
            leagueEntryDTOSet = webClientService.getLeagueEntryMonoSet(Objects.requireNonNull(summonerDTO).getId(), region);
        } catch (HttpServerErrorException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        LeagueEntryDTO leagueEntryDTO = null;

        for (LeagueEntryDTO l: Objects.requireNonNull(leagueEntryDTOSet)) {
            if (l.getQueueType().equals("RANKED_SOLO_5x5"))
                leagueEntryDTO = l;
        }

        LolStats lolStats = leagueEntryDTO.leagueEntryDTOtoLolStats(userDB, region);

        lolStats.setChampionNames(webClientService.getTopChampionNames(summonerDTO.getId(), region));

        lolStatsService.save(lolStats);

        return ResponseEntity.ok().body(new LolStatsDTO().lolStatsToLolStatsDTO(lolStats));
    }

    @PutMapping("/editLolProfile")
    public ResponseEntity<?> editLolProfile(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/registration/{userId}")
    public ResponseEntity<Void> registrationUser(@PathVariable Long userId) throws Exception{
        userService.findById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat")
    public String chat(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username.equals("anonymousUser")){
            model.addAttribute("authenticated", false);
        } else {
            model.addAttribute("authenticated", true);
            model.addAttribute("username", username);
        }

        return "chat";
    }

    @GetMapping("/fetchAll")
    @ResponseBody
    public List<Long> fetchAll(){
        List<Long> ids = new ArrayList<>();
        List<User> users = userService.findAll();
        for (User u: users)
            ids.add(u.getId());

        return ids;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user){
        userService.save(user);
        return "chat";
    }

    @PostMapping("/sendFriendRequest")
    @ResponseBody
    public String sendFriendRequest(@ModelAttribute Friend friend) throws Exception {
//        User userDB = userService.findByNameAndRegion(friend.getName(), friend.getRegion());

//        System.out.println(userDB);

        return "success";
    }
}
