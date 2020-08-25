package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.UserReg;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/25
 */
public interface UserRegMapper {

    int save(UserReg userReg);

    UserReg selectByUsername(@Param("username") String username);
}
