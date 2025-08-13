---
视频地址: https://www.bilibili.com/video/BV1UN411x7xe?spm_id_from=333.788.videopod.episodes&vd_source=a503248b608b8da9614b6dd7eb24901d&p=53
---
# 1 什么是XML

- 可扩展标记语言
- 和html都是标记语言，基本语法都是标签，但是不能随便写
- 多数作为配置文件，不只是java，各个文件都可以用

之前用properties（JDBC中做数据库连接信息），也可以作为配置文件，但是无法表示复杂的配置
用的是key=value
如果是可能有多个环境（测试环境和生产环境），就需要重复多个，比较混乱，容易错，格式也有要求
xml就有优势，层次结构就会比较清晰

要求：
- 根标签只能有一个
- 第一行一定是这个，不能有空格等
```xml
<?xml version="1.0" encoding="UTF-8"?>
```
- 会有约束，不能随意写标签
- 约束两种，无需深入了解，idea可以自己生成，实际就只有写具体内容，约束不用自己写。有约束后写的时候idea会有提示
- ![[Pasted image 20250813175925.png]]
比如这里：`"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"`就是约束，后缀标识约束类型。
# 2 XML解析（DOM4J）

> 大致了解即可，后续开发很少会用到自己解析xml，多数是框架已经集成好的
## 2.1 添加依赖

如果你用 **Maven**：

```xml
<dependency>
    <groupId>org.dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>2.1.4</version>
</dependency>
```

如果是普通 Java 项目，就下载 dom4j 的 jar 包，手动加到 classpath。

---
## 2.2 示例 XML 文件

假设你有一个 `books.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<books>
    <book id="1">
        <title>Java 核心技术</title>
        <author>Cay S. Horstmann</author>
    </book>
    <book id="2">
        <title>Effective Java</title>
        <author>Joshua Bloch</author>
    </book>
</books>
```

---

## 2.3 读取 XML 文件

```java
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class Dom4jExample {
    public static void main(String[] args) throws Exception {
        // 1. 创建 SAXReader 对象
        SAXReader reader = new SAXReader();

        // 2. 读取 XML 文件，返回 Document 对象
        Document document = reader.read(new File("books.xml"));

        // 3. 获取根节点
        Element root = document.getRootElement();

        // 4. 遍历子节点
        List<Element> books = root.elements("book");
        for (Element book : books) {
            String id = book.attributeValue("id"); // 获取属性
            String title = book.elementText("title"); // 获取子元素文本
            String author = book.elementText("author");

            System.out.println("ID: " + id);
            System.out.println("书名: " + title);
            System.out.println("作者: " + author);
            System.out.println("------");
        }
    }
}
```

---

## 2.4 常用 API 速查表

|方法|功能|
|---|---|
|`Document.getRootElement()`|获取根元素|
|`Element.element(String name)`|获取**第一个**指定名称的子元素|
|`Element.elements()`|获取**所有子元素**|
|`Element.elements(String name)`|获取指定名称的所有子元素|
|`Element.attributeValue(String name)`|获取属性值|
|`Element.elementText(String name)`|获取子元素的文本值|
|`Element.getText()`|获取当前元素的全部文本|

---

## 2.5 注意事项

1. **文件编码**要和 XML 声明一致（`<?xml version="1.0" encoding="UTF-8"?>`），否则会有乱码。
    
2. `SAXReader` 默认是基于 SAX 解析的，性能不错，但如果文件很大（几十 MB 以上），考虑流式解析（StAX 或 SAX）。
    
3. dom4j 是 **DOM 风格** API，可以直接在内存中修改节点，然后用 `XMLWriter` 保存回去。
    

---

## 2.6 写回 XML

dom4j 本质上是 **把 XML 文件加载到内存中形成一棵树状结构（Document/Element）**，你在运行时可以直接修改节点、属性、文本，再写回文件，因此是可以 **运行时动态修改** 的。

假设你要修改 `books.xml` 中 id=2 的书名：

```java
// 1. 读取
SAXReader reader = new SAXReader();
Document document = reader.read(new File("books.xml"));

// 2. 找到要修改的节点
Element root = document.getRootElement();
for (Element book : root.elements("book")) {
    if ("2".equals(book.attributeValue("id"))) {
        book.element("title").setText("Effective Java (第三版)");
    }
}

// 3. 写回文件
OutputFormat format = OutputFormat.createPrettyPrint();
format.setEncoding("UTF-8");
XMLWriter writer = new XMLWriter(new FileWriter("books.xml"), format);
writer.write(document);
writer.close();
```

这样你可以在程序运行时修改 XML 内容，并立刻写回磁盘。  
如果你不写回文件，那么修改只存在于内存中的 `Document` 对象里。

---

## 2.7 和 Spring Boot `application.yml` 的区别

| 对比点        | dom4j XML 文件                    | Spring Boot YML 配置                                                  |
| ---------- | ------------------------------- | ------------------------------------------------------------------- |
| **格式**     | XML（标签 + 属性）                    | YAML（缩进 + 键值对）                                                      |
| **读取方式**   | 通过 dom4j/SAX/JAXP 等 XML 解析库     | 通过 Spring Boot 自带的 `YamlPropertySourceLoader` 解析                    |
| **动态修改**   | 可以直接解析成 `Document`，运行时修改节点，写回文件 | Spring Boot **启动时** 加载到 `Environment`，默认运行期间不会监控文件变化（除非自己加监听/热刷新逻辑） |
| **热刷新支持**  | 需要自己实现文件监听                      | 可配合 Spring Cloud Config、Spring Boot Actuator `/refresh` 实现热刷新       |
| **应用场景**   | 通用配置、结构化数据存储、数据交换               | Spring Boot 配置文件、服务配置管理                                             |
| **修改生效时机** | 取决于你什么时候重新读取或更新配置               | 一般要重启应用，或用特定机制刷新 `Environment`                                      |

---

## 2.8 核心区别总结

- **Spring Boot 的 YML 配置文件**
    
    - 在应用启动时读取到 `Environment`
    - 修改文件本身**不会立即影响运行中的程序**（除非配合 Spring Cloud Config/Actuator 做动态刷新）
    - 主要是 **启动参数**、Bean 配置等用途
        
- **dom4j 操作 XML**
    
    - 程序自己决定何时读取和写回
    - 修改可以即时生效（如果你的业务逻辑后续直接用内存中的新值）
    - 主要用于业务数据、动态配置等
        
