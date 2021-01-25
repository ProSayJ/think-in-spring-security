/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.authentication.filter;

import io.jsonwebtoken.lang.Collections;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import prosayj.thinking.springsecurity.model.response.ResponseWriter;
import prosayj.thinking.springsecurity.model.response.RestBody;
import prosayj.thinking.springsecurity.model.user.domain.CustomUserDetails;
import prosayj.thinking.springsecurity.model.user.domain.UserDomain;
import prosayj.thinking.springsecurity.common.util.JWTUtils;
import prosayj.thinking.springsecurity.common.util.JsonUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static prosayj.thinking.springsecurity.common.constant.BaseConstant.ACCESS_TOKE;
import static prosayj.thinking.springsecurity.common.util.JWTUtils.TOKEN_PREFIX;


/**
 * Jwt认证过滤器
 * <br>
 * 会通过这个过滤器的端点有 /auth/login
 * <br>
 * request ---> JWTAuthenticationFilter ---> JWTAuthorizationFilter ---> ...
 * <p>
 * 并不是只有表单请求才会经过 UsernamePasswordAuthenticationFilter.
 * 只要请求是 post, 且 url 是 filterProcessUrl 就会经过这个过滤器:ps：验证没有生效
 * </p>
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 05:43
 * @since 1.0.0
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 浏览器访问 http://localhost:60004/auth/login 会通过 JWTAuthenticationFilter
        super.setFilterProcessesUrl("/auth/login");
        super.setUsernameParameter("name");
        super.setPasswordParameter("password");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserDomain userDomain;
        // 如果登录提交方式是 form表单提交，则数据由request.getParameterMap()中获取。
        // 如果登录提交方式是 json 提交，则数据由 requestBody 传输
        if (!Collections.isEmpty(request.getParameterMap())) {
            userDomain = new UserDomain(request.getParameter("name"), request.getParameter("password"));
        } else {
            userDomain = JsonUtils.toBean(request.getInputStream(), UserDomain.class);
        }


        assert userDomain != null;
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDomain.getName(), userDomain.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        log.info("authentication filter successful authentication: {}", authResult);
        // 如果验证成功, 就生成 Token 并返回
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        response.setHeader(ACCESS_TOKE, TOKEN_PREFIX + JWTUtils.create(customUserDetails.getName(), false, customUserDetails));
        ResponseWriter
                .accessDenied(
                        response,
                        RestBody.okData(HttpServletResponse.SC_ACCEPTED, "登录成功；欢迎你：" + customUserDetails.getName()));
    }

    /**
     * 如果 attemptAuthentication 抛出 AuthenticationException 则会调用这个方法
     *
     * @see UsernamePasswordAuthenticationFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        log.info("authentication filter unsuccessful authentication: {}", failed.getMessage());
        ResponseWriter
                .accessDenied(
                        response,
                        RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "authentication failed, reason: " + failed.getMessage()));
    }
}
