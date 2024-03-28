package com.kouwik.repository;

import com.kouwik.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Annoter l'interface avec @Repository pour la détection automatique par Spring
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Ici, vous pouvez ajouter des méthodes personnalisées, par exemple :
    // List<Ticket> findByStatus(String status);

    // Mais même sans méthodes supplémentaires, vous avez accès à une gamme complète de méthodes CRUD :
    // - save(S entity)
    // - findById(ID id)
    // - findAll()
    // - delete(T entity)
    // - etc.
}
