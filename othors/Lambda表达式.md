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

Lambda 表达式作为 Java 8 引入的重要特性，广泛应用于函数式编程。以下是一些常见的 Lambda 表达式用法，它们展示了如何利用 Lambda 简化代码，尤其在集合操作、事件处理等方面的应用。

### 1. **简化匿名内部类**

Lambda 表达式最常见的用法就是替代匿名内部类，使代码更加简洁。特别是在使用单方法接口（如 `Runnable`、`Comparator`）时，Lambda 表达式能够大幅度减少代码冗余。

#### 示例 1：`Runnable` 接口

**传统方式：**

```java
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Running in a thread");
    }
});
```

**使用 Lambda：**

```java
Thread thread = new Thread(() -> System.out.println("Running in a thread"));
```

### 2. **函数式接口（Functional Interface）**

函数式接口是仅包含一个抽象方法的接口，可以用 Lambda 表达式来实例化这些接口。常见的函数式接口包括 `Runnable`、`Comparator`、`Consumer`、`Function` 等。

#### 示例 2：`Consumer` 接口

`Consumer` 接口用于消费数据，它的 `accept` 方法接受一个参数。

```java
import java.util.function.Consumer;

public class LambdaConsumerExample {
    public static void main(String[] args) {
        // Lambda 表达式实现 Consumer 接口
        Consumer<String> printMessage = (message) -> System.out.println(message);
        printMessage.accept("Hello, Lambda!");
    }
}
```

### 3. **Stream API 中的 Lambda 表达式**

Java 8 引入了 Stream API，用于简化集合处理、提高代码的可读性和可维护性。Lambda 表达式在 Stream 操作中使用得非常广泛，尤其是在链式操作中。

#### 示例 3：Stream 流式处理

```java
import java.util.List;
import java.util.Arrays;

public class LambdaStreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // 使用 Lambda 表达式进行过滤并打印
        numbers.stream()
               .filter(n -> n % 2 == 0)  // 过滤偶数
               .forEach(n -> System.out.println(n));  // 输出结果
    }
}
```

### 4. **`Comparator` 接口排序**

`Comparator` 接口用于自定义排序逻辑，Lambda 表达式在排序时非常简洁。

#### 示例 4：自定义排序

```java
import java.util.*;

public class LambdaComparatorExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Alice", "Bob");

        // 使用 Lambda 表达式按字母顺序排序
        Collections.sort(names, (a, b) -> a.compareTo(b));

        names.forEach(name -> System.out.println(name));
    }
}
```

### 5. **`Function` 接口：单一输入，单一输出**

`Function` 接口接受一个输入并返回一个结果，Lambda 表达式可以方便地实现这种逻辑。

#### 示例 5：使用 `Function` 接口

```java
import java.util.function.Function;

public class LambdaFunctionExample {
    public static void main(String[] args) {
        // Lambda 表达式将输入的字符串转换为大写
        Function<String, String> toUpperCase = (str) -> str.toUpperCase();
        
        String result = toUpperCase.apply("hello");
        System.out.println(result);  // 输出 "HELLO"
    }
}
```

### 6. **`Predicate` 接口：用于条件判断**

`Predicate` 接口用于做条件判断，它接收一个输入并返回一个布尔值。常用于过滤或匹配。

#### 示例 6：使用 `Predicate` 进行过滤

```java
import java.util.function.Predicate;

public class LambdaPredicateExample {
    public static void main(String[] args) {
        Predicate<Integer> isEven = (n) -> n % 2 == 0;

        System.out.println(isEven.test(4));  // 输出 true
        System.out.println(isEven.test(5));  // 输出 false
    }
}
```

### 7. **`BiConsumer` 和 `BiFunction` 接口**

`BiConsumer` 接口接受两个输入参数，执行操作而不返回结果；`BiFunction` 接口则接受两个输入并返回一个结果。

#### 示例 7：使用 `BiConsumer`

```java
import java.util.function.BiConsumer;

public class LambdaBiConsumerExample {
    public static void main(String[] args) {
        // 使用 BiConsumer 接口接受两个参数
        BiConsumer<String, Integer> printDetails = (name, age) -> 
            System.out.println(name + " is " + age + " years old.");
        
        printDetails.accept("Alice", 25);  // 输出 Alice is 25 years old.
    }
}
```

#### 示例 8：使用 `BiFunction`

```java
import java.util.function.BiFunction;

public class LambdaBiFunctionExample {
    public static void main(String[] args) {
        // 使用 BiFunction 接口，接受两个参数并返回一个结果
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        
        System.out.println(add.apply(5, 3));  // 输出 8
    }
}
```

### 8. **`Optional` 类和 Lambda 表达式结合使用**

`Optional` 类用于避免空指针异常（`NullPointerException`），可以结合 Lambda 表达式处理缺失值。

#### 示例 9：使用 `Optional` 和 Lambda

```java
import java.util.Optional;

public class LambdaOptionalExample {
    public static void main(String[] args) {
        Optional<String> name = Optional.of("John");
        
        // 如果值存在，则执行 Lambda 表达式
        name.ifPresent(n -> System.out.println("Hello, " + n));  // 输出 Hello, John
    }
}
```

### 9. **`forEach` 和 Lambda**

`forEach` 是 `Iterable` 接口中的默认方法，结合 Lambda 表达式用于遍历集合元素。

#### 示例 10：使用 `forEach` 和 Lambda

```java
import java.util.List;
import java.util.Arrays;

public class LambdaForEachExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // 使用 Lambda 表达式遍历集合
        names.forEach(name -> System.out.println(name));
    }
}
```

### 总结

Lambda 表达式是 Java 8 中的一个重要特性，它可以简化代码，提高可读性。常见的用法包括：

1. **简化匿名内部类**：如 `Runnable`、`Comparator` 等。
    
2. **Stream API**：用于集合处理，支持链式调用。
    
3. **函数式接口**：如 `Consumer`、`Function`、`Predicate` 等。
    
4. **简化集合操作**：如 `forEach`、`map`、`filter` 等。
    
5. **Optional**：用于避免空指针异常，结合 Lambda 表达式处理可选值。
    

这些用法使得 Java 代码更加简洁且易于理解，并且支持函数式编程风格。