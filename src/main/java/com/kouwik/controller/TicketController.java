package com.kouwik.controller;

import com.kouwik.model.Ticket;
import com.kouwik.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket, @RequestParam("boardId") String boardId) {
        // La méthode du service attend maintenant aussi un boardId sous forme de chaîne
        Ticket createdTicket = ticketService.createTicket(ticket.getContent(), ticket.getColumnId(), boardId);
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
    @PutMapping("/{id}/title")
    public ResponseEntity<Void> updateTicketTitle(@PathVariable Long id, @RequestBody String newTitle) {
        boolean updated = ticketService.updateTicketTitle(id, newTitle);
        if (updated) {
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

    @GetMapping("/tickets")
    public List<Ticket> getTicketsByBoardUuid(@RequestParam String boardUuid) {
        return ticketService.getTicketsByBoardUuid(boardUuid);
    }



    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}