package 设计模式.建造者模式;

/**
 * Description 计算机，使用建造者模式创建
 * Author wanghengwei
 * Date   2019/7/3 14:30
 */
public class Computer {

    private String mCpu; //cpu
    private String mMainBoard; //主板
    private String mHd; //硬盘

    private Computer(Builder builder) {
        mCpu = builder.cpu;
        mMainBoard = builder.mainboard;
        mHd = builder.hd;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "mCpu='" + mCpu + '\'' +
                ", mMainBoard='" + mMainBoard + '\'' +
                ", mHd='" + mHd + '\'' +
                '}';
    }

    public static final class Builder {
        String cpu;
        String mainboard;
        String hd;

        public Builder addCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder addMainboard(String mainboard) {
            this.mainboard = mainboard;
            return this;
        }

        public Builder addHd(String hd) {
            this.hd = hd;
            return this;
        }

        public Computer create() {
            return new Computer(this);
        }

    }

}
