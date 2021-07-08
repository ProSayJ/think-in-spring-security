package prosayj.thinking.springsecurity.authentication.pwd_storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;

/**
 * 密码存储-委托密码编码器
 * <p>
 * See: <a href=
 * "https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#authentication-password-storage-dpe"
 * > 5.1.2：密码存储-委托密码编码器</a>
 *
 * @author yangjian
 * @date 2021-07-08
 * @since 1.0.0
 */
public class DelegatingPasswordEncoderTest {

    /**
     * <p>{@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}
     */
    @Test
    @DisplayName("Example 18 创建默认DelegatingPasswordEncoder")
    public void m18() {
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder instanceof DelegatingPasswordEncoder) {
            DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) passwordEncoder;
            System.out.println(delegatingPasswordEncoder);
        }
    }

    /**
     * 创建自定义 DelegatingPasswordEncoder
     * <p> 格式一般为：{id}encodedPassword
     * <p> eg:
     * <p> {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
     * <br> {noop}password
     * <br> {pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc
     * <br> {scrypt}$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc=
     * <br> {sha256}97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0
     */
    @Test
    @DisplayName("Example 19 创建自定义 DelegatingPasswordEncoder")
    public void m19() {
        String idForEncode = "bcrypt";
        HashMap<String, PasswordEncoder> toPasswordEncoder = new HashMap<String, PasswordEncoder>() {{
            put(idForEncode, new BCryptPasswordEncoder());
            put("noop", NoOpPasswordEncoder.getInstance());
            put("sha256", new StandardPasswordEncoder());
            put("pbkdf2", new Pbkdf2PasswordEncoder());
            put("scrypt", new SCryptPasswordEncoder());
        }};

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, toPasswordEncoder);

        System.out.println(passwordEncoder.encode("abc123"));
        System.out.println(passwordEncoder.encode("abc123"));
    }


    @Test
    @DisplayName("Example 24.可重用的UserBulider")
    public void m3() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        System.out.println(userBuilder
                .username("user1")
                .password("password1")
                .roles("user1")
                .build().getPassword());
        System.out.println(userBuilder
                .username("user2")
                .password("password2")
                .roles("user2")
                .build().getPassword());
    }

    @Test
    @DisplayName("密码匹配")
    public void m4() {
        String pwd1 = "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG";
        String pwd2 = "{pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc";
        String pwd3 = "{scrypt}$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc=";
        String pwd4 = "{sha256}97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0";
        PasswordEncoder passwordEncoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String sourcePwd = "abc123456";
        String encode = passwordEncoder.encode(sourcePwd);
        String encode1 = passwordEncoder.encode(sourcePwd);

        System.out.println(encode);
        System.out.println(encode1);
        System.out.println(passwordEncoder.matches(sourcePwd, encode));
        System.out.println(passwordEncoder.matches(sourcePwd, encode1));


        //设值默认的加密器
        ((DelegatingPasswordEncoder)passwordEncoder).setDefaultPasswordEncoderForMatches(
                new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));

    }
}
