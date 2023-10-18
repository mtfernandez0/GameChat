package com.mati.gamechat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mati.gamechat.entity.lol.LolStats;
import com.mati.gamechat.entity.lol.QueueType;
import com.mati.gamechat.entity.lol.Region;
import com.mati.gamechat.dto.lolDto.LeagueEntryDTO;
import com.mati.gamechat.dto.lolDto.SummonerDTO;
import com.mati.gamechat.entity.lol.LolQueueStats;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.service.LolQueueStatsService;
import com.mati.gamechat.service.LolStatsService;
import com.mati.gamechat.service.UserService;
import com.mati.gamechat.service.WebClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Log4j2
public class AccountController {

    private final WebClientService webClientService;
    private final LolStatsService lolStatsService;
    private final LolQueueStatsService lolQueueStatsService;
    private final UserService userService;

    public AccountController(WebClientService webClientService,
                             LolStatsService lolStatsService,
                             LolQueueStatsService lolQueueStatsService,
                             UserService userService) {
        this.webClientService = webClientService;
        this.lolStatsService = lolStatsService;
        this.lolQueueStatsService = lolQueueStatsService;
        this.userService = userService;
    }

    private long getDifference(Date from, Date to){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        long res = 0;
        try{
            long diff = sdf.parse(to.toString()).getTime() - sdf.parse(from.toString()).getTime();
            res = (diff / (1000 * 60 * 60 * 24)) % 365;
        } catch (Exception ignore) {}

        return res;
    }

    private void updateProfile(User user,
                               String nickname,
                               Region region) throws JsonProcessingException{

        SummonerDTO summonerDTO;
        Set<LeagueEntryDTO> leagueEntryDTOSet = null;
        LolStats lolStats = new LolStats();

        try {
            summonerDTO = webClientService.getMonoSummoner(nickname, region);
            lolStats = LolStats.builder()
                    .nick(nickname)
                    .region(region)
                    .user(user)
                    .championNames(webClientService.getTopChampionNames(summonerDTO.getId(), region))
                    .build();
            leagueEntryDTOSet = webClientService.getLeagueEntryMonoSet(summonerDTO.getId(), region);
        } catch (HttpServerErrorException ignore){}

        if (user.getLolStats() != null && user.getLolStats().getLolQueueStats() != null)
            for (LolQueueStats lolQueueStats : user.getLolStats().getLolQueueStats())
                lolQueueStatsService.deleteById(lolQueueStats.getId());

        lolStats.setLolQueueStats(new ArrayList<>());

        if (user.getLolStats() != null) lolStats.setId(user.getLolStats().getId());

        lolStats = lolStatsService.save(lolStats);

        for (LeagueEntryDTO l: Objects.requireNonNull(leagueEntryDTOSet)) {
            try {
                QueueType queueType = QueueType.findCorrectOne(l.getQueueType());
                LolQueueStats lolQueueStats = l.leagueEntryDTOtoLolStats(user, queueType);
                lolQueueStats.setLolStats(lolStats);
                lolQueueStatsService.save(lolQueueStats);
            } catch (IllegalArgumentException ignored){}
        }

        user.setLolStats(lolStatsService.save(lolStats));
        userService.save(user);
    }

    @GetMapping("/account")
    public String account(Model model,
                          Principal principal) throws JsonProcessingException {

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        if (user.getLolStats() != null && getDifference(user.getUpdated_at(), new Date()) > 0)
            updateProfile(user, user.getLolStats().getNick(), user.getLolStats().getRegion());

        return "account";
    }

    @PostMapping("/saveLolProfile")
    public ResponseEntity<?> saveLolProfile(@RequestParam("nickname") String nickname,
                                            @RequestParam("region") Region region,
                                            Principal principal) throws JsonProcessingException {
        User user = userService.findByUsername(principal.getName());

        if (Objects.nonNull(user.getLolStats()))
            return ResponseEntity.badRequest().build();

        updateProfile(user, nickname, region);

        return ResponseEntity.ok().build();
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
}
