package com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime date;
    private String message;
    private String details;
}
