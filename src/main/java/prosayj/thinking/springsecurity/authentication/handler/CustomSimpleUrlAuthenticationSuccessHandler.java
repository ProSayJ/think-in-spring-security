/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.authentication.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import prosayj.thinking.springsecurity.model.response.ResponseWriter;
import prosayj.thinking.springsecurity.model.response.RestBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomSimpleUrlAuthenticationSuccessHandler
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 01:33
 * @since 1.0.0
 */
@Slf4j
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("自定义 SuccessHandler :: 登陆成功");
        ResponseWriter.success(response, RestBody.ok("登录成功"));
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        log.info("自定义 SuccessHandler :: 重定向到: {}", requestCache.getRequest(request, response).getRedirectUrl());
        return requestCache.getRequest(request, response).getRedirectUrl();
    }
}
