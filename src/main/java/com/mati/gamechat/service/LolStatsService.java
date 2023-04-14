package com.mati.gamechat.service;

import com.mati.gamechat.entity.LolStats;

import java.util.Optional;

public interface LolStatsService {
    Optional<LolStats> findByUserId(Long id);

    LolStats save(LolStats stats);

    void deleteById(Long id);
}
