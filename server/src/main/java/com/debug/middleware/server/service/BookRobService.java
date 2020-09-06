package com.debug.middleware.server.service;

import com.debug.middleware.server.dto.BookRobDTO;

/**
 * @author walker
 * @date 2020/9/6
 */
public interface BookRobService {

    void robWithNoLock(BookRobDTO dto);

    void robWithLock(BookRobDTO dto);
}
