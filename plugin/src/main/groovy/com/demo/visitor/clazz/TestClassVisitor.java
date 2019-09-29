package com.demo.visitor.clazz;

import com.demo.visitor.method.TestMethodVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Description 类访问器
 * Author wanghengwei
 * Date   2019/9/25 18:16
 */
public class TestClassVisitor extends ClassVisitor {

    public TestClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new TestMethodVisitor(Opcodes.ASM5, methodVisitor, access, name, desc);
    }

}
