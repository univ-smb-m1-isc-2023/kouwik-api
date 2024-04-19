package com.kouwik.controller;

import com.kouwik.model.Board;
import com.kouwik.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<Board> createBoard() {
        Board board = boardService.createBoard();
        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Board> getBoardByUuid(@PathVariable String uuid) {
        Board board = boardService.getBoardByUuid(uuid);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
