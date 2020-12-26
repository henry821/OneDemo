package 设计模式.外观模式;

/**
 * Description 外观模式
 * Author wanghengwei
 * Date   2019/7/3 16:14
 */
public class Facade {

    private Light mLight;
    private Television mTelevision;

    public Facade(Light mLight, Television mTelevision) {
        this.mLight = mLight;
        this.mTelevision = mTelevision;
    }

    public void turnOn() {
        mLight.turnOn();
        mTelevision.turnOn();
    }

    public void turnOff() {
        mTelevision.turnOff();
        mLight.turnOff();
    }
}
