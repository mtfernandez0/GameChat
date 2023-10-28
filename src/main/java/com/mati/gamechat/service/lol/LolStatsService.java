package com.mati.gamechat.service.lol;

import com.mati.gamechat.entity.lol.LolStats;
import com.mati.gamechat.repository.LolStatsRepository;
import org.springframework.stereotype.Service;

@Service
public class LolStatsService {
    private final LolStatsRepository lolStatsRepository;

    public LolStatsService(LolStatsRepository lolStatsRepository) {
        this.lolStatsRepository = lolStatsRepository;
    }

    public LolStats findByUserId(Long id) {
        return lolStatsRepository.findByUserId(id);
    }

    public LolStats save(LolStats stats) {
        return lolStatsRepository.save(stats);
    }

    public void deleteById(Long id) {
        lolStatsRepository.deleteById(id);
    }
}
