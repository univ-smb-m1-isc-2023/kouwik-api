package com.kouwik.service;

import com.kouwik.model.Board;
import com.kouwik.model.UserVote;
import com.kouwik.repository.BoardRepository;
import com.kouwik.repository.TicketRepository;
import com.kouwik.model.Ticket;
import com.kouwik.repository.UserVoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository; // Déclarez TicketRepository
    private final BoardRepository boardRepository;

    private final UserVoteRepository userVoteRepository;

    // Injectez TicketRepository via le constructeur
    @Autowired
    public TicketService(TicketRepository ticketRepository, BoardRepository boardRepository, UserVoteRepository userVoteRepository) {
        this.ticketRepository = ticketRepository;
        this.boardRepository = boardRepository;
        this.userVoteRepository = userVoteRepository;
    }

    public Ticket createTicket(String content, Integer columnId, String boardUuid) {
        // Retrieve the Board using the boardUuid
        Board board = boardRepository.findByUuid(boardUuid);

        // Create a new Ticket with the provided content, column, and the retrieved
        // Board
        Ticket newTicket = new Ticket();
        newTicket.setContent(content);
        newTicket.setColumnId(columnId);
        newTicket.setBoard(board); // Set the associated Board
        return ticketRepository.save(newTicket);
    }

    public Ticket addOrRemoveVote(Long ticketId, UUID userId, boolean addVote) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (!ticketOptional.isPresent()) {
            return null;  // Ticket not found
        }
        Ticket ticket = ticketOptional.get();

        if (addVote) {
            // Ajoute toujours un nouveau vote
            UserVote userVote = new UserVote(userId, ticketId);
            userVoteRepository.save(userVote);
            ticket.setVotes(ticket.getVotes() + 1);
        } else {
            // Tente de retirer un vote existant pour cet utilisateur et ce ticket
            UserVote userVote = userVoteRepository.findTopByUserIdAndTicketId(userId, ticketId);
            if (userVote != null) {
                userVoteRepository.delete(userVote);
                ticket.setVotes(ticket.getVotes() - 1);
            } else {
                return null;  // User has not voted on this ticket, cannot remove a vote
            }
        }

        ticketRepository.save(ticket);
        return ticket;
    }


    @Transactional
    public boolean deleteTicket(Long ticketId) {
        try {
            ticketRepository.deleteById(ticketId);
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
            return true; // Indique que le déplacement a réussi
        } else {
            return false; // Indique que le ticket n'a pas été trouvé
        }
    }

    public Ticket updateTicket(Long ticketId, String newContent) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setContent(newContent);
            Ticket updatedTicket = ticketRepository.save(ticket);
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

    public List<Ticket> getTicketsByBoardUuid(String boardUuid) {
        return ticketRepository.findByBoardUuid(boardUuid);
    }

}
