package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.RedDetail;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/16
 */
public interface RedDetailMapper {

    void save(RedDetail detail);

    int batchSave(List<RedDetail> details);
}
