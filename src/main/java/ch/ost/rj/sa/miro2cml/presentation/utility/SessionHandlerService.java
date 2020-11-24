package ch.ost.rj.sa.miro2cml.presentation.utility;

import javax.servlet.http.HttpSession;

public class SessionHandlerService {
    private SessionHandlerService(){}
    private static String accessTokenAttributeName = "miroAccessToken";
    private static String miroTeamIdAttributeName = "miroTeamId";
    public static String getMiroAccessToken(HttpSession session) {
        return session.getAttribute(accessTokenAttributeName).toString();
    }

    public static void setMiroAccessToken(HttpSession session, String accessToken) {
        session.setAttribute(accessTokenAttributeName, accessToken);
    }

    public static String getMiroTeamId(HttpSession session) {
        return session.getAttribute(miroTeamIdAttributeName).toString();
    }

    public static void setMiroTeamId(HttpSession session, String miroTeamId) {
        session.setAttribute(miroTeamIdAttributeName, miroTeamId);
    }

    public static boolean hasMiroAccessToken(HttpSession session) {
        return session.getAttribute(accessTokenAttributeName) != null;
    }
}
