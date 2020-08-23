package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.UserAccount;
import org.apache.ibatis.annotations.Param;

/**
 * @author walker
 * @date 2020/8/23
 */
public interface UserAccountMapper {

    UserAccount selectByUserId(@Param("userId") Integer userId);

    UserAccount selectByUserIdLock(@Param("userId") Integer userId);

    int updateAmount(@Param("money") Double money, @Param("id") Integer id);

    int updateAmountByVersion(@Param("money") Double money, @Param("id") Integer id, @Param("version") Integer version);

    int updateAmountLock(@Param("money") Double money, @Param("id") Integer id);
}
