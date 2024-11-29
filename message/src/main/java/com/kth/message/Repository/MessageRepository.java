package com.kth.message.Repository;

import com.kth.message.domain.Account;
import com.kth.message.domain.Message;
import com.kth.message.domain.MessageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, MessageId> {
    List<Message> findByIdConversation(Account conversation); // Hämta alla meddelanden för en mottagare
    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderAccount = :account1 AND m.id.conversation = :account2) OR " +
            "(m.senderAccount = :account2 AND m.id.conversation = :account1)")
    List<Message> findMessagesBetweenAccounts(@Param("account1") Account account1, @Param("account2") Account account2);
}
