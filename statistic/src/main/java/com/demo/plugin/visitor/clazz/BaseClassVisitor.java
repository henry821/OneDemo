package com.demo.plugin.visitor.clazz;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

import java.util.Arrays;

/**
 * Description 类访问器基类，每个方法上添加了注释，方法按照调用顺序排好序
 * Author wanghengwei
 * Date   2019/9/25 18:16
 */
public abstract class BaseClassVisitor extends ClassVisitor {

    /**
     * 打印log总开关
     */
    private boolean mainLogController;
    /**
     * 最终决定是否可以打印log，计算方式：总开关 & 子类开关
     */
    private boolean canPrintLog;

    protected String className;

    public BaseClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
        mainLogController = false;
    }

    /**
     * Visits the header of the class.
     * 访问类头部
     *
     * @param version    the class version
     *                   类版本
     * @param access     the class's access flags (see Opcodes). This parameter also indicates if the class is deprecated.
     *                   类的访问标志位，这个参数也显示类是否过期
     * @param name       the internal name of the class (see getInternalName).
     *                   类名称
     * @param signature  the signature of this class. May be null if the class is not a generic one, and does not extend or implement generic classes or interfaces.
     *                   类签名，这个值可能为null当这个类不是一般的类，而且没有继承一般的类或者实现一般的接口
     * @param superName  the internal of name of the super class (see getInternalName). For interfaces, the super class is Object. May be null, but only for the Object class.
     *                   父类名称，对于接口其父类是Object，只有Object类的这个值是null
     * @param interfaces the internal names of the class's interfaces (see getInternalName). May be null.
     *                   类接口的名称，可能为null
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        className = name;
        canPrintLog = mainLogController & setPrintLogFlag();
        if (canPrintLog) {
            String builder = "visit(" +
                    version + "," +
                    access + "," +
                    name + "," +
                    signature + "," +
                    superName + "," +
                    Arrays.toString(interfaces) +
                    ")";
            System.out.println(builder);
        }
    }

    /**
     * Visits the source of the class.
     * 访问该类的来源
     *
     * @param source the name of the source file from which the class was compiled. May be null.
     *               被编译的该类的来源名称，可能为null
     * @param debug  additional debug information to compute the correspondance between source and compiled elements of the class. May be null.
     *               为了计算该类来源和编译元素间联系所附加的调试信息，可能为null
     */
    @Override
    public void visitSource(String source, String debug) {
        if (canPrintLog) {
            String builder = "visitSource(" +
                    source + "," +
                    debug + ")";
            System.out.println(builder);
        }
        super.visitSource(source, debug);
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        if (canPrintLog) {
            String builder = "visitModule(" +
                    name + "," +
                    access + "," +
                    version + ")";
            System.out.println(builder);
        }
        return super.visitModule(name, access, version);
    }

    /**
     * Visits the enclosing class of the class. This method must be called only if the class has an enclosing class.
     * 访问该类的外部类，当该类有外部类时此方法一定会被调用
     *
     * @param owner internal name of the enclosing class of the class.
     *              该类外部类的名称
     * @param name  the name of the method that contains the class, or null if the class is not enclosed in a method of its enclosing class.
     *              包含该类的方法名，当该类不在其外部类的方法里时为null
     * @param desc  the descriptor of the method that contains the class, or null if the class is not enclosed in a method of its enclosing class.
     *              包含该类的方法的描述，当该类不在其外部类的方法里时为null
     */
    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        if (canPrintLog) {
            String builder = "visitOuterClass(" +
                    owner + "," +
                    name + "," +
                    desc + ")";
            System.out.println(builder);
        }
        super.visitOuterClass(owner, name, desc);
    }

    /**
     * Visits an annotation of the class.
     * 访问该类的注解
     *
     * @param desc    the class descriptor of the annotation class.
     *                该类注解的描述
     * @param visible true if the annotation is visible at runtime.
     *                如果是运行时注解为true
     * @return a visitor to visit the annotation values, or null if this visitor is not interested in visiting this annotation.
     * 一个访问注解值的访问器，如果不想访问注解可以为null
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (canPrintLog) {
            String builder = "visitAnnotation(" +
                    desc + "," +
                    visible + ")";
            System.out.println(builder);
        }
        return super.visitAnnotation(desc, visible);
    }

    /**
     * Visits an annotation on a type in the class signature.
     *
     * @param typeRef  a reference to the annotated type. The sort of this type reference must be CLASS_TYPE_PARAMETER, CLASS_TYPE_PARAMETER_BOUND or CLASS_EXTENDS. See TypeReference.
     * @param typePath the path to the annotated type argument, wildcard bound, array element type, or static inner type within 'typeRef'. May be null if the annotation targets 'typeRef' as a whole.
     * @param desc     the class descriptor of the annotation class.
     * @param visible  true if the annotation is visible at runtime.
     * @return a visitor to visit the annotation values, or null if this visitor is not interested in visiting this annotation.
     */
    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        if (canPrintLog) {
            String builder = "visitTypeAnnotation(" +
                    typeRef + "," +
                    typePath.toString() + "," +
                    desc + "," +
                    visible + ")";
            System.out.println(builder);
        }
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    /**
     * Visits a non standard attribute of the class.
     * 访问该类不标准的属性
     *
     * @param attr an attribute.
     *             属性
     */
    @Override
    public void visitAttribute(Attribute attr) {
        if (canPrintLog) {
            String builder = "visitAttribute(" +
                    attr.toString() + ")";
            System.out.println(builder);
        }
        super.visitAttribute(attr);
    }

    /**
     * Visits information about an inner class. This inner class is not necessarily a member of the class being visited.
     * 访问一个内部类的信息。这个内部类不一定是该类的一个成员
     *
     * @param name      the internal name of an inner class (see getInternalName).
     *                  内部类的名称
     * @param outerName the internal name of the class to which the inner class belongs (see getInternalName). May be null for not member classes.
     *                  这个内部类所属的类名称。不是成员类时为null
     * @param innerName the (simple) name of the inner class inside its enclosing class. May be null for anonymous inner classes.
     *                  这个内部类在包裹它的类内部的名称(简称)，如果是匿名内部类则为null
     * @param access    the access flags of the inner class as originally declared in the enclosing class.
     *                  这个内部类定义在包裹它的类中的标志符
     */
    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        if (canPrintLog) {
            String builder = "visitInnerClass(" +
                    name + "," +
                    outerName + "," +
                    innerName + "," +
                    access + ")";
            System.out.println(builder);
        }
        super.visitInnerClass(name, outerName, innerName, access);
    }

    /**
     * Visits a field of the class.
     * 访问该类的一个成员变量
     *
     * @param access    the field's access flags (see Opcodes). This parameter also indicates if the field is synthetic and/or deprecated.
     *                  这个成员变量的访问标志符，这个参数也指示是否过期
     * @param name      the field's name.
     *                  成员变量名称
     * @param desc      the field's descriptor (see Type).
     *                  成员变量描述
     * @param signature the field's signature. May be null if the field's type does not use generic types.
     *                  成员变量签名，如果这个成员变量不是一般的类型时为null
     * @param value     the field's initial value. This parameter, which may be null if the field does not have an initial value, must be an Integer, a Float, a Long, a Double or a String (for int, float, long or String fields respectively). This parameter is only used for static fields. Its value is ignored for non static fields, which must be initialized through bytecode instructions in constructors or methods.
     *                  这个成员变量的初始值，没有初始值时为null
     * @return a visitor to visit field annotations and attributes, or null if this class visitor is not interested in visiting these annotations and attributes.
     * 一个访问该成员变量注解和属性的访问器，如果不想访问可以为null
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (canPrintLog) {
            String builder = "visitField(" +
                    access + "," +
                    name + "," +
                    desc + "," +
                    signature + "," +
                    value + ")";
            System.out.println(builder);
        }
        return super.visitField(access, name, desc, signature, value);
    }

    /**
     * Visits a method of the class. This method must return a new MethodVisitor instance (or null) each time it is called, i.e., it should not return a previously returned visitor.
     * 访问该类的一个方法。每次访问都必须返回一个新的MethodVisitor实例(也可为null)，举例：不能返回一个之前返回过的MethodVisitor实例
     *
     * @param access     the method's access flags (see Opcodes). This parameter also indicates if the method is synthetic and/or deprecated.
     *                   方法的访问标志符。这个参数也能指示这个方法是否是合成的(和/或)过期的
     * @param name       the method's name.
     *                   方法名
     * @param desc       the method's descriptor (see Type).
     *                   方法描述
     * @param signature  the method's signature. May be null if the method parameters, return type and exceptions do not use generic types.
     *                   方法签名。当方法参数、返回类型和异常不是一般类型时为null
     * @param exceptions the internal names of the method's exception classes (see getInternalName). May be null.
     *                   方法异常类的名称，可能为null
     * @return an object to visit the byte code of the method, or null if this class visitor is not interested in visiting the code of this method.
     * 一个访问方法字节码的对象，如果不想访问方法可以为null
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (canPrintLog) {
            String builder = "visitMethod(" +
                    access + "," +
                    name + "," +
                    desc + "," +
                    signature + "," +
                    Arrays.toString(exceptions) + ")";
            System.out.println(builder);
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    /**
     * Visits the end of the class. This method, which is the last one to be called, is used to inform the visitor that all the fields and methods of the class have been visited.
     * 访问该类结束。这个方法最后一个被调用，用来告知用户该类所有的成员变量和方法都已被访问过
     */
    @Override
    public void visitEnd() {
        if (canPrintLog) {
            System.out.println("visitEnd()");
        }
        super.visitEnd();
    }

    protected abstract boolean setPrintLogFlag();
}
