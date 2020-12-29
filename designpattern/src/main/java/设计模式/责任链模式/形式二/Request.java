package 设计模式.责任链模式.形式二;

/**
 * Created by hengwei on 2020/12/16.
 */
public class Request {

    private final StringBuilder params;

    public Request(StringBuilder params) {
        this.params = params;
    }

    public StringBuilder getParams() {
        return params;
    }

    @Override
    public String toString() {
        return String.format("%s , 请求参数：%s", getClass().getSimpleName(), params.toString());
    }
}
