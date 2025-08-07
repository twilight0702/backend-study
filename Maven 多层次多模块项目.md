# 多模块声明

在 Maven 多层次多模块项目（multi-module project）中，模块的管理和增减是一个核心任务，通常通过父项目的 `pom.xml` 实现统一管理和配置。以下是模块管理的基本原则、结构设计和模块增减的具体操作指南：

---

## 一、项目结构（多层次模块示例）

假设项目叫 `my-project`，结构如下：

```
my-project/
├── pom.xml               <- 父项目（聚合 + 管理）
├── common/               <- 公共模块
│   └── pom.xml
├── service/
│   ├── service-a/
│   │   └── pom.xml
│   ├── service-b/
│   │   └── pom.xml
│   └── pom.xml           <- 二级聚合模块（可选）
└── web/
    ├── web-admin/
    │   └── pom.xml
    ├── web-api/
    │   └── pom.xml
    └── pom.xml           <- 二级聚合模块（可选）
```

---

## 二、模块管理原则

### 1. 顶层父模块（root `pom.xml`）

负责聚合（）、统一依赖版本、插件管理、构建配置等：

```xml
<modules>
  <module>common</module>
  <module>service/service-a</module>
  <module>service/service-b</module>
  <module>web/web-admin</module>
  <module>web/web-api</module>
</modules>

<dependencyManagement>
  <dependencies>
    <!-- 所有模块统一使用的依赖版本 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
      <version>3.2.0</version>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### 2. 子模块 `pom.xml`

每个子模块声明：

```xml
<parent>
  <groupId>com.example</groupId>
  <artifactId>my-project</artifactId>
  <version>1.0.0</version>
</parent>
```

如果有中间聚合模块（如 `service/pom.xml`），它本身也可以是 `pom` 类型，继续聚合子模块。

---

## 三、如何新增模块

以添加一个新模块 `service-c` 为例：

### 步骤 1：在对应目录新建模块

```bash
cd service
mvn archetype:generate -DgroupId=com.example.service -DartifactId=service-c -DarchetypeArtifactId=maven-archetype-quickstart
```

### 步骤 2：确保 `service/service-c/pom.xml` 的 `<parent>` 指向顶层或二级父模块：

```xml
<parent>
  <groupId>com.example</groupId>
  <artifactId>my-project</artifactId>  <!-- 或 service -->
  <version>1.0.0</version>
</parent>
```

### 步骤 3：在父模块的 `<modules>` 添加

- 若直接在顶层 `pom.xml` 聚合：
    

```xml
<module>service/service-c</module>
```

- 若通过二级聚合 `service/pom.xml` 管理：
    

```xml
<module>service-c</module>
```

### 步骤 4：构建验证

```bash
mvn clean install
```

---

## 四、如何删除模块

以删除模块 `web-api` 为例：

1. **从 `pom.xml` 中删除对应 `<module>` 声明**。
    
2. **从硬盘中删除该模块目录（如 `web/web-api/`）**。
    
3. **检查是否有其他模块依赖它**，删除相关 `<dependency>`。
    

---

## 五、常见实践建议

- **统一版本控制**：使用 `<dependencyManagement>` 控制版本，避免模块间版本冲突。
    
- **分层结构清晰**：可按领域（如 common、service、web、infra）组织模块。
    
- **父模块禁止打包代码**：设置为 `pom` 类型，并添加 `<packaging>pom</packaging>`。
    
- **二级聚合非必须**：但大型项目推荐使用，便于分组管理。
    

---

如需我帮你分析具体项目结构，或设计模块组织结构，可以贴出你的 `pom.xml` 或项目目录结构。

# 模块聚合和依赖引用

这是一个很关键的问题，涉及到 Maven 的两个核心机制区别：

---

## 一、**模块聚合（aggregation） vs 依赖引用（dependency）**

Maven 多模块项目中常见两种行为：

### 1. **模块聚合（`<modules>`）**

出现在父模块的 `pom.xml`：

```xml
<modules>
  <module>common</module>
  <module>service/service-a</module>
</modules>
```

> 意义：**告诉 Maven 在构建当前项目时，同时构建这些子模块**。但只是“构建顺序上的包含”，**不会自动成为依赖**。

---

### 2. **依赖引用（`<dependency>`）**

出现在子模块的依赖声明中：

```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>common</artifactId>
  <version>1.0.0</version>
</dependency>
```

> 意义：**将另一个模块编译输出的 JAR 包作为当前模块的依赖**，用于编译、运行。

---

## 二、为什么需要在依赖中添加自己的模块？

简单一句话：

> 如果你在一个模块中用到了另一个模块中的代码（类、方法、常量等），那你必须添加依赖。

### 举例：

比如：

- `common` 模块中定义了 `Result<T>`、工具类 `JsonUtil` 等；
    
- `service-a` 想要使用这些类：
    

你就必须在 `service-a/pom.xml` 添加：

```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>common</artifactId>
  <version>1.0.0</version>
</dependency>
```

否则 `service-a` 会在编译时报错：**找不到类 Result、JsonUtil 等**。

---

## 三、聚合 ≠ 自动依赖

这是很多初学者的误区。聚合只是告诉 Maven：

- **构建时一起编译**。
    
- **并不代表模块之间有依赖关系**。
    

所以如果你没显式声明依赖，模块间是互相看不到的。

---

## 四、总结

|场景|是否需要添加依赖|
|---|---|
|模块中使用了另一个模块的代码（类/方法/常量）|✅ 必须添加 `<dependency>`|
|只是想构建时一起编译|❌ 只需要 `<modules>`|
|模块之间没有调用关系，仅同属一个工程|❌ 只要聚合即可|

---

如果你有一个模块使用了另一个模块但没加依赖、却能编译通过，那要么：

1. 是 IDE 提供了智能引用（但打包会失败），
    
2. 是通过“传递依赖”间接引入的（不推荐依赖这种行为，建议显式声明）。
    

如需我帮你分析你现在的模块依赖情况是否合理，也可以贴出相关模块的 `pom.xml` 和引用类。