package br.com.guerin.Utils;


import br.com.guerin.Entity.User;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.guerin.Payload.User.ResultTokens;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

public class GetToken {
    private User user;
    private String userPasswordStr;
    private String accessToken;
    private String refreshToken;

    public User getUser() {
        return user;
    }

    public String getUserPasswordStr() {
        return userPasswordStr;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    private String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    private StringBuffer connectToApiAndGetResponse(User user, String userPasswordStr) {
        this.user = user;
        this.userPasswordStr = userPasswordStr;

        try {
            String urlString = "http://localhost:8085/api/login";
            String requestMethod = "POST";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("username", user.getUsername());
            parameters.put("password", userPasswordStr);

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(this.getParamsString(parameters));
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            return content;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultTokens setTokens(StringBuffer tokens)  {
        String tokensStr = tokens.toString();

        try {
            JSONObject jsonObject = new JSONObject(tokensStr);
            this.accessToken = jsonObject.getString("access_token");
            this.refreshToken = jsonObject.getString("refresh_token");
            return new ResultTokens(this.refreshToken, this.accessToken);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultTokens getToken(User user, String userPasswordStr) {
        StringBuffer response = this.connectToApiAndGetResponse(user, userPasswordStr);
        ResultTokens tokens = this.setTokens(response);
        return tokens;
    }

}
