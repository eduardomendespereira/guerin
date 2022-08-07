package br.com.guerin.Payload.User;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
