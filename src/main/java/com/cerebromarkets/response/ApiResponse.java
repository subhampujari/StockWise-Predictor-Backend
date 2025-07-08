package com.cerebromarkets.response;


import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApiResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
