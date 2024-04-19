package com.kouwik.service;

import com.kouwik.model.Board;
import com.kouwik.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Board createBoard() {
        Board board = new Board();
        boardRepository.save(board);
        return board;
    }

    public Board getBoardByUuid(String uuid) {
        return boardRepository.findByUuid(uuid);
    }
}
