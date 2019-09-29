package com.demo.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.demo.visitor.clazz.TestClassVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

public class StatisticsTransform extends Transform {

    @Override
    String getName() {
        return "Statistics"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println "Transform开始"
        transformInvocation.inputs.each { TransformInput input ->
            //遍历directory
            input.directoryInputs.each { DirectoryInput directoryInput ->
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class")
                                && !name.endsWith("R.class")
                                && !name.endsWith("BuildConfig.class")
                                && !name.contains("R\$")) {
                            handleCurrentClassFile(file)
                        }
                    }
                }
                //把修改后的文件写回
                File dest = transformInvocation.outputProvider.getContentLocation(
                        directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.getFile(), dest)
            }
            //遍历jar
            input.jarInputs.each {
                JarInput jarInput ->
                    //把修改后的文件写回
                    String jarName = jarInput.name
                    String md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
                    if (jarName.endsWith(".jar")) {
                        jarName = jarName.substring(0, jarName.length() - 4)
                    }
                    File dest = transformInvocation.outputProvider.getContentLocation(
                            jarName + md5Name,
                            jarInput.contentTypes,
                            jarInput.scopes,
                            Format.JAR)
                    FileUtils.copyFile(jarInput.getFile(), dest)
            }
        }
    }

    /**
     * 处理当前class文件
     */
    static void handleCurrentClassFile(File file) {
        ClassReader classReader = new ClassReader(file.bytes)
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        TestClassVisitor classVisitor = new TestClassVisitor(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        byte[] bytes = classWriter.toByteArray()
        File destFile = new File(file.parentFile.absoluteFile, file.name)
        FileOutputStream fileOutputStream = new FileOutputStream(destFile)
        fileOutputStream.write(bytes)
        fileOutputStream.close()
    }

}