package com.debug.middleware.server.distribute.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 锁机制
 * </p>
 *
 * @author mu qin
 * @date 2020/8/21
 */
@Slf4j
public class LockOne {
    public static void main(String[] args) {
        Thread tAdd = new Thread(new LockThread(100), "add");
        Thread tSub = new Thread(new LockThread(-100), "sub");
        tAdd.start();
        tSub.start();
    }

    static class LockThread implements Runnable {

        private int count;

        public LockThread(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    SysConstant.amount = SysConstant.amount + count;
                    log.info("此时账户余额为：{}", SysConstant.amount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
