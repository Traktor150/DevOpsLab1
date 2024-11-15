package com.kth.journal.Dto;

import java.sql.Timestamp;

public record MessageResponse(Long senderId, String senderName, Long recipientId, String message, Timestamp timestamp) {
}