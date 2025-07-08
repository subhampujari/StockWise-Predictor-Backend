package com.cerebromarkets.response;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AuthResponse {
    public boolean isTwoFactorAuthEnabled() {
        return isTwoFactorAuthEnabled;
    }

    public void setTwoFactorAuthEnabled(boolean twoFactorAuthEnabled) {
        isTwoFactorAuthEnabled = twoFactorAuthEnabled;
    }

    private String jwt;
      private boolean status;
      private String message;
      private boolean isTwoFactorAuthEnabled;
      private String session;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getSession() {
        return session;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setSession(String session) {
        this.session = session;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
