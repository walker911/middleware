package com.debug.middleware.server.controller;

import com.debug.middleware.server.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/9
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {

    @GetMapping(value = "/info")
    public Book info(Integer bookNo, String bookName) {
        Book book = new Book();
        book.setBookNo(bookNo);
        book.setName(bookName);
        return book;
    }
}
