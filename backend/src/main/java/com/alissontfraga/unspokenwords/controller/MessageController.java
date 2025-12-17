package com.alissontfraga.unspokenwords.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.alissontfraga.unspokenwords.model.Message;
import com.alissontfraga.unspokenwords.model.User;
import com.alissontfraga.unspokenwords.repository.MessageRepository;
import com.alissontfraga.unspokenwords.service.MessageService;
import com.alissontfraga.unspokenwords.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final MessageService messageService;

    public MessageController(MessageRepository messageRepository, UserService userService, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> list(@AuthenticationPrincipal UserDetails userDetails) {
        User u = userService.findByUsername(userDetails.getUsername());
        return messageRepository.findByOwnerId(u.getId());
    }

    @PostMapping
    public ResponseEntity<Message> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Message payload
    ) {
        User u = userService.findByUsername(userDetails.getUsername());

        // define o dono da mensagem
        payload.setOwner(u);

        // se a data não vier no JSON, usa a data de hoje como fallback (opcional)
        if (payload.getDate() == null) {
            payload.setDate(LocalDate.now());
        }

        Message saved = messageRepository.save(payload);
        return ResponseEntity.ok(saved);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id, Authentication auth) {

        String username = auth.getName(); // usuário logado

        try {
            messageService.delete(id, username);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }



}
