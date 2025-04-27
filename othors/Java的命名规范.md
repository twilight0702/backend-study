# 基本原则

Java 的命名规范是 Java 编程风格和可维护性的重要组成部分，主要遵循 **驼峰命名法（Camel Case）** 和 **统一的风格规则**

| 类型       | 命名风格      | 示例                            |
| -------- | --------- | ----------------------------- |
| 包名       | 全小写       | `com.example.project`         |
| 类名 / 接口名 | 大驼峰式      | `StudentManager`              |
| 方法名      | 小驼峰式      | `getStudentName()`            |
| 变量名      | 小驼峰式      | `studentName`                 |
| 常量名      | 全大写 + 下划线 | `MAX_SIZE`, `DEFAULT_TIMEOUT` |
| 枚举常量名    | 全大写 + 下划线 | `RED`, `GREEN`                |
| 泛型参数     | 单个大写字母    | `T`, `E`, `K`, `V`            |

---
# 命名规范细则

### 1. 包（Package）

- 全部使用小写字母，避免使用下划线。
    
- 使用公司域名倒置 + 项目名 + 模块名。
    
- 示例：`com.google.gson`, `org.apache.commons.lang3`

### 2. 类（Class）与 接口（Interface）

- 使用**大驼峰式（PascalCase）**，每个单词首字母大写。
    
- 使用名词，体现其“实体”特性。
    
- 接口命名可使用形容词或“能”做什么的动词，如 `Runnable`, `Serializable`。
    
- 不使用缩写（除非非常常见如 `URL`, `HTML`）。

### 3. 方法（Method）

- 使用**小驼峰式（camelCase）**。
    
- 命名应为动词或动宾结构，表示行为。
    
- 示例：`calculateSum()`, `getName()`, `isAvailable()`

### 4. 变量（Variable）

- 使用小驼峰式。
    
- 命名应表达变量的意义，避免使用 `a`, `b`, `x1` 等无意义变量名（除非在非常局部的上下文中）。
    
- 示例：`studentAge`, `totalPrice`

### 5. 常量（Constant）

- 使用全大写字母 + 下划线分隔。
    
- 通常为 `public static final` 修饰。
    
- 示例：`MAX_VALUE`, `DEFAULT_PORT`

### 6. 枚举（Enum）

- 枚举类型使用大驼峰式，如 `ColorType`。
    
- 枚举常量使用全大写 + 下划线：`RED`, `DARK_BLUE`

### 7. 泛型类型参数

- 通常使用单个大写字母：
    
    - `T`：Type
        
    - `E`：Element
        
    - `K`：Key
        
    - `V`：Value
        
    - `N`：Number
        
    - `R`：Result

### 8. 注解（Annotation）

- 使用大驼峰式，通常以 `@` 开头。
    
- 示例：`@Override`, `@Deprecated`, `@Controller`

### 9. 测试方法命名（JUnit）

- 常用格式：`方法名_测试条件_期望结果`
    
- 示例：`addUser_whenUserIsValid_shouldSucceed()`

# 常用单词

## 一、通用动作（动词）

|英文|含义|常用于|
|---|---|---|
|`get`|获取|`getName()`|
|`set`|设置|`setName()`|
|`add`|添加|`addItem()`|
|`remove`|移除|`removeUser()`|
|`create`|创建|`createFile()`|
|`update`|更新|`updateInfo()`|
|`delete`|删除|`deleteItem()`|
|`load`|加载|`loadData()`|
|`save`|保存|`saveUser()`|
|`build`|构建|`buildQuery()`|
|`check`|检查|`checkValid()`|
|`validate`|验证|`validateForm()`|
|`init`|初始化|`initSystem()`|
|`reset`|重置|`resetForm()`|
|`calculate`|计算|`calculateSum()`|
|`is`|是否（布尔）|`isEmpty()`|
|`has`|是否拥有|`hasPermission()`|

---

## 二、常用名词（表示实体或属性）

|英文|含义|常用于|
|---|---|---|
|`user`|用户|`userName`, `UserService`|
|`name`|名字|`getName()`|
|`id`|编号|`userId`, `id`|
|`list`|列表|`userList`|
|`map`|映射表|`configMap`|
|`info`|信息|`userInfo`|
|`data`|数据|`rawData`|
|`type`|类型|`fileType`|
|`status`|状态|`orderStatus`|
|`count`|数量|`itemCount`|
|`value`|值|`totalValue`|
|`result`|结果|`queryResult`|
|`message`|消息|`errorMessage`|
|`date`|日期|`createDate`|
|`file`|文件|`filePath`|
|`config`|配置|`configFile`|
|`error`|错误|`errorCode`|

---

## 三、布尔变量常用前缀（is / has / can 等）

|词汇|示例|
|---|---|
|`is`|`isActive`, `isValid`|
|`has`|`hasPermission`, `hasValue`|
|`can`|`canEdit`, `canDelete`|
|`should`|`shouldRetry`, `shouldDisplay`|
|`need`|`needLogin`, `needRefresh`|

---

## 四、类/接口常见后缀（表示结构、作用）

|后缀|含义|示例|
|---|---|---|
|`Service`|业务类|`UserService`|
|`Manager`|管理类|`LoginManager`|
|`Controller`|控制器类|`UserController`|
|`Util`|工具类|`StringUtil`|
|`Helper`|辅助类|`DateHelper`|
|`Factory`|工厂类|`UserFactory`|
|`Handler`|处理器类|`RequestHandler`|
|`Repository`|数据仓库|`UserRepository`|
|`DTO`|数据传输对象|`UserDTO`|
|`VO`|视图对象|`UserVO`|
|`Entity`|数据实体|`UserEntity`|

---

## 五、常用缩略词（需注意大小写一致性）

|缩写|原词|备注|
|---|---|---|
|`ID`|Identifier|`userId`, `getID()`|
|`URL`|Uniform Resource Locator|`imageURL`|
|`XML`|Extensible Markup Language|`xmlParser`|
|`JSON`|JavaScript Object Notation|`jsonData`|
|`DB`|Database|`dbHelper`|
|`API`|Application Programming Interface|`apiResponse`|
|`HTML`|Hypertext Markup Language|`htmlContent`|


