package com.mati.gamechat.dto.lolDto;

import com.mati.gamechat.Region;
import com.mati.gamechat.entity.LolStats;
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

    public LolStatsDTO lolStatsToLolStatsDTO(LolStats lolStats){
        return LolStatsDTO.builder()
                .nick(lolStats.getNick())
                .tier(lolStats.getTier())
                .division(lolStats.getDivision())
                .region(lolStats.getRegion())
                .lp(lolStats.getLp())
                .championNames(lolStats.getChampionNames()).build();
    }
}
