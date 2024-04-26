package com.kouwik;

import com.kouwik.model.Ticket;
import com.kouwik.model.UserVote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class UserVoteTest {
    private UserVote userVote;
    private UUID userId;
    private Long ticketId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        ticketId = 1L;
        userVote = new UserVote(userId, ticketId);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(userId, userVote.getUserId(), "Constructor should set userId correctly.");
        assertEquals(ticketId, userVote.getTicketId(), "Constructor should set ticketId correctly.");
    }

    @Test
    void testSetAndGetId() {
        Long id = 100L;
        userVote.setId(id);
        assertEquals(id, userVote.getId(), "setId should update the id correctly.");
    }

    @Test
    void testSetAndGetUserId() {
        UUID newUserId = UUID.randomUUID();
        userVote.setUserId(newUserId);
        assertEquals(newUserId, userVote.getUserId(), "setUserId should update the userId correctly.");
    }

    @Test
    void testSetAndGetTicketId() {
        Long newTicketId = 2L;
        userVote.setTicketId(newTicketId);
        assertEquals(newTicketId, userVote.getTicketId(), "setTicketId should update the ticketId correctly.");
    }

    @Test
    void testSetAndGetTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(3L);
        userVote.setTicket(ticket);
        assertEquals(ticket, userVote.getTicket(), "setTicket should correctly link the ticket.");
        assertEquals(ticket.getId(), userVote.getTicket().getId(), "The linked ticket should have the correct id.");
    }
}
