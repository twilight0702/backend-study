# 变量类型

在 Java 中，变量的种类可以从多个角度划分，比如从**数据类型**来看、从**声明位置/生命周期**来看等。下面我会从这两个常用角度来给你全面总结一下 👇

---

## ✅ 一、从 **数据类型** 来看变量（Java 一切都是类型）

Java 中的变量分为两大类：

### 1️⃣ 基本数据类型（primitive types）— 共 8 种：

| 类型        | 大小   | 默认值      | 示例值            | 用途示例        |
| --------- | ---- | -------- | -------------- | ----------- |
| `byte`    | 1 字节 | 0        | `127`          | 节省空间，范围小的数字 |
| `short`   | 2 字节 | 0        | `32000`        | 小范围整数       |
| `int`     | 4 字节 | 0        | `123456`       | 一般整数运算      |
| `long`    | 8 字节 | 0L       | `10000000000L` | 大整数         |
| `float`   | 4 字节 | 0.0f     | `3.14f`        | 单精度浮点       |
| `double`  | 8 字节 | 0.0      | `3.1415926`    | 双精度浮点（更常用）  |
| `char`    | 2 字节 | `\u0000` | `'A'`          | 字符          |
| `boolean` | 1 字节 | false    | `true`/`false` | 逻辑判断        |

### 2️⃣ 引用数据类型（reference types）：

- **类类型**（如 `String`, `Scanner`, `ArrayList`）
    
- **数组类型**（如 `int[]`, `String[]`）
    
- **接口类型**（如 `Runnable`, `Comparable`）
    
- **枚举类型**（如 `enum Color { RED, GREEN }`）
    
- **记录类型（record）**（Java 14+，轻量类）
    

---

## ✅ 二、从 **声明位置 / 生命周期** 来看变量

|类型|声明位置|生命周期|特点和示例|
|---|---|---|---|
|**局部变量**|方法内、代码块中|方法调用时创建，调用结束就销毁|必须先初始化再使用，存在于栈中|
|**成员变量（字段）**|类中、方法外|随对象一起创建/销毁|有默认值，可以不初始化|
|**静态变量**|类中，加 `static` 修饰|类加载时创建，直到类卸载|属于类，不属于对象，适合共享数据|
|**参数变量**|方法参数中|方法调用期间有效|实际参数值的副本|

📌 举例说明：

```java
public class Example {
    static int staticVar = 100;  // 静态变量
    int memberVar = 200;         // 成员变量（实例变量）

    public void method(int paramVar) { // paramVar 是参数变量
        int localVar = 300;            // 局部变量
    }
}
```

---

## ✅ 三、你也可以从 “变量作用域” 来划分：

|作用域|变量示例|
|---|---|
|类作用域|静态变量、成员变量|
|方法作用域|参数变量、局部变量|
|代码块作用域|`for`, `if`, `{}` 内声明的变量|

---

## 🧠 小贴士（常见误区）：

- Java 中没有 `global`（全局变量）关键字，但 `public static` 变量类似于“全局变量”
    
- 所有非 `primitive` 类型都是引用类型（包括数组）
    
- `String` 虽然是引用类型，但行为像值类型（因为它是不可变的）

# 变量初始化

在 Java 中，一个类中的变量**是否自动初始化、默认值是多少、何时初始化**，取决于它的类型（基本类型 vs 引用类型）和声明位置（成员变量 vs 局部变量）。下面我来给你详细讲清楚 👇

---

## ✅ 一、类中变量的初始化分类

### 💡 Java 中的变量主要分两类：

|类型|说明|
|---|---|
|**成员变量（字段）**|在类中，方法外声明的变量|
|**局部变量**|在方法、构造器、代码块中声明的变量|

---

## ✅ 二、成员变量（字段）自动初始化（有默认值）

无论是 **实例变量** 还是 **静态变量**，**Java 都会自动为它们赋初值**：

|类型|默认值|
|---|---|
|`int`|`0`|
|`double`|`0.0`|
|`boolean`|`false`|
|`char`|`\u0000`（即 '\0'）|
|引用类型|`null`|

### ✅ 示例：

```java
public class Demo {
    static int staticVar;        // 静态变量（自动为 0）
    int instanceVar;             // 实例变量（自动为 0）

    public void print() {
        System.out.println(staticVar);   // 0
        System.out.println(instanceVar); // 0
    }
}
```

---

## ❌ 三、局部变量必须显式初始化

局部变量（方法、构造器、代码块里声明的）**不会自动赋默认值**，你使用之前**必须先赋值**，否则编译报错！

### ❌ 示例：

```java
public class Test {
    public void run() {
        int a;  // 局部变量
        // System.out.println(a);  // ❌ 编译错误：变量可能未初始化
    }
}
```

---

## ✅ 四、静态初始化和构造初始化

### 🔹 静态变量的初始化：在类加载时执行一次

```java
static int count = 10;  // 静态初始化
static {
    count += 5;         // 静态代码块也可初始化静态变量
}
```

### 🔹 实例变量的初始化：在创建对象时执行（构造函数执行之前）

