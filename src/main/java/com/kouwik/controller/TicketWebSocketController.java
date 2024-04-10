package com.kouwik.controller;

import com.kouwik.service.TicketService;
import com.kouwik.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TicketWebSocketController {

    @Autowired
    private TicketService ticketService;

    @MessageMapping("/ticket/create")
    @SendTo("/topic/tickets")
    public Ticket createTicket(Ticket ticket) {
        return ticketService.createTicket(ticket.getContent(), ticket.getColumnId());
    }

    // Ajoutez d'autres méthodes pour les mises à jour, les suppressions, etc.
}
