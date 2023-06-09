package com.mati.gamechat.dto.lolDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MiniSeriesDTO {
    private int losses;
    private String progress;
    private int target;
    private int wins;
}
