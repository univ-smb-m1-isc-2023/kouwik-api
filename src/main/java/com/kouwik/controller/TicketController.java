package com.kouwik.controller;

import com.kouwik.model.Ticket;
import com.kouwik.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public Ticket createTicket(@RequestBody String content) {
        return ticketService.createTicket(content);
    }
}