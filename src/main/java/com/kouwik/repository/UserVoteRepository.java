package com.kouwik.repository;

import com.kouwik.model.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, Long> {

    // Trouver le premier vote d'un utilisateur pour un ticket spécifique
    UserVote findTopByUserIdAndTicketId(UUID userId, Long ticketId);

    // Trouver tous les votes d'un utilisateur pour un ticket spécifique
    List<UserVote> findByUserIdAndTicketId(UUID userId, Long ticketId);

    void deleteByTicketId(Long ticketId);
}
