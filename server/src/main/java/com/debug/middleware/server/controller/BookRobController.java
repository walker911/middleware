package com.debug.middleware.server.controller;

import com.debug.middleware.api.BaseResponse;
import com.debug.middleware.api.enums.StatusCode;
import com.debug.middleware.server.dto.BookRobDTO;
import com.debug.middleware.server.service.BookRobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍抢购Controller
 *
 * @author walker
 * @date 2020/9/6
 */
@RestController
public class BookRobController {

    private final BookRobService bookRobService;

    public BookRobController(BookRobService bookRobService) {
        this.bookRobService = bookRobService;
    }

    @PostMapping(value = "book/rob/request")
    public BaseResponse<String> takeMoney(@RequestBody BookRobDTO dto) {
        BaseResponse<String> response = new BaseResponse<>(StatusCode.SUCCESS);
        try {
            bookRobService.robWithNoLock(dto);
        } catch (Exception e) {
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), e.getMessage());
        }
        return response;
    }
}
