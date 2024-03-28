package com.kouwik.service;
import com.kouwik.repository.TicketRepository;
import com.kouwik.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
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

    public Ticket createTicket(String content) {
        Ticket newTicket = new Ticket(content);
        // Sauvegarde le ticket dans la base de données...
        // Envoie un message WebSocket à tous les clients connectés pour informer de la création du ticket
        messagingTemplate.convertAndSend("/topic/tickets", "New ticket created: " + newTicket.getId());
        return newTicket;
    }

    public Ticket voteForTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket != null) {
            ticket.setVotes(ticket.getVotes() + 1);
            return ticketRepository.save(ticket);
        }
        return null;
    }

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
}
