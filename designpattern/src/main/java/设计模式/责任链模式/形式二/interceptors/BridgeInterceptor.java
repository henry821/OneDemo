package 设计模式.责任链模式.形式二.interceptors;

import 设计模式.责任链模式.形式二.Interceptor;
import 设计模式.责任链模式.形式二.Request;
import 设计模式.责任链模式.形式二.Response;

/**
 * Created by hengwei on 2020/12/16.
 */
public class BridgeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        request.getParams().append("BridgeInterceptor处理").append("\r\n");
        return new Response(request);
    }
}
