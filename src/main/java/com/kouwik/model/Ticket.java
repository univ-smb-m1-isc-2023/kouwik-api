package com.kouwik.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content; // Contenu du ticket

    @Column(name = "votes")
    private int votes; // Nombre de votes pour le ticket

    @ManyToOne(fetch = FetchType.LAZY) // Plusieurs tickets peuvent appartenir à un seul tableau
    @JoinColumn(name = "board_id", nullable = false) // La colonne de clé étrangère dans la table Ticket
    @JsonIgnore
    private Board board; // Référence au tableau associé

    @Column(name = "column_id")
    private int columnId; // Identifiant de la colonne dans laquelle le ticket est placé

    // Constructeur par défaut requis par JPA
    public Ticket() {
    }

    // Constructeur utilisé pour créer un ticket avec toutes les propriétés
    // nécessaires
    public Ticket(String content, int columnId, Board board) {
        this.content = content;
        this.columnId = columnId;
        this.board = board;
        this.votes = 0; // Initialiser les votes à 0 pour un nouveau ticket
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }
}
