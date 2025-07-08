package com.cerebromarkets.response;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class PaymentResponse {
    private String payment_url;
}
