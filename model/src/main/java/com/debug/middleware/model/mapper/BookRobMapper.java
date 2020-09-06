package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.BookRob;
import org.apache.ibatis.annotations.Param;

/**
 * @author walker
 * @date 2020/9/6
 */
public interface BookRobMapper {

    int save(BookRob bookRob);

    int countByBookNoUserId(@Param("userId") Integer userId, @Param("bookNo") String bookNo);
}
