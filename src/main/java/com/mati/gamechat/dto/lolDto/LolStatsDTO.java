package com.mati.gamechat.dto.lolDto;

import com.mati.gamechat.entity.lol.Region;
import com.mati.gamechat.entity.lol.LolQueueStats;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class LolStatsDTO {
    private String nick;
    private Region region;
    private String tier;
    private String division;
    private int lp;
    private List<String> championNames;

    public LolStatsDTO lolStatsToLolStatsDTO(LolQueueStats lolStats){
        return LolStatsDTO.builder()
                .tier(lolStats.getTier())
                .division(lolStats.getDivision())
                .lp(lolStats.getLp()).build();
    }
}
