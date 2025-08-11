---
简介: 关于Java中String多种创建方式的比较
---
# 1 `String a = "abc";` —— 字符串字面量（常量池）

## 1.1 机制

- `"abc"` 是字符串字面量，**编译时确定**，存储于 `.class` 文件的常量池中。
- 类加载时，JVM 将字面量加载到**运行时字符串常量池**（通常在方法区或 Metaspace 的特殊区域）。
- 如果常量池已有 `"abc"`，则直接返回该引用；否则新建一个。
- 因此该写法复用常量池中的字符串对象。

## 1.2 特点

- **高效、节省内存**，推荐使用。
- 字面量字符串之间可以用 `==` 比较，判断是否为同一对象。
- 适合绝大多数字符串初始化场景。

---

# 2 `String a = new String("abc");` —— 显式创建新对象（堆内存）

## 2.1 机制

- `"abc"` 仍是字面量，存在常量池。
- `new String("abc")` 会**在堆中创建一个新的 String 对象**，拷贝常量池中的 `"abc"` 内容。
- 该对象与常量池的对象引用不同，内容相同。

## 2.2 具体执行等价于

```java
String temp = "abc";       // 引用常量池对象
String a = new String(temp); // 堆中新建 String 对象，内容拷贝自 temp
```
## 2.3 特点

- 每次执行都会新建堆对象，**占用额外内存**。
- `a == "abc"` 返回 `false`（不同引用）。
- 一般不推荐使用，除非确实需要创建新的实例（如防御式编程、序列化场景）。

---

# 3 对比总结表

|特性|`String a = "abc"`|`String a = new String("abc")`|
|---|---|---|
|对象位置|常量池|常量池 + 堆|
|是否复用已有对象|是|否|
|内存开销|小|较大|
|`a == "abc"`|`true`|`false`|
|推荐使用|✅|❌（除特殊需求）|

---

# 4 代码验证示例

```java
public class Test {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = new String("abc");

        System.out.println(s1 == s2);      // false
        System.out.println(s1.equals(s2)); // true
    }
}
```

---

# 5 字符串常量池的加载时机

- 编译阶段：字面量被写入 `.class` 文件的常量池。
- 运行阶段：类加载时，字面量进入 JVM 的运行时字符串常量池（Metaspace 中）。
- `temp` 指向常量池中的对象。

---

## 5.1 `new String(temp)` 的执行细节

- 调用 `String` 构造函数，堆中新建实例。
- 对原字符串底层字符数组（Java 8 之前是 `char[]`，Java 9+ 是优化后的 `byte[]`）进行浅拷贝。
- 因此 `a` 与 `temp` 是两个独立对象，引用不同。

---

# 6 `intern()` 方法示例

如果想让 `new String("abc")` 返回的对象引用指向常量池中已有的字符串，可使用：

```java
String s1 = "abc";
String s2 = new String("abc").intern();
System.out.println(s1 == s2); // true
```

`intern()` 会将字符串加入常量池并返回常量池中的引用。

---

# 7 面试建议

- 理解字面量字符串和堆上新建字符串的区别。
- 牢记 `==` 比较引用，`.equals()` 比较内容。
- 优先使用字面量形式初始化字符串。
- 了解常量池机制和 `intern()` 的用途。