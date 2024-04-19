package com.kouwik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kouwik.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUuid(String uuid);
}
