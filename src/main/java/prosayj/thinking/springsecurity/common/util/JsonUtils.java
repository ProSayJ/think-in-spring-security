/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JsonUtils
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 05:46
 * @since 1.0.0
 */
public class JsonUtils {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //反序列时，忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        // add GrantedAuthorityFiledDeserializer to Jackson.
        module.addDeserializer(Set.class, new GrantedAuthorityFiledDeserializer());
//        module.addDeserializer(HashSet.class, new GrantedAuthorityFiledDeserializer());
        objectMapper.registerModule(module);

    }

    public static String toJsonString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] toJsonByte(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化对象。注意对象需要有默认的无参的构造方法
     *
     * @param content content
     * @param type    type
     * @param <T>     <T>
     * @return <T>
     */
    public static <T> T toBean(String content, Class<T> type) {
        try {
            return objectMapper.readValue(content, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toBean(InputStream inputStream, Class<T> type) {
        try {
            return objectMapper.readValue(inputStream, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * object to map
     *
     * @param value 对象
     * @return Map<String, Object>
     */
    public static Map<String, Object> toMap(Object value) {
        try {
            return objectMapper.readValue(toJsonString(value), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> toMap(byte[] value) {
        return toMap(new String(value, StandardCharsets.UTF_8));
    }

    public static Map<String, Object> toMap(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toBean(byte[] data, Class<T> type) {
        return toBean(new String(data, StandardCharsets.UTF_8), type);
    }

    public static <T> T toList(byte[] data, TypeReference<T> type) {
        try {
            return objectMapper.readValue(data, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String data, Class<T> type) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, type);
        return toList(data, javaType);
    }

    public static <T> List<T> toList(String data, JavaType javaType) {
        try {
            return objectMapper.readValue(data, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode readTree(byte[] data) {
        try {
            return objectMapper.readTree(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode readTree(String data) {
        try {
            return objectMapper.readTree(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJsonNodeAsText(JsonNode jsonNode, String jsonKey) {
        return jsonNode.get(jsonKey).asText();
    }

}
