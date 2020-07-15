package com.debug.middleware.server.service.redis.impl;

import com.debug.middleware.model.entity.Item;
import com.debug.middleware.model.mapper.ItemMapper;
import com.debug.middleware.server.service.redis.CachePassService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/15
 */
@Slf4j
@Service
public class CachePassServiceImpl implements CachePassService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ItemMapper itemMapper;
    private final ObjectMapper objectMapper;
    private static final String KEY_PREFIX = "item:";

    public CachePassServiceImpl(RedisTemplate<String, String> redisTemplate, ItemMapper itemMapper, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.itemMapper = itemMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Item getItemInfo(String code) throws Exception {
        Item item = null;

        final String key = KEY_PREFIX + code;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Boolean isExist = redisTemplate.hasKey(key);
        if (isExist != null && isExist) {
            String res = valueOperations.get(key);
            if (StringUtils.isNotBlank(res)) {
                item = objectMapper.readValue(res, Item.class);
            }
        } else {
            item = itemMapper.selectByCode(code);
            if (item != null) {
                valueOperations.set(key, objectMapper.writeValueAsString(item));
            } else {
                valueOperations.set(key, "", 30L, TimeUnit.MINUTES);
            }
        }

        return item;
    }
}
