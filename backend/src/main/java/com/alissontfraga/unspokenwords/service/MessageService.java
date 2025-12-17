package com.alissontfraga.unspokenwords.service;

import org.springframework.stereotype.Service;

import com.alissontfraga.unspokenwords.model.Message;
import com.alissontfraga.unspokenwords.repository.MessageRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

        public void delete(String id, String username) {

        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        // compara username com username
        if (!message.getOwner().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized to delete this message");
        }

        messageRepository.delete(message);
    }

}
