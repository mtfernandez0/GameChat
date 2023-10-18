package com.mati.gamechat.entity.lol;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("champion")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Champion implements Serializable {
    @Id
    private Long id;
    private String name;
}
