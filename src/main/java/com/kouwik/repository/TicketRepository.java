package com.kouwik.repository;

import com.kouwik.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Annoter l'interface avec @Repository pour la détection automatique par Spring
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t JOIN t.board b WHERE b.uuid = :boardUuid")
    List<Ticket> findByBoardUuid(String boardUuid);
}
