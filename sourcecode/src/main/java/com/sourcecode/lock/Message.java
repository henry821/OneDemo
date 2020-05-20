package com.sourcecode.lock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description 信息类，模拟发送信息
 * Author wanghengwei
 * Date   2020/5/20
 */
public class Message {

    private String pattern = "yyyy-MM-dd HH:mm:ss";
    private DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());

    private ReentrantLock lock = new ReentrantLock();

    void sendMessage() {
        System.out.println(String.format("<%s> 线程 %s 开始编辑【信息】",
                formatter.format(new Date()), Thread.currentThread().getName()));
        lock.lock();
        try {
            System.out.println(String.format("<%s> 线程 %s 获得锁，开始发送【信息】",
                    formatter.format(new Date()), Thread.currentThread().getName()));
            sendEmail();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("<%s> 线程 %s 发送【信息】方法释放锁",
                    formatter.format(new Date()), Thread.currentThread().getName()));
            lock.unlock();
        }
    }

    public void sendEmail() {
        System.out.println(String.format("<%s> 线程 %s 开始编辑【邮件】",
                formatter.format(new Date()), Thread.currentThread().getName()));
        lock.lock();
        try {
            System.out.println(String.format("<%s> 线程 %s 获得锁，开始发送【邮件】",
                    formatter.format(new Date()), Thread.currentThread().getName()));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("<%s> 线程 %s 发送【邮件】方法释放锁",
                    formatter.format(new Date()), Thread.currentThread().getName()));
            lock.unlock();
        }
    }

}
