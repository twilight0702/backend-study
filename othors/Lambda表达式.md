## 🔍 什么是 Lambda 表达式？

Lambda 是 Java 8 引入的一种 **函数式编程**方式，用来简化 **只有一个抽象方法的接口（函数式接口）** 的使用。

一句话总结：**Lambda 是匿名方法的简写，更优雅地传递行为（函数）！**

---

## ✨ Lambda 的基本语法：

```java
(参数列表) -> {方法体}
```

### 🔸 示例 1：最基础的 Lambda 表达式

```java
Runnable r = () -> {
    System.out.println("Hello Lambda!");
};
new Thread(r).start();
```

### 🔸 示例 2：带参数、带返回值

```java
Comparator<Integer> comp = (a, b) -> a - b;
System.out.println(comp.compare(10, 5)); // 输出：5
```

---

## ✅ Lambda 的简化规则：

```java
(参数) -> 单行语句
```

- 参数类型可以省略（编译器自动推断）
    
- 参数只有一个时，`()` 可省略
    
- 方法体只有一句时，`{}` 和 `return` 可省略
    

```java
list.forEach(s -> System.out.println(s)); // 简洁明了
```

---

## 🎯 Lambda 一般用于哪里？

### 1. **代替匿名内部类**

```java
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked!");
    }
});
```

使用 Lambda：

```java
button.addActionListener(e -> System.out.println("Clicked!"));
```

---

### 2. **与集合框架搭配使用**

```java
List<String> list = Arrays.asList("apple", "banana", "cherry");
list.forEach(s -> System.out.println(s));
```

```java
list.sort((s1, s2) -> s1.length() - s2.length());
```

---

### 3. **与 Stream API 搭配（非常常见）**

```java
List<String> filtered = list.stream()
                            .filter(s -> s.startsWith("a"))
                            .collect(Collectors.toList());
```

---

## 🧠 函数式接口简介

Lambda 只能用于“函数式接口”，即只有一个抽象方法的接口，比如：

```java
@FunctionalInterface
interface MyFunc {
    int add(int a, int b);
}
```

Lambda 使用：

```java
MyFunc f = (a, b) -> a + b;
System.out.println(f.add(3, 4)); // 输出：7
```

Java 中的常见函数式接口：

- `Runnable`、`Callable`
    
- `Comparator<T>`
    
- Java 8 内置的函数式接口（`java.util.function.*`）如：
    
    - `Consumer<T>`、`Supplier<T>`、`Function<T, R>`、`Predicate<T>`