package com.demo.visitor.method;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Description 方法访问器
 * Author wanghengwei
 * Date   2019/9/25 18:23
 */
public class TestMethodVisitor extends AdviceAdapter {

    private String methodName;
    private boolean shouldInject;

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
    public TestMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        //如果注入代码标志位为true，则在进入该方法时插入一行打印log代码
        if (shouldInject) {
            mv.visitLdcInsn("这是注入代码，当前方法：" + methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/baselibrary/utils/LogUtil", "e", "(Ljava/lang/String;)V", false);
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        //碰到自定义注解时将注入代码标志位值为true
        shouldInject = "Lcom/baselibrary/annotation/WhwTest;".equals(desc);
        return super.visitAnnotation(desc, visible);
    }
}
