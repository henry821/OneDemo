package 设计模式.责任链模式.形式二;

/**
 * Created by hengwei on 2020/12/16.
 */
public interface Interceptor {

    Response intercept(Chain chain);

    interface Chain {

        Request request();

        Response proceed(Request request);

    }

}
