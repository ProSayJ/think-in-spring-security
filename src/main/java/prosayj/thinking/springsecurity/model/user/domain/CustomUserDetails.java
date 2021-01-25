/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.model.user.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import prosayj.thinking.springsecurity.model.user.domain.UserDomain;
import prosayj.thinking.springsecurity.common.util.GrantedAuthorityFiledDeserializer;

import java.util.Collections;
import java.util.Set;

/**
 * CustomUserDetails
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:24
 * @since 1.0.0
 */
@Data
public class CustomUserDetails implements UserDetails {
    /**
     * username
     */
    private String name;

    /**
     * password
     */
    private String password;

    /**
     * {@link GrantedAuthority}
     */
    private Set<GrantedAuthority> authorities;


    /**
     * default Constructor for jackson Deserialization
     * {@link GrantedAuthorityFiledDeserializer}
     */
    public CustomUserDetails() {
        super();
    }

    public CustomUserDetails(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Constructor for test
     *
     * @param user CustomUserDetails
     */

    public CustomUserDetails(@NonNull UserDomain user) {
        this.name = user.getName();
        this.password = user.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    public CustomUserDetails(String name, String password, Set<GrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
