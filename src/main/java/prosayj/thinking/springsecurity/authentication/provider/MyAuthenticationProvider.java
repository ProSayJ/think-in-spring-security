/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.authentication.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import prosayj.thinking.springsecurity.service.impl.UserDetailsServiceImpl;

/**
 * 自定义认证器
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 03:40
 * @since 1.0.0
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        // 获取封装用户信息的对象
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 进行密码的比对
        boolean flag = passwordEncoder.matches(password, userDetails.getPassword());
        // 校验通过
        if (flag) {
            // 将权限信息也封装进去
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        throw new AuthenticationException("用户名或密码错误") {
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
