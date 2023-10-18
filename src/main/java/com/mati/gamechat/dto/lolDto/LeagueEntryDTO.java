package com.mati.gamechat.dto.lolDto;

import com.mati.gamechat.entity.lol.LolQueueStats;
import com.mati.gamechat.entity.User;
import com.mati.gamechat.entity.lol.QueueType;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class LeagueEntryDTO {
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    public LolQueueStats leagueEntryDTOtoLolStats(User user, QueueType queueType){
        return LolQueueStats.builder()
                .queueType(queueType)
                .tier(tier)
                .division(rank)
                .lp(leaguePoints)
                .build();
    }
}
