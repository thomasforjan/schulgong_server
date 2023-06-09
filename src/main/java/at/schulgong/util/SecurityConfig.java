package at.schulgong.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Spring Security Configuration
 * @since June 2023
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  /**
   * Constructor
   *
   * @param jwtTokenProvider JwtTokenProvider
   */
  @Autowired
  public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * SecurityFilterChain Bean for Spring Security
   *
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception Exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .cors().configurationSource(corsConfigurationSource())
      .and()
      .authorizeRequests()
      .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
      .requestMatchers(new AntPathRequestMatcher("/login", HttpMethod.POST.toString())).permitAll()
      .anyRequest().authenticated();

    http.addFilterAfter(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * UserDetailsService Bean for Spring Security
   *
   * @return UserDetailsService
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager();
  }

  /**
   * CorsConfigurationSource Bean for Spring Security
   *
   * @return CorsConfigurationSource
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
