package com.debug.middleware.server.service.redis;

import com.debug.middleware.model.entity.Item;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/15
 */
public interface CachePassService {

    Item getItemInfo(String code) throws Exception;
}
