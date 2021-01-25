package prosayj.thinking.springsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prosayj.thinking.springsecurity.authentication.filter.JWTAuthenticationFilter;
import prosayj.thinking.springsecurity.authorization.fliter.JWTAuthorizationFilter;
import prosayj.thinking.springsecurity.model.user.domain.UserDomain;
import prosayj.thinking.springsecurity.model.user.mapper.UserDomainMapper;

import java.util.Map;

/**
 * AuthController
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:38
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDomainMapper userDomainMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 注册
     *
     * @param registerUser 用户信息
     * @return UserDomain
     * @see JWTAuthenticationFilter
     * @see JWTAuthorizationFilter
     */
    @PostMapping("/register")
    public UserDomain registerUser(@RequestBody Map<String, String> registerUser) {
        UserDomain user = new UserDomain();
        user.setName(registerUser.get("name"));
        user.setPassword(passwordEncoder.encode(registerUser.get("password")));
        user.setRole("USER");
        log.info("AuthController: {}", userDomainMapper.save(user));
        return user;
    }


}
