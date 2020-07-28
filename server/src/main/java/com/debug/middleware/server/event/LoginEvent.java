package com.debug.middleware.server.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * <p>
 * 用户登录成功后事件实体
 * </p>
 *
 * @author mu qin
 * @date 2020/7/27
 */
@Getter
@Setter
@ToString
public class LoginEvent extends ApplicationEvent implements Serializable {

    /**
     * 用户名
     */
    private String username;
    /**
     * 登录时间
     */
    private String loginTime;
    /**
     * 所在IP
     */
    private String ip;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LoginEvent(Object source) {
        super(source);
    }

    public LoginEvent(Object source, String username, String loginTime, String ip) {
        super(source);
        this.username = username;
        this.loginTime = loginTime;
        this.ip = ip;
    }
}
