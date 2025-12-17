package com.alissontfraga.unspokenwords.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alissontfraga.unspokenwords.model.Message;

    public interface MessageRepository extends JpaRepository<Message, String> {
        List<Message> findByOwnerId(String ownerId);
    }