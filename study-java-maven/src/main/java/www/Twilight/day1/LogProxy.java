package www.Twilight.day1;

import java.lang.reflect.*;

public class LogProxy {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Method realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

                        if (realMethod.isAnnotationPresent(LogExecutionTime.class)) {
                            long start = System.currentTimeMillis();
                            System.out.println("开始执行方法: " + method.getName());
                            Object result = method.invoke(target, args);
                            long end = System.currentTimeMillis();
                            System.out.println("方法执行结果: " + result);
                            System.out.println("方法执行耗时: " + (end - start) + " ms");
                            return result;
                        } else {
                            return method.invoke(target, args);
                        }
                    }
                }
        );
    }
}
