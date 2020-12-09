package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.presentation.model.MiroAuthResponse;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Value("${miro.client.id}")
    BigInteger clientID;

    @Value("${miro.client.secret}")
    String clientSecret;

    @Value("${miro.baseUrl}")
    String miroBaseUrl; //https://miro.com
    @Value("${miro.api.baseUrl}")
    String miroApiBaseUrl; //https://api.miro.com/v1/

    @Value("${miro.api.oauthTokenEndpoint}")
    String miroApiOAuthTokenEndpoint; ///oauth/token

    @Value("${miro.oauthEndpoint}")
    String miroOauthEndpoint; // /oauth/authorize

    @Value("${miro2cml.authorizeReceiverEndpoint}")
    String authorizeReceiverEndpoint;

    @Value("${miro2cml.baseUrl}")
    String miro2cmlBaseUrl;



    @GetMapping("/auth")
    public ModelAndView getAuthView(ModelMap model) {

        String code = "code";
        String completeRedirectUri = miro2cmlBaseUrl + authorizeReceiverEndpoint;

        String miroAuthUri = miroBaseUrl + miroOauthEndpoint +"?response_type=" + code + "&client_id=" + clientID.toString() + "&redirect_uri=" + completeRedirectUri;
        model.addAttribute("miroAuthUri", miroAuthUri);

        model.addAttribute("auth_token", "empty");
        return new ModelAndView("getAuth", model);
    }


    @GetMapping("/auth/redirect")
    public ModelAndView resolveRedirect(@RequestParam(name = "code") String authCode, ModelMap model, HttpSession session) {
        logger.debug("incoming auth-flow call");

        MiroAuthResponse authResponse = postAuthCode(authCode);
        if (authResponse == null){
            return new ModelAndView("redirect:/auth",model);
        }
        SessionHandlerService.setMiroAccessToken(session, authResponse.getAccess_token());
        SessionHandlerService.setMiroTeamId(session, authResponse.getTeam_id());

        logger.debug("redirect to root");
        return new ModelAndView("redirect:/", model);
    }

    public MiroAuthResponse postAuthCode(String authCode) {
        String completeRedirectUri = miro2cmlBaseUrl + authorizeReceiverEndpoint;

        String postUri = miroApiBaseUrl+miroApiOAuthTokenEndpoint+"?grant_type=authorization_code&code=" + authCode + "&redirect_uri=" + completeRedirectUri + "&client_id=" + clientID + "&client_secret=" + clientSecret;

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(postUri))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MiroAuthResponse mappedResponse = null;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            mappedResponse = objectMapper.readValue(response.body(), MiroAuthResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mappedResponse;
    }


}

