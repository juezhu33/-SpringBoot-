package com.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    // 创建ThreadLocal对象
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Test
    public void testThreadLocalSetAndGet() throws InterruptedException {
        // 使用CountDownLatch确保测试等待所有线程完成
        CountDownLatch latch = new CountDownLatch(2);

        // 线程1
        Thread thread1 = new Thread(() -> {
            try {
                threadLocal.set("Thread1");
                System.out.println(Thread.currentThread().getName() + " 设置值: Thread1");
                System.out.println(Thread.currentThread().getName() + " 获取值: " + threadLocal.get());
            } finally {
                threadLocal.remove(); // 清理ThreadLocal，避免内存泄漏
                latch.countDown();
            }
        }, "Thread1");

        // 线程2
        Thread thread2 = new Thread(() -> {
            try {
                threadLocal.set("Thread2");
                System.out.println(Thread.currentThread().getName() + " 设置值: Thread2");
                System.out.println(Thread.currentThread().getName() + " 获取值: " + threadLocal.get());
            } finally {
                threadLocal.remove(); // 清理ThreadLocal，避免内存泄漏
                latch.countDown();
            }
        }, "Thread2");

        // 启动线程
        thread1.start();
        thread2.start();

        // 等待所有线程完成，最多等待5秒
        latch.await(5, TimeUnit.SECONDS);

        System.out.println("测试完成：每个线程都有自己独立的ThreadLocal值");
    }

}
