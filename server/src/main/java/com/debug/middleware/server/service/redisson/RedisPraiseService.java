package com.debug.middleware.server.service.redisson;

import com.debug.middleware.model.dto.PraiseRankDTO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/27
 */
public interface RedisPraiseService {

    void cachePraiseBlog(Integer blogId, Integer userId, Integer status) throws Exception;

    Long getCacheTotalBlog(Integer blogId);

    void rankBlogPraise();

    List<PraiseRankDTO> getBlogPraiseRank();

}
