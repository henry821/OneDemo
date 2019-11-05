package com.demo.plugin.visitor.clazz;

import org.objectweb.asm.ClassVisitor;

/**
 * Description 没有操作的ClassVisitor
 * Author wanghengwei
 * Date   2019/10/29 19:25
 */
public class NoOpClassVisitor extends BaseClassVisitor {

    public NoOpClassVisitor(ClassVisitor cv) {
        super(cv);
    }

    @Override
    protected boolean setPrintLogFlag() {
        return false;
    }
}
