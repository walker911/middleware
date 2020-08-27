package com.debug.middleware.model.mapper;

import com.debug.middleware.model.dto.PraiseRankDTO;
import com.debug.middleware.model.entity.Praise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/27
 */
public interface PraiseMapper {

    int save(Praise praise);

    Praise selectByBlogUserId(@Param("userId") Integer userId, @Param("blogId") Integer blogId);

    int countByBlogId(@Param("blogId") Integer blogId);

    int cancelPraise(@Param("blogId") Integer blogId, @Param("userId") Integer userId);

    List<PraiseRankDTO> getPraiseRank();

}
