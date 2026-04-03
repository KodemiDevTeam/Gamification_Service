package com.gamification.streaks.exception;

import com.gamification.streaks.execption.BadRequestException;
import com.gamification.streaks.execption.ErrorResponse;
import com.gamification.streaks.execption.GlobalExceptionHandler;
import com.gamification.streaks.execption.NotificationNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotificationNotFound_shouldReturn404WithMessage() {
        NotificationNotFoundException ex = new NotificationNotFoundException("Resource not found");

        ResponseEntity<ErrorResponse> response = handler.handleNotificationNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Resource not found");
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    void handleBadRequest_shouldReturn400WithMessage() {
        BadRequestException ex = new BadRequestException("Invalid input");

        ResponseEntity<ErrorResponse> response = handler.handleBadRequest(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid input");
        assertThat(response.getBody().getStatus()).isEqualTo(400);
    }

    @Test
    void handleGeneralException_shouldReturn500WithGenericMessage() {
        Exception ex = new RuntimeException("Something went wrong");

        ResponseEntity<ErrorResponse> response = handler.handleGeneralException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Internal Server Error");
        assertThat(response.getBody().getStatus()).isEqualTo(500);
    }

    @Test
    void notificationNotFoundException_shouldCarryMessage() {
        NotificationNotFoundException ex = new NotificationNotFoundException("Badge not found");
        assertThat(ex.getMessage()).isEqualTo("Badge not found");
    }

    @Test
    void badRequestException_shouldCarryMessage() {
        BadRequestException ex = new BadRequestException("Missing required field");
        assertThat(ex.getMessage()).isEqualTo("Missing required field");
    }
}
