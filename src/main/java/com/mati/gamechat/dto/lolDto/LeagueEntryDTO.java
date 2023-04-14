package com.mati.gamechat.dto.lolDto;

import com.mati.gamechat.Region;
import com.mati.gamechat.entity.LolStats;
import com.mati.gamechat.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LeagueEntryDTO {
    private String summonerName;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    public LolStats leagueEntryDTOtoLolStats(User user, Region region){
        return LolStats.builder()
                .nick(summonerName)
                .tier(tier)
                .division(rank)
                .region(region)
                .lp(leaguePoints)
                .user(user).build();
    }
}
