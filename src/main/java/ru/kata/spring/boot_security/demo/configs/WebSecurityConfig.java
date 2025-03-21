
package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> // Используем authorizeRequests вместо authorizeHttpRequests
                        authorizeRequests
                                .antMatchers("/", "/index").permitAll() // Разрешить доступ всем
                                .antMatchers("/admin/**").hasRole("ADMIN") // Только для админов
                                .antMatchers("/user").hasAnyRole("USER", "ADMIN") // Для пользователей и админов
                                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .formLogin(formLogin ->
                        formLogin
                                .successHandler(successUserHandler) // Перенаправление после успешного входа
                                .permitAll() // Разрешить доступ к форме входа всем
                )
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/logout") // URL для выхода
//                                .logoutSuccessUrl("/") // Перенаправление после выхода
//                                .permitAll() // Разрешить доступ к logout всем
//                );
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL для выхода
                                .logoutSuccessUrl("/") // Перенаправление после выхода
                                .invalidateHttpSession(true) // Уничтожение сессии
                                .deleteCookies("JSESSIONID") // Удаление cookies
                                .permitAll() // Разрешить доступ всем
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование паролей
    }
}