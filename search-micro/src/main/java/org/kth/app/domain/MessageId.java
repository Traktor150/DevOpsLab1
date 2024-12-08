package org.kth.app.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class MessageId implements Serializable {

    @ManyToOne
    private Account conversation;

    private Timestamp timestamp;

    public MessageId() {
    }

    public MessageId(Account conversation, Timestamp timestamp) {
        this.conversation = conversation;
        this.timestamp = timestamp;
    }

    public Account getConversation() {
        return conversation;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setConversation(Account conversation) {
        this.conversation = conversation;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MessageId messageId = (MessageId) o;
        return Objects.equals(conversation, messageId.conversation) && Objects.equals(timestamp, messageId.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversation, timestamp);
    }
}
