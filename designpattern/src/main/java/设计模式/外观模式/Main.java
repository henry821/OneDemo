package 设计模式.外观模式;

/**
 * Description 外观模式测试类
 * Author wanghengwei
 * Date   2019/7/3 16:18
 */
public class Main {

    private Light mLight;
    private Television mTv;

    private Facade mFacade;

    public static void main(String[] args) {
        Main test = new Main();

        test.mLight = new Light();
        test.mTv = new Television();
        test.mFacade = new Facade(test.mLight, test.mTv);

        //使用外观模式前
        System.out.println("使用外观模式前");
        test.mLight.turnOn();
        test.mTv.turnOn();
        test.mTv.turnOff();
        test.mLight.turnOff();

        //使用外观模式后
        System.out.println("使用外观模式后");
        test.mFacade.turnOn();
        test.mFacade.turnOff();
    }

}
