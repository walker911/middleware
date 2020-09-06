package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.BookStock;
import org.apache.ibatis.annotations.Param;

/**
 * @author walker
 * @date 2020/9/6
 */
public interface BookStockMapper {

    BookStock selectByBookNo(@Param("bookNo") String bookNo);

    int updateStock(@Param("bookNo") String bookNo);

    int updateStockWithLock(@Param("bookNo") String bookNo);
}
