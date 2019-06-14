package com.demo.utils;

import com.baselibrary.utils.LogUtil;
import com.demo.beans.reflection.ReflectionBean;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Description 反射示例工具类，几点注意事项：
 * <br>1.在java的反射机制中，getDeclaredMethod得到的是全部方法，getMethod得到的是公有方法</br>
 * <br>2.反射机制的setAccessible可能会破坏封装性，可以任意访问私有方法和私有变量</br>
 * <br>3.setAccessible并不是将private改为public，事实上，public方法的accessible属性也是false的，setAccessible只是取消了安全访问控制检查，所以通过设置setAccessible，可以跳过访问控制检查，执行的效率也比较高</br>
 *
 * <br>Author wanghengwei</br>
 * <br>Date 2019/6/13 17:28</br>
 */
public class ReflectionUtil {

    /**
     * 打印{@link ReflectionBean}反射信息
     */
    public static void printReflectionDemoInfo() {
        try {
            Class<?> c = Class.forName("com.demo.beans.reflection.ReflectionBean");
            printReflectionInfo("获得类对象", c);

            Class<?> superclass = c.getSuperclass();
            printReflectionInfo("获得超类", superclass);

            Class<?>[] interfaces = c.getInterfaces();
            printReflectionInfo("获得所有父接口", interfaces);

            ReflectionBean bean = (ReflectionBean) c.newInstance();
            printReflectionInfo("实例化", bean);

            Constructor<?>[] constructors = c.getConstructors(); //获得访问属性为public的构造方法//获得指定参数的构造方法
            printReflectionInfo("获得构造方法", constructors);

            Constructor<?> constructor = c.getDeclaredConstructor(int.class); // 获得指定参数的构造方法
            printReflectionInfo("获得指定构造方法", constructor);

            Method method = c.getMethod("oneParamMethod2", String.class); // 获得方法，getMethod只能获得public方法，包括父类和接口继承的方法
            printReflectionInfo("获得公有方法", method);

            method.invoke(bean, "HoHoHo"); //调用方法

            String modifier = Modifier.toString(method.getModifiers()); //获得修饰符,包括private/public/protect/static
            printReflectionInfo("获得修饰符", modifier);

            Class<?>[] parameterTypes = method.getParameterTypes(); //获得参数类型
            printReflectionInfo("获得参数类型", parameterTypes);

            Class<?> returnType = method.getReturnType(); //获得返回值类型
            printReflectionInfo("获得返回值类型", returnType);

            Class<?>[] exceptionTypes = method.getExceptionTypes(); //获得异常类型
            printReflectionInfo("获得异常类型", exceptionTypes);

            Method method2 = c.getDeclaredMethod("oneParamMethod1", String.class); //调用私有方法，getDeclaredMethod获得类自身的方法，包括private/public/protect方法
            method2.setAccessible(true);
            String result = (String) method2.invoke(bean, "LoL");
            printReflectionInfo("获得私有方法", result);

            Field[] fields = c.getFields(); //获得全部属性
            printReflectionInfo("获得全部属性", fields);

            Field field = c.getDeclaredField("num"); //获得类自身定义的指定属性
            printReflectionInfo("获得自身属性", field);

            Field field2 = c.getField("comment"); //获得类及其父类、父接口定义的public属性
            printReflectionInfo("获得公有属性", field2);

            String fieldModifier = Modifier.toString(field.getModifiers()); //获得权限修饰符，包括private/public/protect/static/final
            printReflectionInfo("获得权限修饰符", fieldModifier);

            int[] exampleArray = {1, 2, 3, 4, 5}; //操作数组

            Class<?> componentType = exampleArray.getClass().getComponentType();//获得数组类型
            printReflectionInfo("数组类型", componentType.getName());

            printReflectionInfo("数组长度", Array.getLength(exampleArray)); //获得数组长度

            printReflectionInfo("获得数组元素", Array.get(exampleArray, 2)); //获得指定元素

            Array.set(exampleArray, 2, 6); //修改指定元素
            printReflectionInfo("修改数组元素", exampleArray);

            printReflectionInfo("获得当前类加载器", bean.getClass().getClassLoader().getClass().getName()); //获得当前类加载器

        } catch (ClassNotFoundException e) {
            LogUtil.e(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            LogUtil.e(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            LogUtil.e(e.getLocalizedMessage());
        } catch (NoSuchMethodException e) {
            LogUtil.e(e.getLocalizedMessage());
        } catch (InvocationTargetException e) {
            LogUtil.e(e.getLocalizedMessage());
        } catch (NoSuchFieldException e) {
            LogUtil.e(e.getLocalizedMessage());
        }
    }

    /**
     * 打印反射信息
     *
     * @param info 标题
     * @param obj  打印对象
     */
    private static void printReflectionInfo(String info, Object obj) {
        if (obj.getClass().isArray()) {
            LogUtil.e(info + ": ");
            int length = Array.getLength(obj);
            LogUtil.e("Array Size: " + length);
            for (int i = 0; i < length; i++) {
                LogUtil.e("Array[" + i + "]:" + Array.get(obj, i) + ",");
            }
        }
        LogUtil.e(info + ": " + obj.toString());
        LogUtil.e("=====================================");
    }

}
