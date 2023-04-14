package com.mati.gamechat.service;

import com.mati.gamechat.entity.Champion;

import java.util.List;

public interface ChampionService {
    void save(Champion champion);
    void saveAll(List<Champion> champions);
    boolean existsAll(List<Long> ids);
    Champion findById(Long id);
    List<Champion> findAllById(List<Long> ids);
}
