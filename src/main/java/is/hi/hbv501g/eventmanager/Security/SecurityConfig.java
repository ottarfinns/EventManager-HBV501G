package is.hi.hbv501g.eventmanager.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1) Public endpoints (no token needed)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/accounts").permitAll()   // signup
                        .requestMatchers(HttpMethod.GET, "/events/**").permitAll()  // list & view events (public)

                        // 2) Protected endpoints (token required; choose role or just isAuthenticated)
                        //.requestMatchers(HttpMethod.POST, "/events/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/events/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/events/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/test-auth/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/test-auth/**").authenticated()

                        // 3) Everything else
                        .anyRequest().permitAll() // or .authenticated() if you prefer default-locked
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
