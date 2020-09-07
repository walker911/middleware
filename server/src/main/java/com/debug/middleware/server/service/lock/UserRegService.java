package com.debug.middleware.server.service.lock;

import com.debug.middleware.server.dto.UserRegDTO;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/25
 */
public interface UserRegService {

    void userRegNoLock(UserRegDTO dto);

    void userRegWithLock(UserRegDTO dto);

    void userRegRedisson(UserRegDTO dto);

    void userRegWithZKLock(UserRegDTO dto) throws Exception;
}
