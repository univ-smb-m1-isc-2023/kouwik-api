package com.kouwik.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;



@Entity // Cette annotation indique que c'est une entité JPA
public class Ticket {

    @Id // Cette annotation indique que le champ ci-dessous est la clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cette annotation indique que la clé primaire est générée automatiquement par la base de données.
    private Long id;

    private String content; // Un champ représentant le contenu du ticket
    private int votes; // Un champ représentant le nombre de votes
    private String title;
    private int columnId;
    // Constructeurs, getters et setters
    public Ticket() {}

    public Ticket(String content, Integer column) {
        this.content = content;
        this.votes = 0; // Initialiser les votes à 0 pour un nouveau ticket
        this.columnId = 1;
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
    public int getColumnId() {
        return columnId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }
    // Vous pouvez ajouter d'autres méthodes et champs selon vos besoins
}