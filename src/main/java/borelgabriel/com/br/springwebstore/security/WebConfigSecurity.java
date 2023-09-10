package borelgabriel.com.br.springwebstore.security;

import borelgabriel.com.br.springwebstore.service.ImplUserDetailsService;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity implements HttpSessionListener {
    @Autowired
    private ImplUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.userDetailsService(this.userDetailsService);
        http.passwordManagement(Customizer.withDefaults());
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        );
        http.logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
        );
        http.csrf((csrf) -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable()
        );
        http.addFilterAfter(
                new JWTLoginFilter("/login", this.authenticationManager()), UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(new JWTAPIAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userDetailsService);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }
}
