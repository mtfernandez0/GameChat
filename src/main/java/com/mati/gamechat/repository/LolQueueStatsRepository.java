package com.mati.gamechat.repository;

import com.mati.gamechat.entity.lol.LolQueueStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LolQueueStatsRepository extends CrudRepository<LolQueueStats, Long> {
}
