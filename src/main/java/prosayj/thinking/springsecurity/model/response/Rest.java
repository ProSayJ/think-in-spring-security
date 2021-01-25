/*
 * Copyright (c) 2020. 布比（北京）网络技术有限公司.
 * All rights reserved.
 */

package prosayj.thinking.springsecurity.model.response;

/**
 * Rest<T>
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-14 下午 01:23
 * @since 1.0.0
 */
public interface Rest<T> {
    /**
     * 状态码 .
     *
     * @param httpStatus the http status
     */
    void setHttpStatus(int httpStatus);

    /**
     * 数据载体.
     *
     * @param data the data
     */
    void setData(T data);

    /**
     * 提示信息.
     *
     * @param msg the msg
     */
    void setMsg(String msg);

    /**
     * Sets identifier.
     *
     * @param identifier 标识
     */
    void setIdentifier(String identifier);
}
