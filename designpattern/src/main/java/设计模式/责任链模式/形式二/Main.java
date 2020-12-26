package 设计模式.责任链模式.形式二;

import java.util.ArrayList;
import java.util.List;

import 设计模式.责任链模式.形式二.interceptors.BridgeInterceptor;
import 设计模式.责任链模式.形式二.interceptors.CacheInterceptor;
import 设计模式.责任链模式.形式二.interceptors.CallServerInterceptor;

/**
 * Created by hengwei on 2020/12/16.
 */
public class Main {

    public static void main(String[] args) {

        Main main = new Main();
        Response response = main.getResponseWithInterceptorChain();

    }

    public Response getResponseWithInterceptorChain() {

        StringBuilder builder = new StringBuilder();
        builder.append("原始请求参数").append("\r\n");
        Request originalRequest = new Request(builder);

        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new BridgeInterceptor());
        interceptors.add(new CacheInterceptor());
        interceptors.add(new CallServerInterceptor());

        Interceptor.Chain chain = new RealInterceptorChain(interceptors, 0, originalRequest);

        return chain.proceed(originalRequest);
    }

    public static class RealInterceptorChain implements Interceptor.Chain {

        private List<Interceptor> interceptors;
        private int index;
        private Request request;

        public RealInterceptorChain(List<Interceptor> interceptors, int index, Request request) {
            this.interceptors = interceptors;
            this.index = index;
            this.request = request;
        }

        @Override
        public Request request() {
            return request;
        }

        @Override
        public Response proceed(Request request) {
            RealInterceptorChain next = new RealInterceptorChain(interceptors, index + 1, request);
            Interceptor interceptor = interceptors.get(index);
            Response response = interceptor.intercept(next);
            return response;
        }

    }


}
