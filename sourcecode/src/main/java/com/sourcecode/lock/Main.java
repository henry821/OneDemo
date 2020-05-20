package com.sourcecode.lock;

/**
 * Description
 * Author wanghengwei
 * Date   2020/5/20
 */
public class Main {

    public static void main(String[] args) {
        final Message message = new Message();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    message.sendMessage();
                }
            }).start();
        }
    }

}
