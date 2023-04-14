package com.mati.gamechat.repository;

import com.mati.gamechat.entity.Champion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisChampionRepository extends CrudRepository<Champion, Long> {
}
