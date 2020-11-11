package ch.ost.rj.sa.miro2cml.presentation.utility;

import javax.servlet.http.HttpSession;

public class SessionHandlerService {
    public static String getMiroAccessToken(HttpSession session) {
        return session.getAttribute("miroAccessToken").toString();
    }

    public static void setMiroAccessToken(HttpSession session, String accessToken) {
        session.setAttribute("miroAccessToken", accessToken);
    }

    public static String getMiroTeamId(HttpSession session) {
        return session.getAttribute("miroTeamId").toString();
    }

    public static void setMiroTeamId(HttpSession session, String miroTeamId) {
        session.setAttribute("miroTeamId", miroTeamId);
    }

    public static boolean hasMiroAccessToken(HttpSession session) {
        return session.getAttribute("miroAccessToken") != null;
    }
}
