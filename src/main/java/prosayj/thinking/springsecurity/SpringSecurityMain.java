package prosayj.thinking.springsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringSecurityMain
 *
 * @author yangjian201127@credithc.com
 * @date 2020-12-26 下午 06:25
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
@RestController
public class SpringSecurityMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityMain.class, args);
    }

    @GetMapping("/hello")
    public String hello() throws JsonProcessingException {
        log.info("authentication::{}", new ObjectMapper()
                .writeValueAsString(SecurityContextHolder.getContext().getAuthentication()));
        return "hello spring security";
    }

}
