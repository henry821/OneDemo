package 设计模式.责任链模式.形式二.interceptors;

import 设计模式.责任链模式.形式二.Interceptor;
import 设计模式.责任链模式.形式二.Request;
import 设计模式.责任链模式.形式二.Response;

/**
 * Created by hengwei on 2020/12/16.
 */
public class CallServerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        System.out.println("CallServerInterceptor 开始处理");
        Request request = chain.request();
        request.getParams().append("--").append("CallServerInterceptor处理");
        Response response = genResponse(request);
        System.out.println("CallServerInterceptor 处理结束：" + response.toString());
        return response;
    }

    private Response genResponse(Request request) {
        return new Response(request);
    }
}
