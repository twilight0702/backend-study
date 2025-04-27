# 流的种类

Java 中的输入输出流（I/O 流）是处理数据读写的核心机制，它统一抽象为**以“流”的形式来处理数据的输入和输出**。掌握它就能轻松处理：文件读写、网络通信、内存操作、键盘输入、控制台输出等。

---

## 📦 一、Java IO 的分类结构图

Java 中的 I/O 流主要分为两大类：

```
          java.io
          ┌─────────────┐
          │     抽象类      │
          └─────────────┘
                ↓
      ┌─────────────┐        ┌─────────────┐
      │ InputStream │        │ Reader      │（字符输入流）
      └─────────────┘        └─────────────┘
      │ （字节输入流）         │
      ↓                      ↓
  FileInputStream       FileReader 等
  BufferedInputStream   BufferedReader 等
  ObjectInputStream     InputStreamReader 等
      ↑                      ↑
    Input                  字符

      ┌─────────────┐        ┌─────────────┐
      │ OutputStream│        │ Writer      │（字符输出流）
      └─────────────┘        └─────────────┘
      │ （字节输出流）         │
      ↓                      ↓
  FileOutputStream      FileWriter 等
  BufferedOutputStream  BufferedWriter 等
  ObjectOutputStream    OutputStreamWriter 等
```

---

## ✳️ 二、字节流 VS 字符流

|区别|字节流|字符流|
|---|---|---|
|父类|`InputStream` / `OutputStream`|`Reader` / `Writer`|
|处理单位|1 字节（8 bit）|1 字符（支持多字节）|
|常用于|二进制数据（图片、音频、视频）|文本文件（中文、英文）|
|编码问题|不管编码（自己处理）|会自动处理编码（如 UTF-8）|

---

## ✍️ 三、常用流的使用示例

### 🔹 1. 读取文本文件（字符流）

```java
FileReader reader = new FileReader("test.txt");
int ch;
while ((ch = reader.read()) != -1) {
    System.out.print((char) ch);
}
reader.close();
```

### 🔹 2. 写文本文件（字符流）

```java
FileWriter writer = new FileWriter("output.txt");
writer.write("你好，Java IO！");
writer.close();
```

---

### 🔹 3. 复制二进制文件（字节流）

```java
FileInputStream in = new FileInputStream("a.jpg");
FileOutputStream out = new FileOutputStream("copy.jpg");

byte[] buffer = new byte[1024];
int len;
while ((len = in.read(buffer)) != -1) {
    out.write(buffer, 0, len);
}

in.close();
out.close();
```

---

### 🔹 4. 使用缓冲流（提高性能）

```java
BufferedReader br = new BufferedReader(new FileReader("test.txt"));
String line;
while ((line = br.readLine()) != null) {
    System.out.println(line);
}
br.close();
```

---

### 🔹 5. 控制台输入输出（System.in/out）

```java
BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
System.out.print("请输入：");
String input = reader.readLine();
System.out.println("你输入的是：" + input);
```

---

## 🔁 四、InputStream 与 Reader 的转换

当你从网络或二进制中读入后要转字符用这个：

```java
InputStream is = new FileInputStream("data.txt");
Reader reader = new InputStreamReader(is, "UTF-8"); // 加字符编码
```

---

## ✅ 总结：什么时候用哪种流？

|场景|建议用的流|
|---|---|
|处理二进制文件（图片、视频）|字节流（InputStream/OutputStream）|
|处理文本文件（.txt, .java）|字符流（Reader/Writer）|
|大文件读写、性能优化|缓冲流（BufferedXxx）|
|控制台交互输入输出|`System.in` + `BufferedReader` / `Scanner`|

# 释放流

在 Java 中使用流时，**流必须在操作完成后及时释放**，否则可能会导致资源泄漏，影响程序的性能和稳定性。释放流主要是为了关闭底层资源，比如文件、网络连接等，确保它们能够被其他程序使用。

---

## ✅ 一、为什么需要释放流？

#### 1. **文件句柄、数据库连接、网络连接等被占用**

- **文件流**：如果不关闭文件流，操作系统可能无法释放文件句柄，导致其他程序无法访问或修改该文件，甚至会导致文件锁定。操作系统在一段时间后可能会限制可用文件句柄的数量，从而影响程序的运行。
    
- **数据库连接流**：如果数据库连接不关闭，数据库连接池中的连接数可能会用完，导致无法再获取新的数据库连接。此时，程序会出现无法访问数据库的问题。
    
