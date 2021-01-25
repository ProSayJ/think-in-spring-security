/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义 jackson 反序列化对象 没有无参构造函数问题
 * {@link JWTUtils#userDetails(String)}
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 上午 12:22
 * @since 1.0.0
 */
public class GrantedAuthorityFiledDeserializer extends JsonDeserializer<Set<GrantedAuthority>> {

    @Override
    public Set<GrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        Set<GrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if (treeNode instanceof ArrayNode) {
            for (JsonNode jsonNode : (ArrayNode) treeNode) {
                JsonNode authority = jsonNode.get("authority");
                if (authority instanceof TextNode) {
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
                }
            }

        }
        return simpleGrantedAuthorities;
    }
}
