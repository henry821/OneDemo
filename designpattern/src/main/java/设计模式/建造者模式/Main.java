package 设计模式.建造者模式;

/**
 * Description 建造者模式测试类
 * Author wanghengwei
 * Date   2019/7/3 16:26
 */
public class Main {

    public static void main(String[] args) {

        //建造者模式
        Computer computer = new Computer.Builder()
                .addCpu("Core i5")
                .addMainboard("华硕")
                .addHd("三星EVO")
                .create();
        System.out.println(computer.toString());

    }

}
