package com.kth.journal.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {
    @NotNull
    private Long senderId;
    @NotNull
    private Long recipientId;
    @NotNull
    private String message;
}
