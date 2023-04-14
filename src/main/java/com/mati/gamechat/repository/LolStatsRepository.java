package com.mati.gamechat.repository;

import com.mati.gamechat.entity.LolStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LolStatsRepository extends JpaRepository<LolStats, Long> {
    Optional<LolStats> findByUserId(Long id);
}
