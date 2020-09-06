package com.debug.middleware.server.service.impl;

import com.debug.middleware.model.entity.BookRob;
import com.debug.middleware.model.entity.BookStock;
import com.debug.middleware.model.mapper.BookRobMapper;
import com.debug.middleware.model.mapper.BookStockMapper;
import com.debug.middleware.server.dto.BookRobDTO;
import com.debug.middleware.server.service.BookRobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 书籍抢购服务处理类
 *
 * @author walker
 * @date 2020/9/6
 */
@Slf4j
@Service
public class BookRobServiceImpl implements BookRobService {

    private final BookStockMapper bookStockMapper;
    private final BookRobMapper bookRobMapper;

    public BookRobServiceImpl(BookStockMapper bookStockMapper, BookRobMapper bookRobMapper) {
        this.bookStockMapper = bookStockMapper;
        this.bookRobMapper = bookRobMapper;
    }

    /**
     * 书籍抢购 - 不加锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void robWithNoLock(BookRobDTO dto) {
        BookStock stock = bookStockMapper.selectByBookNo(dto.getBookNo());
        int count = bookRobMapper.countByBookNoUserId(dto.getUserId(), dto.getBookNo());
        if (stock != null && stock.getStock() > 0 && count <= 0) {
            log.info("处理书籍抢购-不加锁-当前信息：{}", dto);
            int res = bookStockMapper.updateStock(dto.getBookNo());
            if (res > 0) {
                BookRob entity = new BookRob();
                entity.setUserId(dto.getUserId());
                entity.setBookNo(dto.getBookNo());
                entity.setRobTime(LocalDateTime.now());
                bookRobMapper.save(entity);
            }
        } else {
            throw new RuntimeException("该书籍库存不足");
        }

    }

    @Override
    @Transactional
    public void robWithLock(BookRobDTO dto) {

    }
}