```java
int score = 60;  // 实例变量初始化
{
    score += 20;  // 实例代码块
}
public Demo() {
    score += 10;  // 构造器也可以初始化
}
```

---

## 🧠 总结表格

|变量类型|默认值|是否自动初始化|初始化时机|说明|
|---|---|---|---|---|
|静态变量|有|✅|类加载时|属于类，所有对象共享|
|实例变量|有|✅|对象创建时|每个对象一份|
|局部变量|无|❌|方法调用时|使用前必须显式赋值|
|方法参数（形参）|N/A|✅（调用传值）|方法调用时赋值|自动通过实参传入，不用手动初始化|

# 引用类型和值类型

这句话的意思有点“反直觉”，但它说的是 **Java 中的 `String` 虽然是引用类型，但因为它的不可变（immutable）特性，在使用和传参时，**表现得像值类型**（value type）。我们一步步来解释清楚👇：

---

## 🧠 一、引用类型 vs 值类型 的差异

| 区别点       | 引用类型        | 值类型（基本类型）       |
| --------- | ----------- | --------------- |
| 存储的内容     | 对象的地址（引用）   | 变量本身的值          |
| 传参方式      | 传引用的“副本”    | 传值的“副本”         |
| 方法中能否修改原值 | ✅（可通过引用改内容） | ❌（只是拷贝的值，改不了原值） |

---

## ✅ 二、`String` 是引用类型，但“行为像值类型”的原因

### 🔐 关键点：**`String` 是不可变的（Immutable）**

Java 中的 `String` 被设计为**一旦创建就不可修改内容**，比如：

```java
String s = "hello";
s.toUpperCase(); // 并不会修改原来的 s
System.out.println(s); // 输出仍然是 hello
```

`toUpperCase()` 实际上返回了一个新字符串 `"HELLO"`，原始的 `s` 并没有改变。

---

## 🧪 三、例子演示：String传参就像传值类型

```java
public class Test {
    public static void change(String str) {
        str = "world";
    }

    public static void main(String[] args) {
        String s = "hello";
        change(s);
        System.out.println(s); // 👉 仍然是 hello
    }
}
```

尽管 `str` 是引用类型，**但我们不能通过它修改外部的 `"hello"`，因为 String 是不可变的！**

所以它就“**看起来像值类型**”：你传进去它的副本，怎么改都不会影响原始变量。

---

## 🔁 四、对比：真正的引用类型传参

```java
class Person {
    String name;
}

public class Test {
    public static void change(Person p) {
        p.name = "Tom";
    }

    public static void main(String[] args) {
        Person p = new Person();
        p.name = "Jerry";
        change(p);
        System.out.println(p.name); // 👉 会输出 Tom
    }
}
```

这里 `Person` 是引用类型，且是**可变的**，所以方法中可以影响外部对象的内容。

---

## ✅ 总结

| 类型              | 可变性     | 方法中能否改外部值 | 表现像        |
| --------------- | ------- | --------- | ---------- |
| `int`           | ✅ 可变（值） | ❌         | 值类型        |
| `String`        | ❌ 不可变   | ❌         | 值类型（尽管是引用） |
| `List`、`Person` | ✅ 可变    | ✅         | 引用类型       |

---

如果你想真正模拟 C++ 的引用传递或指针传递那种“能改外部变量本身”的行为，就不能用 String 这种不可变类，要用像 `StringBuilder` 或其他自定义类才行。

# 关于new的用法

✅ **不是所有非基本类型都必须使用 `new` 来创建。**

不过，大多数情况下你确实需要 `new` 来**手动创建对象实例**，因为非基本类型本质上是**引用类型（Reference Type）**，你得有一个“对象”被引用才行。

| 类型                       | 是否必须 `new`？  | 说明                                                 |
| ------------------------ | ------------ | -------------------------------------------------- |
| `String`                 | ❌ 不一定        | 字符串常量可以直接赋值：`String s = "hello";` 不需要 `new`        |
| `Integer`, `Double` 等包装类 | ❌ 可以自动装箱     | `Integer i = 10;` 编译器会自动转换成 `Integer.valueOf(10)`  |
| `Array`                  | ✅ 必须使用 `new` | 除非用字面量初始化：`int[] arr = {1,2,3};`                   |
| 自定义类（比如 `Person`）        | ✅ 需要使用 `new` | 必须自己创建实例：`Person p = new Person();`                |
| 集合类（如 `ArrayList`）       | ✅ 必须 `new`   | 因为接口不能实例化：`List<String> list = new ArrayList<>();` |

✅ 示例对比

```java
// 不需要 new
String s = "hello";  // 字符串常量池中的引用
Integer i = 10;      // 自动装箱，等价于 Integer.valueOf(10)

// 必须 new
Person p = new Person("张三");
List<String> list = new ArrayList<>();
```

❗为什么 `String` 不需要 `new` 也能用？

Java 中对 `String` 做了特殊优化 —— 字符串常量池。

```java
String s1 = "hello";
String s2 = "hello";
System.out.println(s1 == s2); // true，指向同一个常量池对象
```

而如果你显式 `new`，它会创建一个新的对象：

```java
String s3 = new String("hello");
System.out.println(s1 == s3); // false
```

