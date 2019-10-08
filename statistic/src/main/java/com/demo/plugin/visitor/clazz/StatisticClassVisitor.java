package com.demo.plugin.visitor.clazz;

import com.demo.plugin.visitor.method.StatisticMethodVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Description 类访问器
 * Author wanghengwei
 * Date   2019/9/25 18:16
 */
public class StatisticClassVisitor extends ClassVisitor {

    private String className;

    public StatisticClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new StatisticMethodVisitor(className, Opcodes.ASM5, methodVisitor, access, name, desc);
    }

}
