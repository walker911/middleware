package com.debug.middleware.server.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/26
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BloomDTO implements Serializable {

    private static final long serialVersionUID = 8911516220674562651L;

    private Integer id;

    private String msg;

    public BloomDTO() {
    }

    public BloomDTO(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
