package com.kouwik.service;

import com.kouwik.model.Ticket;
import com.kouwik.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(String content) {
        Ticket ticket = new Ticket();
        ticket.setContent(content);
        // Vous pouvez ajouter d'autres propriétés ici
        return ticketRepository.save(ticket);
    }

    // Autres méthodes utilisant ticketRepository...
}
