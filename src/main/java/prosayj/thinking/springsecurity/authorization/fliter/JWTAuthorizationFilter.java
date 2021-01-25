/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.authorization.fliter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import prosayj.thinking.springsecurity.model.response.ResponseWriter;
import prosayj.thinking.springsecurity.model.response.RestBody;
import prosayj.thinking.springsecurity.model.user.domain.CustomUserDetails;
import prosayj.thinking.springsecurity.common.util.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Jwt鉴权过滤器<br>
 * 会通过这个过滤器的端点有 /task/**, /auth/register
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:16
 * @since 1.0.0
 */
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final Set<String> WHITE_LIST = Stream.of("/auth/register").collect(Collectors.toSet());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("authorization filter doFilterInternal");
        final String authorization = request.getHeader(JWTUtils.TOKEN_HEADER);
        log.info("raw-access-token: {}", authorization);

        // Branch A: 如果请求头中没有 key  Authorization
        if (!StringUtils.hasLength(authorization)) {
            // 白名单放行
            if (WHITE_LIST.contains(request.getRequestURI())) {
                chain.doFilter(request, response);
            } else {
                ResponseWriter
                        .accessDenied(
                                response,
                                RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "未经授权的访问! "));

            }
            return;
        }

        // Branch B: 如果请求头中有 Bear xxx, 设置认证信息
        final String jsonWebToken = authorization.replace(JWTUtils.TOKEN_PREFIX, "");

        // TODO 用 Redis 的过期控制 token, 而不用 jwt 的 Expiration
        try {
            JWTUtils.hasExpired(jsonWebToken);
        } catch (ExpiredJwtException e) {
            log.error("access-token 已过期", e);
            ResponseWriter
                    .accessDenied(
                            response,
                            RestBody.failure(HttpServletResponse.SC_UNAUTHORIZED, "access-token 已过期, 请重新登陆! " + e.getMessage()));
        }
        // TODO 每一次携带正确 token 的访问, 都刷新 Redis 的过期时间
        CustomUserDetails customUserDetails = JWTUtils.userDetails(jsonWebToken);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        customUserDetails.getName(),
                        // TODO Json Web Token 中不能携带用户密码
                        customUserDetails.getPassword(),
                        customUserDetails.getAuthorities()
                )
        );
        chain.doFilter(request, response);
    }
}

