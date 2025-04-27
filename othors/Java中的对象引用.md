# 什么是对象引用

有点类似于cpp中的指针

- **定义**：对象引用是指向对象的一个引用，它并不直接存储对象的数据，而是存储对象在内存中的地址。引用类型的变量实际上存储的是**指向对象的内存地址**。
    
- **行为**：在 Java 中，对象是通过引用传递的。也就是说，变量存储的是对象在内存中的位置，多个变量可以引用同一个对象。这意味着对一个对象的修改会影响所有引用该对象的变量。
    
- **内存分配**：对象引用本身是一个变量，它存储的是堆内存中的对象地址（对象的数据存储在堆内存中）。当你通过引用变量访问对象时，JVM 会找到该对象并执行相应的操作。
    

示例：对象引用

```java

class Person {     
	String name;     
	Person(String name) { 
		this.name = name; 
	} 
}  
public class Test {     
	public static void main(String[] args) {         
		Person p1 = new Person("Alice"); // p1 是一个引用，指向堆中的 Person 对象
		Person p2 = p1;  // p2 和 p1 都指向同一个 Person 对象  
		p2.name = "Bob";  // 修改 p2 的 name，p1.name 也会变，因为它们指向同一个对象        
		System.out.println(p1.name);  // 输出 "Bob"     
	} 
}
```


在这个例子中，`p1` 和 `p2` 都指向同一个 `Person` 对象。当你通过 `p2` 修改 `name` 属性时，`p1` 看到的 `name` 也被改变，因为它们指向的是同一个对象

和基本数据类型的区别：

- **定义**：基本数据类型（例如 `int`、`float`、`boolean` 等）直接存储值，而不是对象的地址。当你声明一个 `int` 类型的变量时，变量中存储的就是这个整数的实际值。
    
- **行为**：当你将一个基本数据类型的变量传递给方法时，传递的是该值的副本，也就是说方法内部的修改不会影响方法外部的变量。这种传递方式叫做“值传递”。
    
- **内存分配**：基本数据类型的变量存储在栈内存中，直接存储其值。
    

#### 示例：基本数据类型

```java
public class Test {
    public static void main(String[] args) {
        int a = 1;  // a 是基本数据类型，存储值 1
        int b = a;  // b 是 a 的副本，存储的也是值 1

        b = 2;  // 修改 b 的值，a 不受影响
        System.out.println(a);  // 输出 1
    }
}
```

# 一个案例
### 代码解读：

```java
public static Connection getConnection(){
    Connection connection = null;  // 局部变量
    try {
        connection = dataSource.getConnection();  // 获取数据库连接
    } catch (Exception e) {
        throw new RuntimeException(e);  // 异常处理
    }
    return connection;  // 返回连接
}
```

### 1. **`connection` 是局部变量**

`connection` 是 `getConnection()` 方法的局部变量。局部变量的作用范围仅限于该方法内部，通常它会在方法执行结束后被销毁。

### 2. **`connection` 被赋值为数据库连接**

在 `try` 块中，`connection` 被赋值为 `dataSource.getConnection()` 的返回值。这里 `dataSource.getConnection()` 返回的是一个数据库连接对象（`Connection`）。如果数据库连接失败，会抛出异常并被 `catch` 块捕获。

### 3. **`connection` 被返回**

尽管 `connection` 是局部变量，它是方法 `getConnection()` 的返回值。方法内部创建的局部变量可以通过 `return` 返回给调用者。在这种情况下，`connection` 作为局部变量被返回到方法的调用者，而返回的是该对象的引用。

#### 关键点：

- `connection` 在方法内部的作用域是局部的，但它所引用的对象（数据库连接）并不是局部的。也就是说，方法返回的是这个对象的引用，调用方法的代码可以继续使用这个连接对象。
    
- 局部变量 `connection` 在方法调用结束时会被销毁，但所引用的 `Connection` 对象（即数据库连接）会在方法外部继续有效，直到它被显式关闭或垃圾回收。
    

### 4. **为什么局部变量可以返回？**

局部变量虽然在方法内定义，但方法可以返回该局部变量的值（在这里是 `Connection` 类型的引用）。返回的是 **对象的引用** 的复制（也就是复制了一份connect返回），而不是对象本身。这是 Java 中的正常行为。

例如，当你返回一个对象的引用时，方法外部的代码可以继续使用该对象（直到它被显式关闭、垃圾回收或引用变为 `null`）。

### 5. **总结：**

- `connection` 是 `getConnection()` 方法的局部变量。
    
- 它被赋值为从 `dataSource.getConnection()` 获取的数据库连接对象的引用。
    
- 方法返回的是 `connection` 的引用（即数据库连接的引用），外部代码可以继续使用这个连接，直到它被显式关闭。
    
- 方法返回后，`connection` 局部变量会被销毁，但数据库连接对象本身仍然有效，直到你调用 `connection.close()` 来释放连接。

## 如果函数返回一个基本类型呢

在 Java 中，如果你在一个函数中返回一个 `int` 类型的变量，它会遵循“值传递”的机制。也就是说，函数将返回该 `int` 变量的**值**，而不是它的引用。

### 1. **基本类型（如 `int`）的返回：**

```java
public class Test {
    public static void main(String[] args) {
        int result = getNumber();  // 调用 getNumber() 方法，并将返回值存入 result
        System.out.println(result);  // 输出返回的 int 值
    }

    public static int getNumber() {
        int number = 10;  // 定义一个基本类型变量
        return number;  // 返回这个 int 值
    }
}

```
#### **行为分析：**

- `getNumber()` 方法返回的是一个 `int` 类型的值，这个值是 **10**。
    
- 在返回过程中，`getNumber()` 会把 **10** 传递给 `main()` 方法中调用的地方，`result` 将获得该值。
    
- **`int` 类型是值传递**，所以你得到的 `10` 并不是 `getNumber()` 内部的 `number` 变量的引用。即使你在 `main()` 方法中修改 `result`，也不会影响 `getNumber()` 中的 `number`。

## 区别对比

- **对象引用** 是一种通过地址操作对象的方式，多个引用可以指向同一个对象。
    
- **基本数据类型** 是直接存储值的，不会产生引用的副作用。