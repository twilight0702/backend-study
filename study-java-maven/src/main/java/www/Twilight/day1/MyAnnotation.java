package www.Twilight.day1;

import java.lang.annotation.*;

/**
 * 2025.5.13
 * 学习自定义注解
 * target标识作用范围（类，方法）
 * retention标识生命周期（运行时）
 * Document标识是否生成文档
 * Inherited标识是否允许继承
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAnnotation {
    String value();
}
