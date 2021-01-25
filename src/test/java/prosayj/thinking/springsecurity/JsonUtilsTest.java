/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity;


import org.junit.jupiter.api.Test;
import prosayj.thinking.springsecurity.model.user.domain.CustomUserDetails;
import prosayj.thinking.springsecurity.common.util.JsonUtils;

/**
 * TODO
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 06:57
 * @since 1.0.0
 */
public class JsonUtilsTest {

    @Test
    public void testToMap() {
        System.out.println(JsonUtils.toMap(new Student("zs", 12)));
        System.out.println(JsonUtils.toMap("{\"alg\":\"HS512\"}"));

    }

    @Test
    public void testToBean() {
        String obj1 = "{\"name\":\"zs\",\"password\":\"12345\",\"authorities\":[{\"authority\":\"USER\"}],\"enabled\":true,\"username\":\"zs\",\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"sub\":\"zs\",\"exp\":1594746056,\"iat\":1594742456,\"iss\":\"ProSayJ\"}";
        String obj2 = "{\"name\":\"zs\",\"password\":\"$2a$10$YqBw2EsRBxmp4aPbtO5lPeLtYNX2OaDM8Dg7iPps50GuMRHvCecAy\",\"authorities\":[{\"authority\":\"ADMIN\"},{\"authority\":\"USER\"}],\"enabled\":true,\"username\":\"zs\",\"credentialsNonExpired\":true,\"accountNonLocked\":true,\"accountNonExpired\":true,\"sub\":\"zs\",\"exp\":1594865186,\"iat\":1594861586,\"iss\":\"ProSayJ\"}";
        System.out.println(JsonUtils.toBean(obj1, CustomUserDetails.class));
        System.out.println(JsonUtils.toBean(obj2, CustomUserDetails.class));


    }


    static class Student {
        private String name;
        private int age;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
