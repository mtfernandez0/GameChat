package com.mati.gamechat.service;

import com.mati.gamechat.entity.Champion;
import com.mati.gamechat.repository.RedisChampionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionServiceImpl implements ChampionService{
    private final RedisChampionRepository redisChampionRepository;

    public ChampionServiceImpl( RedisChampionRepository redisChampionRepository) {
        this.redisChampionRepository = redisChampionRepository;
    }

    @Override
    public void save(Champion champion) {
        redisChampionRepository.save(champion);
    }

    @Override
    public void saveAll(List<Champion> champions){
        redisChampionRepository.saveAll(champions);
    }

    @Override
    public boolean existsAll(List<Long> ids){
        boolean res = true;

        for (Long id : ids)
            res &= redisChampionRepository.existsById(id);

        return res;
    }

    @Override
    public Champion findById(Long id) {
        return redisChampionRepository.findById(id).orElseThrow(() -> new RuntimeException("Bad"));
    }

    @Override
    public List<Champion> findAllById(List<Long> ids) {
        return (List<Champion>) redisChampionRepository.findAllById(ids);
    }
}
