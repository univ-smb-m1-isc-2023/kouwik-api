package com.kouwik.service;
import com.kouwik.repository.TicketRepository;
import com.kouwik.model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final TicketRepository ticketRepository; // Déclarez TicketRepository

    // Injectez TicketRepository via le constructeur
    @Autowired
    public TicketService(SimpMessagingTemplate messagingTemplate, TicketRepository ticketRepository) {
        this.messagingTemplate = messagingTemplate;
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(String content, Integer column) {
        Ticket newTicket = new Ticket(content, column);
        Ticket savedTicket = ticketRepository.save(newTicket);
        // Envoie un message WebSocket à tous les clients connectés pour informer de la création du ticket
        messagingTemplate.convertAndSend("/topic/tickets", "New ticket created: " + savedTicket.getId());
        return savedTicket;
    }

    public Ticket voteForTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket != null) {
            ticket.setVotes(ticket.getVotes() + 1);
            return ticketRepository.save(ticket);
        }
        return null;
    }
    @Transactional
    public boolean deleteTicket(Long ticketId) {
        try {
            ticketRepository.deleteById(ticketId);

            messagingTemplate.convertAndSend("/topic/tickets", "Ticket deleted: " + ticketId);
            return true; // Indique que la suppression a réussi
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indique que la suppression a échoué
        }
    }

    public boolean moveTicket(Long ticketId, int newColumnId) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setColumnId(newColumnId);
            ticketRepository.save(ticket);
            messagingTemplate.convertAndSend("/topic/tickets", "Ticket moved: " + ticketId);
            return true; // Indique que le déplacement a réussi
        } else {
            return false; // Indique que le ticket n'a pas été trouvé
        }
    }
    public boolean updateTicketTitle(Long id, String newTitle) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket != null) {
            ticket.setTitle(newTitle);
            ticketRepository.save(ticket);
            return true;
        }
        return false;
    }
    public Ticket updateTicket(Long ticketId, String newContent) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setContent(newContent);
            Ticket updatedTicket = ticketRepository.save(ticket);
            messagingTemplate.convertAndSend("/topic/tickets", "Ticket updated: " + ticketId);
            return updatedTicket; // Renvoie le ticket mis à jour
        } else {
            return null; // Renvoie null si le ticket n'est pas trouvé
        }
    }

    public Ticket getTicketById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
