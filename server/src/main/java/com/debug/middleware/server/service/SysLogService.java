package com.debug.middleware.server.service;

import com.debug.middleware.server.dto.UserLoginDTO;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/26
 */
public interface SysLogService {

    void recordLog(UserLoginDTO dto);

}
