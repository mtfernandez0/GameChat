package com.mati.gamechat.entity.lol;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lol_queue_stats")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Builder
public class LolQueueStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = QueueTypeConverter.class)
    @Enumerated(EnumType.STRING)
    private QueueType queueType;

    private String tier;

    private String division;

    private Integer lp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lol_stats_id")
    private LolStats lolStats;
}