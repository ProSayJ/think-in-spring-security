/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ResponseWriter
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 01:25
 * @since 1.0.0
 */
public class ResponseWriter {
    /**
     * 认证成功
     *
     * @param response response
     * @param rest     rest
     * @throws IOException IOException
     */
    public static void success(HttpServletResponse response, Rest<?> rest) {
        response.setStatus(HttpServletResponse.SC_OK);
        flushOut(response, rest);
    }

    /**
     * 认证失败
     *
     * @param response response
     * @param rest     rest
     * @throws IOException IOException
     */
    public static void accessDenied(HttpServletResponse response, Rest<?> rest) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        flushOut(response, rest);
    }


    private static void flushOut(HttpServletResponse response, Rest<?> rest) {
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = null;
        try {
            resBody = objectMapper.writeValueAsString(rest);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(resBody);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
