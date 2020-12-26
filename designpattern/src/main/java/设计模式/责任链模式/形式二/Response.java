package 设计模式.责任链模式.形式二;

/**
 * Created by hengwei on 2020/12/16.
 */
public class Response {

    public Request request;

    public Response(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "{" +
                "\"request\":" + request.toString() +
                '}';
    }
}
