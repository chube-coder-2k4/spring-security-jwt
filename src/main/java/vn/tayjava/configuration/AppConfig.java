package vn.tayjava.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.tayjava.service.UserService;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class AppConfig {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(3600); // 1 hour
            }
        };
    }
    // mục đích của method này để cấu hình CORS (Cross-Origin Resource Sharing) cho ứng dụng, cho phép các yêu cầu từ bất kỳ nguồn gốc nào với các phương thức và tiêu đề cụ thể.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // mục đích của method này để cung cấp một bean PasswordEncoder sử dụng thuật toán mã hóa BCrypt, giúp bảo vệ mật khẩu người dùng bằng cách mã hóa chúng trước khi lưu trữ.

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, Prefilter prefilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**", "/swagger-ui/**", "/user/**").permitAll().anyRequest().authenticated()).sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(prefilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    // mục đích của method này để cấu hình bảo mật cho ứng dụng, bao gồm việc tắt CSRF, cho phép truy cập không cần xác thực đến các endpoint cụ thể, và thiết lập chính sách quản lý phiên làm việc.

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/actuator/**");
    }
//     mục đích của method này để bỏ qua các yêu cầu đến các endpoint cụ thể khỏi quá trình bảo mật, thường là để cho phép truy cập tự do đến tài liệu API và các công cụ giám sát.

    @Bean
    public AuthenticationManager authentication(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    // mục đích của method này để cung cấp một AuthenticationManager, cho phép ứng dụng

    @Bean
    public AuthenticationProvider provider(UserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService.userDetailsService());
        return provider;
    }
    // mục đích của method này để cấu hình một AuthenticationProvider sử dụng DaoAuthenticationProvider, thiết lập bộ mã hóa mật khẩu và dịch vụ chi tiết người dùng để xác thực người dùng.
}
