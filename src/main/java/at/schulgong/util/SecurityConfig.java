package at.schulgong.util;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
 * @version 0.2
 * @implNote Spring Security Configuration
 * @since June 2023
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String API_REQUEST = "/api/**";

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

        http.csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers(
                        new AntPathRequestMatcher(
                                "/api/speaker/play/**", HttpMethod.GET.toString()))
                .permitAll()
                .requestMatchers(
                        new AntPathRequestMatcher(
                                "/",
                                HttpMethod.GET
                                        .toString())) // Add this line to allow all GET requests to
                // "/"
                .permitAll()
                .requestMatchers(
                        new AntPathRequestMatcher("/index.html", HttpMethod.GET.toString()))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**/*.js", HttpMethod.GET.toString()))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**/*.css", HttpMethod.GET.toString()))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**/*.ttf", HttpMethod.GET.toString()))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**/*.svg", HttpMethod.GET.toString()))
                .permitAll()
                .requestMatchers(
                        new AntPathRequestMatcher("/api/auth/login", HttpMethod.POST.toString()))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher(API_REQUEST, HttpMethod.GET.toString()))
                .authenticated()
                .requestMatchers(new AntPathRequestMatcher(API_REQUEST, HttpMethod.POST.toString()))
                .authenticated()
                .requestMatchers(new AntPathRequestMatcher(API_REQUEST, HttpMethod.PUT.toString()))
                .authenticated()
                .requestMatchers(
                        new AntPathRequestMatcher(API_REQUEST, HttpMethod.DELETE.toString()))
                .authenticated();

        http.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .authorizeRequests(authorize -> authorize.anyRequest().permitAll());

        http.addFilterAfter(
                new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat =
                new TomcatServletWebServerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        var securityConstraint = new SecurityConstraint();
                        securityConstraint.setUserConstraint("CONFIDENTIAL");
                        var collection = new SecurityCollection();
                        collection.addPattern("/*");
                        securityConstraint.addCollection(collection);
                        context.addConstraint(securityConstraint);
                    }
                };
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    private Connector getHttpConnector() {
        var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8083);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
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

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .authorizeRequests(authorize -> authorize.anyRequest().permitAll())
                .build();
    }
}
