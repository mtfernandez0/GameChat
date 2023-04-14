package com.mati.gamechat.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("champion")
@Data @NoArgsConstructor @AllArgsConstructor
public class Champion implements Serializable {
    @Id
    private Long id;
    private String name;
}
