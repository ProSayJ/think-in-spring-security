/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.model.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDomain
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:26
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {

    private String name;
    private String password;
    /**
     * 用户角色
     */
    private String role;

    public UserDomain(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
