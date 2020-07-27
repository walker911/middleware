package com.debug.middleware.server;

import com.debug.middleware.server.event.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/27
 */
@SpringBootTest
public class EventTest {

    @Autowired
    private Publisher publisher;

    @Test
    public void testEvent() {
        publisher.sendMsg();
    }
}
