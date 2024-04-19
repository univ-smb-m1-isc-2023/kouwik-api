package com.kouwik.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid = UUID.randomUUID().toString(); // UUID généré automatiquement.

    private String name; // Nom du tableau pour référence.

    // Relation bidirectionnelle avec la classe Ticket
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets; // Liste de tickets associés à ce tableau.

    public Board() {
        // Constructeur par défaut requis par JPA

    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    // Méthodes pour ajouter et supprimer des tickets (utiles pour gérer la relation bidirectionnelle)

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setBoard(this);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setBoard(null);
    }
}
