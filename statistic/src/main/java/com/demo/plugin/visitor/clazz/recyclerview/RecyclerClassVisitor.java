package com.demo.plugin.visitor.clazz.recyclerview;

import com.demo.plugin.visitor.clazz.BaseClassVisitor;
import com.demo.plugin.visitor.method.recyclerview.RecyclerMethodVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Description RecyclerView内部类Recycler类访问器
 * Author wanghengwei
 * Date   2019/10/29 17:55
 */
public class RecyclerClassVisitor extends BaseClassVisitor {

    public RecyclerClassVisitor(ClassVisitor cv) {
        super(cv);
    }

    @Override
    protected boolean setPrintLogFlag() {
        return className.equals("android/support/v7/widget/RecyclerView$Recycler");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new RecyclerMethodVisitor(className, Opcodes.ASM5, methodVisitor, access, name, desc);
    }
}
