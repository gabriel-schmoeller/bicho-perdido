package bichoperdido.appconfig.configuration;

import bichoperdido.appconfig.error.ClientErrorHandler;
import bichoperdido.appconfig.exception.AuthenticationRequiredException;
import bichoperdido.appconfig.filter.AuthenticationFilter;
import bichoperdido.business.authentication.domain.UserRole;
import bichoperdido.business.authentication.service.TokenAuthService;
import bichoperdido.business.authentication.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gabriel.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_REQUIRED_MESSAGE = "Full authentication is required to access this resource.";
    @Autowired
    public ClientErrorHandler errorHandler;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private TokenAuthService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").hasAuthority(UserRole.USER.toString())
                .anyRequest().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        handleAccessDeniedError(response);
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        handleAccessDeniedError(response);
                    }
                })
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin().disable()
                .logout().disable()
                .addFilterBefore(new AuthenticationFilter(userAuthService, tokenService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void handleAccessDeniedError(HttpServletResponse response) throws IOException {
        response.setContentType(errorHandler.getContentType());
        response.getWriter().print(
                errorHandler.buildMessage(new AuthenticationRequiredException(AUTHENTICATION_REQUIRED_MESSAGE)));
    }

}