- **网络连接流**：如果网络连接流没有关闭，服务器和客户端的连接可能无法释放，导致连接数达到上限，影响性能，甚至导致程序崩溃。
    

#### 2. **内存泄漏的间接影响**

虽然**未关闭流不会直接导致内存泄漏**，但它可能会间接影响内存使用。例如，如果不关闭数据库连接或文件流，程序可能无法释放这些资源，导致底层的内存管理不当。长期下来，虽然 [[JVM 的垃圾回收器（GC）|JVM的垃圾回收器（GC）]]可能会回收内存，但无法回收底层资源（如打开的文件描述符、数据库连接等），最终可能导致系统运行缓慢或崩溃。

#### 3. **性能下降**

- 长时间占用资源（如文件、数据库连接、网络连接等）会增加资源消耗，可能导致程序响应慢、延迟增大。
    
- 如果资源一直被占用，可能会影响其他系统或用户的操作，导致资源竞争，从而影响整个系统的性能。
    

#### 4. **系统崩溃或异常终止**

如果长时间不释放连接或流，资源消耗将逐步增大，最终可能导致：

- 系统崩溃，或者程序由于资源不足无法继续运行。
    
- 资源耗尽后，可能会导致程序抛出异常（如 `SQLException`、`IOException` 等），因为程序无法再分配新的资源。
    

---

## ✳️ 二、如何释放流？

Java 提供了两种常见的方式来关闭流：

1. **手动关闭流（使用 `close()` 方法）**
    
2. **使用 `try-with-resources` 语句自动关闭流**
    

### 1. 手动关闭流

每当你完成流的操作后，都应该显式地调用流对象的 `close()` 方法来释放资源。

```java
FileInputStream in = null;
FileOutputStream out = null;

try {
    in = new FileInputStream("input.txt");
    out = new FileOutputStream("output.txt");

    int data;
    while ((data = in.read()) != -1) {
        out.write(data);
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        if (in != null) in.close();  // 关闭输入流
        if (out != null) out.close(); // 关闭输出流
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

在 `finally` 块中关闭流，确保在出现异常时流依然能被关闭。

### 2. 使用 `try-with-resources` 自动关闭流（推荐）

Java 7 引入了 **`try-with-resources`** 语法，它能够自动关闭流、释放资源。所有实现了 `AutoCloseable` 接口的类（包括流类）都可以使用这种语法。

```java
try (FileInputStream in = new FileInputStream("input.txt");
     FileOutputStream out = new FileOutputStream("output.txt")) {

    int data;
    while ((data = in.read()) != -1) {
        out.write(data);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

在这个例子中，`in` 和 `out` 都会在 `try` 块执行完毕后自动关闭，确保资源被及时释放，不需要手动调用 `close()` 方法。

---

## 🛠 三、关于 `close()` 方法

- `close()` 方法是 Java 中关闭流时最重要的步骤，它会确保底层的资源（如文件句柄、网络连接）被释放。
    
- **`close()` 可能抛出异常**，因此建议将它放入 `finally` 块中，或者使用 `try-with-resources` 来避免遗漏关闭。

## 内存泄漏和资源泄露

1. **资源泄漏**：指的是在不再使用某个资源（如文件、网络连接、数据库连接等）时，未正确释放它，导致资源无法被其他程序或进程使用，造成系统性能下降，甚至崩溃。

2. **内存泄漏**：指的是程序无法释放不再使用的对象引用，导致垃圾回收器无法回收这部分内存，从而占用越来越多的内存，最终可能导致程序崩溃。


JVM 的垃圾回收器负责自动管理堆内存中的对象，当对象不再被引用时，GC 会尝试回收这些对象并释放内存。**但是**，它不会自动释放非堆内存中的资源，比如文件流、数据库连接等。

- **文件流**：文件流依赖于操作系统管理的文件句柄。JVM 会在程序结束时释放它自己的内存，但文件句柄可能仍然被操作系统持有，直到操作系统关闭所有进程。
    
- **数据库连接**：如果没有显式关闭数据库连接，数据库连接池会在应用程序结束时关闭连接，但这并不意味着连接池会立即释放这些连接，操作系统和数据库服务器可能仍然持有这些连接一段时间。
    
- **网络连接**：网络连接是由操作系统管理的，程序结束时操作系统会关闭连接，但这不是 JVM 的行为，且可能会有延迟。
