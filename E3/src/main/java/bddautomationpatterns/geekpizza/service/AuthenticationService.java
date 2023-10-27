package bddautomationpatterns.geekpizza.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Optional;

public class AuthenticationService {

    private static Hashtable<String, String> loggedInUsersByToken = new Hashtable<>();

    public static String setCurrentUser(String name) {
        String token = java.util.UUID.randomUUID().toString();
        loggedInUsersByToken.put(token, name);
        return token;
//        if (loggedInUsersByToken.computeIfAbsent(token, u -> name) == name)
//            return token;
//        return null;
    }

    private static String ensureAdminAuthenticated(String token) {
        String currentUserName = ensureAuthenticated(token);
        if (!currentUserName.equals(DefaultDataService.getAdminUserName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not admin");
        return currentUserName;
    }

    private static String ensureAuthenticated(String token) {
        String currentUserName = getCurrentUserName(token);
        if (currentUserName == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not logged in");
        return currentUserName;
    }

    private static String getCurrentUserName(String token) {
        return token == null ? null : loggedInUsersByToken.getOrDefault(token, null);
    }

    public static void clearLoggedInUser(String token) {
        if (token != null)
            loggedInUsersByToken.remove(token);
    }



    public static String getCurrentUserName(HttpServletRequest request, String token) {
        String requestToken = token != null ? token : getTokenFromCookie(request);
        return getCurrentUserName(requestToken);
    }

    public static String ensureAuthenticated(HttpServletRequest request, String token) {
        String requestToken = token != null ? token : getTokenFromCookie(request);
        return ensureAuthenticated(requestToken);
    }

    public static String ensureAdminAuthenticated(HttpServletRequest request, String token) {
        String requestToken = token != null ? token : getTokenFromCookie(request);
        return ensureAdminAuthenticated(requestToken);
    }

    public static void clearLoggedInUser(HttpServletRequest request, String token) {
        String requestToken = token != null ? token : getTokenFromCookie(request);
        clearLoggedInUser(requestToken);
    }

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }

    public static boolean isLoggedIn(RequestContext requestContext){
        String currentUserName = getCurrentUserName(requestContext);
        return currentUserName != null;
    }

    public static boolean isAdmin(RequestContext requestContext){
        String currentUserName = getCurrentUserName(requestContext);
        return DefaultDataService.getAdminUserName().equals(currentUserName);
    }

    public static String getCurrentUserName(RequestContext requestContext) {
        Optional<HttpServletRequest> rc = getCurrentHttpRequest();
        if (rc.isPresent())
        {
            String token = getTokenFromCookie(rc.get());
            String currentUserName = getCurrentUserName(token);
            return currentUserName;
        }

        return null;
    }


    private static String getTokenFromCookie(HttpServletRequest request){
        if (request == null)
            return null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        return Arrays.stream(cookies)
                .filter(c -> "auth-token".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny()
                .orElse(null);
    }

    public static void addAuthCookie(String token, HttpServletResponse response) {
        if (response == null)
            return;
        final Cookie cookie = new Cookie("auth-token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
    }

    public static void removeAuthCookie(HttpServletResponse response) {
        final Cookie cookie = new Cookie("auth-token", null);
        cookie.setMaxAge(0); // removes cookie
        response.addCookie(cookie);
    }
}
