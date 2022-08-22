package br.com.guerin.Payload.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
public class ResultTokens {
    @Getter
    @Setter
    public String refresh_token;

    @Getter
    @Setter
    public String access_token;

    public ResultTokens(String refreshToken, String accessToken) {
        this.refresh_token = refreshToken;
        this.access_token = accessToken;
    }
}
