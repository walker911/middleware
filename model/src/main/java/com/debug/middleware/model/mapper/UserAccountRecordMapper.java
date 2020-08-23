package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.UserAccountRecord;

/**
 * @author walker
 * @date 2020/8/23
 */
public interface UserAccountRecordMapper {

    /**
     * 保存
     * @param record
     * @return
     */
    int save(UserAccountRecord record);
}
