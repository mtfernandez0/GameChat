package com.mati.gamechat.service.lol;

import com.mati.gamechat.entity.lol.LolQueueStats;
import com.mati.gamechat.repository.LolQueueStatsRepository;
import org.springframework.stereotype.Service;

@Service
public class LolQueueStatsService {

    private final LolQueueStatsRepository queueStatsRepository;

    public LolQueueStatsService(LolQueueStatsRepository queueStatsRepository) {
        this.queueStatsRepository = queueStatsRepository;
    }

    public LolQueueStats save(LolQueueStats queueStats){
        return queueStatsRepository.save(queueStats);
    }

    public void deleteById(Long id){
        queueStatsRepository.deleteById(id);
    }
}
