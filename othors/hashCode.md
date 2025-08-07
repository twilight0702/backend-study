`hashCode` 是 Java 中定义在 `Object` 类中的一个方法，签名如下：

```java
public int hashCode()
```

它的作用是：**返回对象的哈希码（hash code），即一个整数，用于支持哈希表等基于哈希的数据结构的快速查找。**

---

## 一、`hashCode` 是什么？

简单来说，`hashCode` 是一种将对象映射为整数的机制。

- 这个整数称为**哈希值**，可以看作是对象的“数字指纹”。
    
- 在哈希结构（如 `HashMap`、`HashSet`、`Hashtable`）中，`hashCode` 决定了对象放在哪个“桶（bucket）”中。
    

---

## 二、为什么需要 `hashCode`？

哈希结构中，查找元素的效率依赖于将对象快速定位到某个位置（桶）。过程如下：

1. 调用对象的 `hashCode()` 方法，获得一个整数。
    
2. 通过该整数与桶的数量进行计算（如取模运算 `hash % n`），确定该对象应该放入哪个桶。
    
3. 再在这个桶中用 `equals()` 精确查找。
    

这就是**哈希表**的基本原理。

---

## 三、`hashCode` 的默认行为

在 `Object` 类中，默认的 `hashCode` 实现如下（通常）：

```java
public native int hashCode();
```

JVM 默认实现返回的是对象的内存地址相关的整数（不是绝对地址，而是根据地址计算的一个数值）。

因此，不重写时，不同对象通常会返回不同的 hashCode。

---

## 四、自定义类中如何重写 `hashCode`

当我们希望自定义“对象相等”规则（即重写 `equals()`）时，也必须重写 `hashCode()`，否则会破坏哈希结构的逻辑一致性。

### 例子：

```java
public class Person {
    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        return this.age == other.age && this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);  // 推荐用 JDK 的工具类生成
    }
}
```

这样才能保证：

- 两个 `Person` 对象若 `equals()` 为 true，它们的 `hashCode()` 也一定相同。
    

---

## 五、`hashCode` 的应用场景

|场景|用途|
|---|---|
|`HashMap`|根据 `hashCode()` 快速定位键的位置|
|`HashSet`|检查元素是否已存在|
|`Hashtable`|类似 `HashMap`|
|`LinkedHashMap`|保证顺序，同时用到 `hashCode`|

---

## 六、常见误区

- 认为 `hashCode` 相等就代表两个对象相等 —— **错误！**
    
    - 相等的对象必须具有相同的 `hashCode`，但 `hashCode` 相同的对象不一定相等（会发生哈希冲突）。
        

---

## 七、总结一句话

> `hashCode()` 是一个返回整数哈希值的方法，用于对象在哈希结构中的定位，必须与 `equals()` 保持一致性。

---

如需深入了解哈希冲突的处理、hashCode 的设计技巧或 JDK 中某些类（如 String、Integer）的 hashCode 实现，也可以继续提问。