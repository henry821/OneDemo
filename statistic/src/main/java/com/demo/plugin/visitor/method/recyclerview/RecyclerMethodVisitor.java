package com.demo.plugin.visitor.method.recyclerview;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Description RecyclerView内部类Recycler方法访问器
 * Author wanghengwei
 * Date   2019/9/25 18:23
 */
public class RecyclerMethodVisitor extends AdviceAdapter {

    private static final String METHOD_Try_Get_ViewHolder_For_Position_By_Deadline = "tryGetViewHolderForPositionByDeadline";

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
    public RecyclerMethodVisitor(String className, int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.className = className;
        methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        if (METHOD_Try_Get_ViewHolder_For_Position_By_Deadline.equals(methodName)) {
            //打印当前方法名
            mv.visitLdcInsn("[ASM埋点]当前方法：" + methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/baselibrary/utils/LogUtil", "w", "(Ljava/lang/String;)V", false);
            // ----------------------------
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (METHOD_Try_Get_ViewHolder_For_Position_By_Deadline.equals(methodName)) {
            //把Recycler的mAttachedScrap缓存设置给Manager
            mv.visitMethodInsn(INVOKESTATIC, "com/baselibrary/manager/RecyclerViewRecordManager", "getInstance", "()Lcom/baselibrary/manager/RecyclerViewRecordManager;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "android/support/v7/widget/RecyclerView$Recycler", "mAttachedScrap", "Ljava/util/ArrayList;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/baselibrary/manager/RecyclerViewRecordManager", "setAttachedScrap", "(Ljava/util/List;)V", false);
            //把Recycler的mCachedViews缓存设置给Manager
            mv.visitMethodInsn(INVOKESTATIC, "com/baselibrary/manager/RecyclerViewRecordManager", "getInstance", "()Lcom/baselibrary/manager/RecyclerViewRecordManager;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "android/support/v7/widget/RecyclerView$Recycler", "mCachedViews", "Ljava/util/ArrayList;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/baselibrary/manager/RecyclerViewRecordManager", "setCachedViews", "(Ljava/util/List;)V", false);
        }
    }
}
