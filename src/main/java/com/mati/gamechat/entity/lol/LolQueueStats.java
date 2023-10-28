package com.mati.gamechat.entity.lol;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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