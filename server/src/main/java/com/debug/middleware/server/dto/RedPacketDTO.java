package com.debug.middleware.server.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/24
 */
@Data
@ToString
public class RedPacketDTO {

    private Long userId;

    @NotNull
    private Integer total;

    @NotNull
    private Integer amount;

}
