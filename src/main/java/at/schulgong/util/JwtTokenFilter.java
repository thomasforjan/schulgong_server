package at.schulgong.util;

import at.schulgong.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote JwtTokenFilter is a filter, which checks the token and validates it
 * @since June 2023
 */
public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  /**
   * Constructor
   *
   * @param jwtTokenProvider JwtTokenProvider
   */
  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * This method is called by the container each time a request/response pair is passed through the chain due to a
   *
   * @param req         The request to process
   * @param res         The response associated with the request
   * @param filterChain Provides access to the next filter in the chain for this filter to pass the request and response
   *                    to for further processing
   * @throws IOException      If an I/O error occurs during this filter's processing of the request
   * @throws ServletException If the processing fails for any other reason
   */
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
    throws IOException, ServletException {

    String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
      filterChain.doFilter(req, res);
    } catch (JwtAuthenticationException e) {
      HttpServletResponse httpResponse = (HttpServletResponse) res;
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.getWriter().write(e.getMessage());
    }
  }
}
