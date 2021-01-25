package prosayj.thinking.springsecurity.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import prosayj.thinking.springsecurity.authentication.filter.JWTAuthenticationFilter;
import prosayj.thinking.springsecurity.authorization.fliter.JWTAuthorizationFilter;
import prosayj.thinking.springsecurity.authentication.handler.CustomAuthenticationFailureHandler;
import prosayj.thinking.springsecurity.authentication.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import prosayj.thinking.springsecurity.authentication.provider.MyAuthenticationProvider;

/**
 * Spring security config
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-09 下午 05:34
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MyAuthenticationProvider authenticationProvider;
    public CustomSimpleUrlAuthenticationSuccessHandler successHandler;
    public CustomAuthenticationFailureHandler failureHandler;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public void setSuccessHandler(CustomSimpleUrlAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Autowired
    public void setFailureHandler(CustomAuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationProvider(MyAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 配置自定义的认证器
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Cross Site Request Forgery: 跨站请求伪造; Reference: https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html
                // Reference: demo-spring-security-csrf
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().hasAnyAuthority("ADMIN")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // 让校验 Token 的过滤器在身份认证过滤器之后
                .addFilterAfter(new JWTAuthorizationFilter(), JWTAuthenticationFilter.class)
                // 不需要 Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler);
        //.disable();
    }


}