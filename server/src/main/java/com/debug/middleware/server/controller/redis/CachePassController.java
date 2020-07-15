package com.debug.middleware.server.controller.redis;

import com.debug.middleware.model.entity.Item;
import com.debug.middleware.server.common.ResultBean;
import com.debug.middleware.server.service.redis.CachePassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/15
 */
@RestController
@RequestMapping(value = "/cache/pass/")
public class CachePassController {

    private final CachePassService cachePassService;

    public CachePassController(CachePassService cachePassService) {
        this.cachePassService = cachePassService;
    }

    @GetMapping(value = "item/info")
    public ResultBean<Item> getItem(@RequestParam String code) throws Exception {
        Item item = cachePassService.getItemInfo(code);

        return ResultBean.success(item);
    }
}
