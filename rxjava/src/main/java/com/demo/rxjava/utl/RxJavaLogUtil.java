package com.demo.rxjava.utl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description
 * Author wanghengwei
 * Date   2020/5/12
 */
class RxJavaLogUtil {

    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());

    static void printOnSubscribe() {
        printLog("onSubscribe");
    }

    static <T> void printOnNext(T content) {
        printLog("onNext : " + content);
    }

    static <T> void printOnSuccess(T content) {
        printLog("onSuccess : " + content);
    }

    static void printOnComplete() {
        printLog("onComplete");
    }

    static void printOnError(Throwable throwable) {
        printLog("onError : " + throwable.getMessage());
    }

    private static void printLog(String content) {
        String log = String.format("<%s> %s --> 当前线程：%s", formatter.format(new Date()), content, Thread.currentThread().getName());
        System.out.println(log);
    }

}
