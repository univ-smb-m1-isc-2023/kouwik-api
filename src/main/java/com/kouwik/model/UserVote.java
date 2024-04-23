package com.kouwik.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "UserVote")
public class UserVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false)
    private UUID userId;  // Utilisation de UUID pour l'identification de l'utilisateur

    @Column(name = "ticketId", nullable = false)
    private Long ticketId;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketId", insertable = false, updatable = false)
    private Ticket ticket;

    public UserVote() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public UserVote(UUID userId, Long ticketId) {
        this.userId = userId;
        this.ticketId = ticketId;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
