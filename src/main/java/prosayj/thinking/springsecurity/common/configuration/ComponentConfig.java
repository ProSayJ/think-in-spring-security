package prosayj.thinking.springsecurity.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import prosayj.thinking.springsecurity.authentication.handler.CustomAuthenticationFailureHandler;
import prosayj.thinking.springsecurity.authentication.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import prosayj.thinking.springsecurity.authentication.provider.MyAuthenticationProvider;

/**
 * ComponentRegister
 *
 * @author yangjian201127@credithc.com
 * @date 2020-12-27 上午 01:55
 * @since 1.0.0
 */
@Configuration
public class ComponentConfig {

    @Bean
    public PasswordEncoder registerPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFailureHandler registerFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CustomSimpleUrlAuthenticationSuccessHandler registerSuccessHandler() {
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationProvider registerMyAuthenticationProvider() {
        return new MyAuthenticationProvider();
    }
}
