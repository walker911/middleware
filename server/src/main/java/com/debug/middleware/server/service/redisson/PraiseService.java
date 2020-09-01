package com.debug.middleware.server.service.redisson;

import com.debug.middleware.model.dto.PraiseRankDTO;
import com.debug.middleware.server.dto.PraiseDTO;

import java.util.Collection;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/27
 */
public interface PraiseService {

    void addPraise(PraiseDTO dto) throws Exception;

    void addPraiseLock(PraiseDTO dto) throws Exception;

    void cancelPraise(PraiseDTO dto) throws Exception;

    Long getBlogPraiseTotal(Integer blogId);

    Collection<PraiseRankDTO> getRankNoRedisson();

    Collection<PraiseRankDTO> getRankWithRedisson();

}
