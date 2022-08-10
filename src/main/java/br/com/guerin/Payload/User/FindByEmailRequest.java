package br.com.guerin.Payload.User;

import lombok.Getter;
import lombok.Setter;

public class FindByEmailRequest {
    @Getter
    @Setter
    private String email;
}
