package com.demo.plugin.visitor.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Description 方法访问器
 * Author wanghengwei
 * Date   2019/9/25 18:23
 */
public class StatisticMethodVisitor extends AdviceAdapter {

    private String className;
    private String methodName;

    /**
     * Creates a new {@link AdviceAdapter}.
     *
     * @param api    the ASM API version implemented by this visitor. Must be one
     *               of {@link Opcodes#ASM4}, {@link Opcodes#ASM5} or {@link Opcodes#ASM6}.
     * @param mv     the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name   the method's name.
     * @param desc   the method's descriptor (see {@link Type Type}).
     */
    public StatisticMethodVisitor(String className, int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.className = className;
        methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        //如果进入构造方法，则打印当前进入的类名
        if ("<init>".equals(methodName)) {
            mv.visitLdcInsn("[ASM埋点]当前类：" + className);
            mv.visitMethodInsn(INVOKESTATIC, "com/baselibrary/utils/LogUtil", "i", "(Ljava/lang/String;)V", false);
        }
        //打印当前方法名
        mv.visitLdcInsn("[ASM埋点]当前方法：" + methodName);
        mv.visitMethodInsn(INVOKESTATIC, "com/baselibrary/utils/LogUtil", "w", "(Ljava/lang/String;)V", false);
    }

}
