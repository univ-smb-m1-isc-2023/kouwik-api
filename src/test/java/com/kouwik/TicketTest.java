package com.kouwik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kouwik.model.Board;
import com.kouwik.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketTest {
    private Ticket ticket;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(); // Assurez-vous que Board est correctement instancié si nécessaire
        ticket = new Ticket("Initial Content", 1, board);
    }

    @Test
    void testGetAndSetContent() {
        // Test initial state from constructor
        assertEquals("Initial Content", ticket.getContent(), "Content should match constructor assignment.");

        // Test setContent and getContent
        ticket.setContent("Updated Content");
        assertEquals("Updated Content", ticket.getContent(), "Content should be updated.");
    }

    @Test
    void testGetAndSetVotes() {
        // Test initial state from constructor
        assertEquals(0, ticket.getVotes(), "Initial votes should be 0.");

        // Test setVotes and getVotes
        ticket.setVotes(5);
        assertEquals(5, ticket.getVotes(), "Votes should be updated to 5.");
    }

    @Test
    void testGetAndSetBoard() {
        // Test initial state from constructor
        assertEquals(board, ticket.getBoard(), "Board should match constructor assignment.");

        // Create a new board and set it
        Board newBoard = new Board();
        ticket.setBoard(newBoard);
        assertEquals(newBoard, ticket.getBoard(), "Board should be updated.");
    }

    @Test
    void testGetAndSetColumnId() {
        // Test initial state from constructor
        assertEquals(1, ticket.getColumnId(), "Column ID should match constructor assignment.");

        // Test setColumnId and getColumnId
        ticket.setColumnId(2);
        assertEquals(2, ticket.getColumnId(), "Column ID should be updated.");
    }
}
