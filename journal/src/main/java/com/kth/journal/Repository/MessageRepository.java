package com.kth.journal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kth.journal.domain.Message;
import com.kth.journal.domain.MessageId;

public interface MessageRepository extends JpaRepository<Message, MessageId> {

}
