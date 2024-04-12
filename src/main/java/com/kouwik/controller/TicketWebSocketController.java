package com.kouwik.controller;

import com.kouwik.service.TicketService;
import com.kouwik.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TicketWebSocketController {

    @Autowired
    private TicketService ticketService;

    @MessageMapping("/ticket/create")
    @SendTo("/topic/tickets")
    public Ticket createTicket(Ticket ticket) {
        return ticketService.createTicket(ticket.getContent(), ticket.getColumnId());
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/trigger-websocket")
    public ResponseEntity<String> triggerWebsocket() {
        messagingTemplate.convertAndSend("/topic/message", "ok");
        return ResponseEntity.ok("Triggered");
    }
    // Ajoutez d'autres méthodes pour les mises à jour, les suppressions, etc.
}
