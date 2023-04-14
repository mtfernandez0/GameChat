package com.mati.gamechat.service;

import com.mati.gamechat.entity.LolStats;
import com.mati.gamechat.repository.LolStatsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LolStatsServiceImpl implements LolStatsService{
    private final LolStatsRepository lolStatsRepository;

    public LolStatsServiceImpl(LolStatsRepository lolStatsRepository) {
        this.lolStatsRepository = lolStatsRepository;
    }

    @Override
    public Optional<LolStats> findByUserId(Long id) {
        return lolStatsRepository.findByUserId(id);
    }

    @Override
    public LolStats save(LolStats stats) {
        System.out.println("LOL STATS: " + stats);

        return lolStatsRepository.save(stats);
    }

    @Override
    public void deleteById(Long id) {
        lolStatsRepository.deleteById(id);
    }
}
