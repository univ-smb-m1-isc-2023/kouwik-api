package com.kouwik.service;

import com.kouwik.model.Board;
import com.kouwik.model.Ticket;
import com.kouwik.model.UserVote;
import com.kouwik.repository.BoardRepository;
import com.kouwik.repository.TicketRepository;
import com.kouwik.repository.UserVoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserVoteRepository userVoteRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private Board board;
    private UUID userId;
    private Long ticketId = 1L;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        board = new Board();
        board.setId(1L);
        board.setUuid("board-uuid");

        ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setColumnId(1);
        ticket.setContent("Content");
        ticket.setBoard(board);
    }

    @Test
    void testCreateTicket() {
        String content = "Test Content";
        Integer columnId = 1;
        String boardUuid = "test-uuid";
        Board mockBoard = new Board();
        Ticket expectedTicket = new Ticket();
        expectedTicket.setContent(content);
        expectedTicket.setColumnId(columnId);
        expectedTicket.setBoard(mockBoard);

        when(boardRepository.findByUuid(boardUuid)).thenReturn(mockBoard);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket actualTicket = ticketService.createTicket(content, columnId, boardUuid);

        assertNotNull(actualTicket);
        assertEquals(content, actualTicket.getContent());
        assertEquals(columnId, actualTicket.getColumnId());
        assertEquals(mockBoard, actualTicket.getBoard());
    }

    @Test
    void testAddVote() {
        UUID userId = UUID.randomUUID();
        Long ticketId = 1L;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(ticketId);
        mockTicket.setVotes(0);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        when(userVoteRepository.save(any(UserVote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updatedTicket = ticketService.addOrRemoveVote(ticketId, userId, true);

        assertNotNull(updatedTicket);
        assertEquals(1, updatedTicket.getVotes());
        verify(userVoteRepository).save(any(UserVote.class));
        verify(ticketRepository).save(mockTicket);
    }

    @Test
    void testRemoveVote() {
        UUID userId = UUID.randomUUID();
        Long ticketId = 1L;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(ticketId);
        mockTicket.setVotes(1);

        UserVote userVote = new UserVote(userId, ticketId);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        when(userVoteRepository.findTopByUserIdAndTicketId(userId, ticketId)).thenReturn(userVote);

        Ticket updatedTicket = ticketService.addOrRemoveVote(ticketId, userId, false);

        assertNotNull(updatedTicket);
        assertEquals(0, updatedTicket.getVotes());
        verify(userVoteRepository).delete(userVote);
        verify(ticketRepository).save(mockTicket);
    }

    @Test
    void testRemoveVote_NoPreviousVote() {
        UUID userId = UUID.randomUUID();
        Long ticketId = 1L;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(ticketId);
        mockTicket.setVotes(1);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        when(userVoteRepository.findTopByUserIdAndTicketId(userId, ticketId)).thenReturn(null);

        Ticket updatedTicket = ticketService.addOrRemoveVote(ticketId, userId, false);

        assertNull(updatedTicket);
    }


    @Test
    void testDeleteTicket() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).deleteById(ticketId);

        boolean deleted = ticketService.deleteTicket(ticketId);

        assertTrue(deleted);
        verify(ticketRepository).deleteById(ticketId);
    }

    @Test
    void testMoveTicket_Success() {
        Long ticketId = 1L;
        int newColumnId = 2;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(ticketId);
        mockTicket.setColumnId(1);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        when(ticketRepository.save(mockTicket)).thenReturn(mockTicket);

        boolean result = ticketService.moveTicket(ticketId, newColumnId);

        assertTrue(result);
        assertEquals(newColumnId, mockTicket.getColumnId());
        verify(ticketRepository).save(mockTicket);
    }

    @Test
    void testMoveTicket_NotFound() {
        Long ticketId = 1L;
        int newColumnId = 2;

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        boolean result = ticketService.moveTicket(ticketId, newColumnId);

        assertFalse(result);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void testUpdateTicket_Success() {
        Long ticketId = 1L;
        String newContent = "Updated Content";
        Ticket mockTicket = new Ticket();
        mockTicket.setId(ticketId);
        mockTicket.setContent("Original Content");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        when(ticketRepository.save(mockTicket)).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updatedTicket = ticketService.updateTicket(ticketId, newContent);

        assertNotNull(updatedTicket);
        assertEquals(newContent, updatedTicket.getContent());
        verify(ticketRepository).save(mockTicket);
    }

    @Test
    void testUpdateTicket_NotFound() {
        Long ticketId = 1L;
        String newContent = "Updated Content";

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        Ticket result = ticketService.updateTicket(ticketId, newContent);

        assertNull(result);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}

