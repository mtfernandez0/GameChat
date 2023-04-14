package com.mati.gamechat.entity;

import com.mati.gamechat.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LolStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nick;
    @Enumerated(EnumType.STRING)
    private Region region;
    private String tier;
    private String division;
    private int lp;
    @ElementCollection
    private List<String> championNames;
    @OneToOne
    @JoinTable(name = "lol_user_stats",
            joinColumns = @JoinColumn(name = "lolStats_id"),
            inverseJoinColumns = @JoinColumn(name = "tb_user_id"))
    User user;
}