package com.debug.middleware.server.controller.redisson;

import com.debug.middleware.api.BaseResponse;
import com.debug.middleware.api.enums.StatusCode;
import com.debug.middleware.model.dto.PraiseRankDTO;
import com.debug.middleware.server.dto.PraiseDTO;
import com.debug.middleware.server.service.redisson.PraiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/27
 */
@Slf4j
@RestController
public class PraiseController {

    private final PraiseService praiseService;

    public PraiseController(PraiseService praiseService) {
        this.praiseService = praiseService;
    }

    /**
     * 点赞
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/blog/praise/add")
    public BaseResponse<Map<String, Object>> addPraise(@RequestBody PraiseDTO dto) {
        BaseResponse<Map<String, Object>> response = new BaseResponse<>(StatusCode.SUCCESS);
        Map<String, Object> params = new HashMap<>();

        try {
            praiseService.addPraise(dto);
            long total = praiseService.getBlogPraiseTotal(dto.getBlogId());
            params.put("total", total);
        } catch (Exception e) {
            log.error("点赞博客-发生异常：{}", dto, e.fillInStackTrace());
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), e.getMessage());
        }
        response.setData(params);

        return response;
    }

    /**
     * 取消点赞
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/blog/praise/cancel")
    public BaseResponse<Map<String, Object>> cancelPraise(@RequestBody PraiseDTO dto) {
        BaseResponse<Map<String, Object>> response = new BaseResponse<>(StatusCode.SUCCESS);
        Map<String, Object> params = new HashMap<>();

        try {
            praiseService.cancelPraise(dto);
            long total = praiseService.getBlogPraiseTotal(dto.getBlogId());
            params.put("total", total);
        } catch (Exception e) {
            log.error("取消点赞博客-发生异常：{}", dto, e.fillInStackTrace());
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), e.getMessage());
        }
        response.setData(params);

        return response;
    }

    /**
     * 排行榜
     *
     * @return
     */
    @GetMapping(value = "/blog/praise/rank")
    public BaseResponse<Collection<PraiseRankDTO>> rank() {
        BaseResponse<Collection<PraiseRankDTO>> response = new BaseResponse<>(StatusCode.SUCCESS);

        try {
            response.setData(praiseService.getRankWithRedisson());
        } catch (Exception e) {
            log.error("获取排行榜-发生异常", e.fillInStackTrace());
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), e.getMessage());
        }

        return response;
    }
}
