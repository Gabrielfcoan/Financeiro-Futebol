package com.futebol.repository;

import com.futebol.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByActiveTrueOrderByName();

    List<Player> findAllByOrderByNameAsc();
}
