package prosayj.thinking.springsecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * TODO
 *
 * @author yangjian
 * @date 2021-06-21 下午 01:18
 * @since 1.0.0
 */
public class PasswordStorage {
    public static void main(String[] args) {
        User user = (User) User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("user")
                .build();
        System.out.println(user.getPassword());
        // {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG


        User.UserBuilder users = User.withDefaultPasswordEncoder();
        User user2 = (User) users
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        User admin = (User) users
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
    }
}
