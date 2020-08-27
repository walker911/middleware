package com.debug.middleware.model.dto;

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
 * @date 2020/8/27
 */
@Getter
@Setter
@ToString
public class PraiseRankDTO implements Serializable {

    private static final long serialVersionUID = 2813165587389227835L;

    private Integer blogId;

    private Long total;

    public PraiseRankDTO() {
    }

    public PraiseRankDTO(Integer blogId, Long total) {
        this.blogId = blogId;
        this.total = total;
    }
}
