package bichoperdido.appconfig.filter;

import bichoperdido.business.authentication.domain.UserIdentification;
import bichoperdido.business.authentication.exception.InvalidTokenException;
import bichoperdido.business.authentication.service.TokenAuthService;
import bichoperdido.business.authentication.service.UserAuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Gabriel.
 */
public class AuthenticationFilter implements Filter {

    public static final String TOKEN_HEADER = "x-token";
    private final UserAuthService userAuthService;
    private final TokenAuthService tokenService;

    public AuthenticationFilter(UserAuthService userAuthService, TokenAuthService tokenService) {
        this.userAuthService = userAuthService;
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        try {
            String token = req.getHeader(TOKEN_HEADER);
            if (token != null) {
                UserIdentification user = userAuthService.authUser(token);
                userAuthService.setAuthUser(user, token);
                tokenService.updateToken(token, user.getId());
            }
        } catch (InvalidTokenException ignored) {}

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
