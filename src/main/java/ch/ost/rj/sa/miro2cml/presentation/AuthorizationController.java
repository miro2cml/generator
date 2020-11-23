package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.model.MiroAuthResponse;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Value("${miro.clientId}")
    BigInteger clientID;

    @Value("${miro.clientSecret}")
    String clientSecret;

    @Value("${miro.redirectUri}")
    String redirectUri = "http://localhost:8080/auth/redirect";


    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    @GetMapping("/auth")
    public ModelAndView getAuthView(ModelMap model) {

        String code = "code";

        String state = "random string"; //optional - use against CSRF attacks

        String miroAuthUri = "https://miro.com/oauth/authorize?response_type=" + code + "&client_id=" + clientID.toString() + "&redirect_uri=" + redirectUri;
        model.addAttribute("miroAuthUri", miroAuthUri);

        model.addAttribute("auth_token", "empty");
        //"redirect:"+miroAuthUri
        return new ModelAndView("getAuth", model);
    }


    @GetMapping("/auth/redirect")
    public ModelAndView resolveRedirect(@RequestParam(name = "code") String authCode, ModelMap model, HttpSession session) {
       logger.debug("incoming auth-flow call");
        model.addAttribute("clientID", clientID);

        MiroAuthResponse authResponse = postAuthCode(authCode);
        model.addAttribute("auth_token", authResponse.getAccess_token());
        SessionHandlerService.setMiroAccessToken(session, authResponse.getAccess_token());
        SessionHandlerService.setMiroTeamId(session, authResponse.getTeam_id());

        logger.debug("redirect to root");
        return new ModelAndView("redirect:/", model);
    }

    public MiroAuthResponse postAuthCode(String authCode) {

/*      Map<Object, Object> data = new HashMap<>();
        data.put("grant_type", "authorization_code");
        data.put("client_id", clientID);
        data.put("client_secret", clientSecret);
        data.put("code", authCode);
        data.put("redirect_uri", redirectUri);
*/
        String postUri = "https://api.miro.com/v1/oauth/token?grant_type=authorization_code&code=" + authCode + "&redirect_uri=" + redirectUri + "&client_id=" + clientID + "&client_secret=" + clientSecret;

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(postUri))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = null;
        ObjectMapper objectMapper = new ObjectMapper();
        MiroAuthResponse mappedResponse = new MiroAuthResponse();

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

