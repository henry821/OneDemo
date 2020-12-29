package 设计模式.责任链模式.形式二.interceptors;

import 设计模式.责任链模式.形式二.Interceptor;
import 设计模式.责任链模式.形式二.Request;
import 设计模式.责任链模式.形式二.Response;

/**
 * Created by hengwei on 2020/12/16.
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        System.out.println("CacheInterceptor 开始处理");
        Request request = chain.request();
        request.getParams().append("--").append("CacheInterceptor处理");
        Response response = chain.proceed(request);
        System.out.println("CacheInterceptor 处理结束：" + response.toString());
        return response;
    }
}
