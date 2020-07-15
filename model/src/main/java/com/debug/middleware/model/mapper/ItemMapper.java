package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.Item;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/15
 */
public interface ItemMapper {

    Item selectByCode(@Param("code") String code);

}
