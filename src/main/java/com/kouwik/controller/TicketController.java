package com.kouwik.controller;

import com.kouwik.model.Ticket;
import com.kouwik.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticket.getContent());
        if (updatedTicket != null) {
            return ResponseEntity.ok(updatedTicket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/tickets/{id}/move")
    public ResponseEntity<Void> moveTicket(@PathVariable Long id, @RequestParam("newPosition") int newPosition) {
        boolean moved = ticketService.moveTicket(id, newPosition);
        if (moved) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        boolean deleted = ticketService.deleteTicket(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/vote")
    public ResponseEntity<Ticket> voteForTicket(@PathVariable Long id) {
        Ticket votedTicket = ticketService.voteForTicket(id);
        if (votedTicket != null) {
            return ResponseEntity.ok(votedTicket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}