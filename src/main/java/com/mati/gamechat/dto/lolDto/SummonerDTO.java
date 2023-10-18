package com.mati.gamechat.dto.lolDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SummonerDTO {
    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private int profileIconId;
}
