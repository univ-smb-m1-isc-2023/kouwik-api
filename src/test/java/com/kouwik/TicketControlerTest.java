package com.kouwik;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kouwik.controller.TicketController;
import com.kouwik.model.Ticket;
import com.kouwik.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTicket() {
        Ticket ticket = new Ticket();
        ticket.setContent("Example content");
        ticket.setColumnId(1);

        String boardId = "board-uuid-123";
        when(ticketService.createTicket(any(), anyInt(), anyString())).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.createTicket(ticket, boardId);

        assertEquals(CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ticket.getContent(), response.getBody().getContent());
    }

    @Test
    void testUpdateTicket_Success() {
        Long ticketId = 1L;
        Ticket existingTicket = new Ticket();
        existingTicket.setId(ticketId);
        existingTicket.setContent("Updated content");

        when(ticketService.updateTicket(eq(ticketId), anyString())).thenReturn(existingTicket);

        ResponseEntity<Ticket> response = ticketController.updateTicket(ticketId, existingTicket);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(existingTicket.getContent(), response.getBody().getContent());
    }



    @Test
    void testMoveTicket_Success() {
        Long ticketId = 1L;
        int newPosition = 2;
        when(ticketService.moveTicket(ticketId, newPosition)).thenReturn(true);

        ResponseEntity<Void> response = ticketController.moveTicket(ticketId, newPosition);

        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void testMoveTicket_NotFound() {
        Long ticketId = 1L;
        int newPosition = 2;
        when(ticketService.moveTicket(ticketId, newPosition)).thenReturn(false);

        ResponseEntity<Void> response = ticketController.moveTicket(ticketId, newPosition);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteTicket_Success() {
        Long ticketId = 1L;
        when(ticketService.deleteTicket(ticketId)).thenReturn(true);

        ResponseEntity<Void> response = ticketController.deleteTicket(ticketId);

        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteTicket_NotFound() {
        Long ticketId = 1L;
        when(ticketService.deleteTicket(ticketId)).thenReturn(false);

        ResponseEntity<Void> response = ticketController.deleteTicket(ticketId);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddOrRemoveVote_Success() {
        Long ticketId = 1L;
        UUID userId = UUID.randomUUID();
        boolean addVote = true;
        Ticket ticket = new Ticket();
        when(ticketService.addOrRemoveVote(ticketId, userId, addVote)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.addOrRemoveVote(ticketId, userId, addVote);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddOrRemoveVote_NotFound() {
        Long ticketId = 1L;
        UUID userId = UUID.randomUUID();
        boolean addVote = false;
        when(ticketService.addOrRemoveVote(ticketId, userId, addVote)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.addOrRemoveVote(ticketId, userId, addVote);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetTicketsByBoardUuid() {
        String boardUuid = "board-uuid-123";
        List<Ticket> tickets = List.of(new Ticket());
        when(ticketService.getTicketsByBoardUuid(boardUuid)).thenReturn(tickets);

        List<Ticket> response = ticketController.getTicketsByBoardUuid(boardUuid);

        assertEquals(1, response.size());
    }

    @Test
    void testGetTicketById_Success() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        when(ticketService.getTicketById(ticketId)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTicketById_NotFound() {
        Long ticketId = 1L;
        when(ticketService.getTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }
}
