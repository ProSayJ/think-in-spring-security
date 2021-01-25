/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prosayj.thinking.springsecurity.model.user.domain.CustomUserDetails;
import prosayj.thinking.springsecurity.model.user.domain.UserDomain;
import prosayj.thinking.springsecurity.model.user.mapper.UserDomainMapper;

import java.util.HashSet;
import java.util.List;

/**
 * UserDetailsService 的主要作用是: 获取数据库里面的信息，然后封装成对象
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 03:50
 * @since 1.0.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDomainMapper userDomainMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //查询用户详情和角色权限。
        UserDomain queryByName = userDomainMapper.findByUsername(name);
        if (queryByName == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //将数据库形式的role解析为UserDetails的权限集
        //该方法是SpringSecurity提供的。方法用于将逗号隔开的权限集合字符串切割成可用权限对象集合。
        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(queryByName.getRole());
        return new CustomUserDetails(queryByName.getName(), queryByName.getPassword(), new HashSet<>(roles));
    }
}
