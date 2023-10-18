package com.mati.gamechat.entity.lol;

import com.mati.gamechat.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lol_stats")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Builder
public class LolStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nick;

    @Enumerated(EnumType.STRING)
    private Region region;
    @ElementCollection
    private List<String> championNames;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "lolStats")
    private List<LolQueueStats> lolQueueStats;

    @Column(nullable = false)
    private Date created_at;

    private Date updated_at;

    @PrePersist
    private void onCreate(){
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    @PreUpdate
    private void onUpdate(){
        this.updated_at = new Date();
    }
}
