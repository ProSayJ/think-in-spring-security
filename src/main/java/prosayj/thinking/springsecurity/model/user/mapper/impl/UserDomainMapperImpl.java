package prosayj.thinking.springsecurity.model.user.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import prosayj.thinking.springsecurity.model.user.domain.UserDomain;
import prosayj.thinking.springsecurity.model.user.mapper.UserDomainMapper;
import prosayj.thinking.springsecurity.common.util.JsonUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDomainMapperImpl
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:14
 * @since 1.0.0
 */
@Service
@Slf4j
public class UserDomainMapperImpl implements UserDomainMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    List<UserDomain> userDb;


    @PostConstruct
    public void initDbStorage() throws InterruptedException {
        log.info("local storage db user init......start~~~~");
        userDb = new ArrayList<UserDomain>() {{
            add(new UserDomain("zs", passwordEncoder.encode("zs"), "USER,ADMIN"));

        }};
        Thread.sleep(2 * 1000);
        log.info("local storage db user init......ent~~~~:{}", JsonUtils.toJsonString(userDb));

    }


    @Override
    public UserDomain findByUsername(String username) {
        List<UserDomain> result = userDb.stream().filter(userDomain -> username.equals(userDomain.getName())).collect(Collectors.toList());
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public int save(UserDomain userDomain) {
        if (userDb.stream().anyMatch(user -> user.getName().equals(userDomain.getName()))) {
            throw new RuntimeException("用户已存在");
        }
        userDb.add(userDomain);
        return 1;
    }
}
