# 缓冲区排序（外排序）

key value
用key排序

前面说的排序都是内存中的排序，这里讲的是内存放不下的排序
磁盘慢

常见用merge sort
排序好的放到磁盘上
# 矩阵乘法

举例：矩阵乘法
三重循环
ijk的顺序可以更换（因为每一层循环用+=）
但磁盘访问效率不同
对于连续数据的访问效率更高
$O(n^3)$ 
[[#1|详细解释]]

# 快速排序

## 内存的快排

- 使用一个pivot，左右归

## 内存放不下？

（ppt理解）

[[#2|详细解释]]
middle group中要不断维护最大和最小 -> 堆

# 归并排序

产生run，合并run 
[[#3|详细过程]]

需要更长的run，减少合并次数，提高效率（？），再长的run都是从内存过一次
如何做到?
-> 在 run 生成阶段，尽可能多地组织和利用内存数据，使得每次写出的 run 更长、更有序，从而减少后续归并次数

（上课回听理解）
（还有ppt）

使用buffer




今天的内容比较简单（可能考试考？）

# R 树

打车？
三分钟画圆能够到达的地方
（上课回听）
怎么找到附近的出租车 问题
B+树




# 详细解释

## 1

当然，以下是不含表情符号的 Obsidian 笔记版本，内容更加正式，并对不同循环顺序的性能差异做了明确说明：

---

### 矩阵乘法的三重循环顺序是否可以更换？

是的，矩阵乘法中的三重循环顺序可以更换。不同的循环顺序在数学上是等价的，结果完全一致。但在实际程序运行中，性能差异可能非常显著，特别是在处理大规模矩阵时。

---

#### 基本定义

设矩阵：

- $A \in \mathbb{R}^{m \times n}$
    
- $B \in \mathbb{R}^{n \times p}$
    
- $C = A \times B \in \mathbb{R}^{m \times p}$
    

其乘法定义为：

C[i][j]=∑k=0n−1A[i][k]⋅B[k][j]C[i][j] = \sum_{k=0}^{n-1} A[i][k] \cdot B[k][j]

---

#### 常见的三重循环实现

```cpp
for (int i = 0; i < m; i++)
    for (int j = 0; j < p; j++)
        for (int k = 0; k < n; k++)
            C[i][j] += A[i][k] * B[k][j];
```

这种实现对应循环顺序：i → j → k

三重循环共有 6 种可能顺序：

1. i-j-k
    
2. i-k-j
    
3. j-i-k
    
4. j-k-i
    
5. k-i-j
    
6. k-j-i
    

---

#### 数学正确性与性能差异

- 数学上，上述六种顺序都能得到相同的结果。
    
- 性能上，不同顺序会引起明显差异，尤其在多次循环迭代或大规模矩阵下。
    

性能差异的主要原因是**内存访问模式**是否**连续友好**。现代处理器有缓存系统，如果数据访问是线性且连续的，缓存命中率高，性能更优。

在 C/C++ 中，二维数组采用**行优先（row-major）存储**，即：

- `A[i][k]` 的访问是连续的，效率高。
    
- `B[k][j]` 的访问可能是跨行的，效率低。
    
- `C[i][j]` 的写入若按行顺序则效率更高。
    

---

#### 推荐顺序：i-k-j

```cpp
for (int i = 0; i < m; i++)
    for (int k = 0; k < n; k++)
        for (int j = 0; j < p; j++)
            C[i][j] += A[i][k] * B[k][j];
```

该顺序下：

- `A[i][k]` 是按行访问，连续性好。
    
- `C[i][j]` 是按行访问，写入效率高。
    
- `B[k][j]` 是跨行访问，但由于 `k` 是内层第二层循环，`B[k][j]` 仍有一定重复利用率，可通过**预先转置 B 矩阵**优化。
    

该顺序在多数情况下性能最佳，尤其适用于行主序语言（如 C/C++）。

---

#### 不同顺序的性能比较分析

以下是一些典型顺序的性能特征：

|循环顺序|数学正确性|内存访问效率|性能表现（C/C++）|
|---|---|---|---|
|i-k-j|正确|好|通常最佳|
|i-j-k|正确|中等|中等|
|j-k-i|正确|较差|较差|
|k-i-j|正确|较差|较差|
|j-i-k|正确|一般|一般|
|k-j-i|正确|最差|最差|

---

#### 结论

- 三重循环顺序可以任意更换，**数学结果不会变**。
    
- **性能会显著变化**，尤其在处理大矩阵或多线程时。
    
- 在 C/C++ 中，**推荐采用 i-k-j 顺序**，并在必要时对 B 矩阵进行转置优化，进一步提高缓存利用率。
    



## 2

你说的这个思路是**基于快速排序思想的外部排序**，通常称为：

> **外部三分区快排（External QuickSort with Multi-Buffers）**

这种方式不是传统快排的直接外部化，而是根据**快速排序“分区”思想**，结合**多缓冲区划分内存区域**，将输入数据**划分为 small、middle、large 三块**，然后递归地对大块继续分区，直到数据可以在内存中处理。

---

### ✅ 核心思路

将内存分成如下几个缓冲区：

|缓冲区|作用|
|---|---|
|Input Buffer|从外部读取未处理的数据|
|Small Buffer|存储比 pivot 小的数据|
|Middle Buffer|存储等于 pivot 的数据|
|Large Buffer|存储比 pivot 大的数据|

当其中某个缓冲区满时，将其内容写入对应的临时文件：

- `small_*.bin`：用于递归处理“小于 pivot”的数据。
    
- `large_*.bin`：用于递归处理“大于 pivot”的数据。
    
- `middle` 通常一次性全部写出，不再递归处理。
    

---

### ✅ 整体流程图（文字描述）

1. **选取 pivot（枢轴）**：从样本或第一批读入数据中选一个合理的值。
    
2. **初始化四个缓冲区**：input、small、middle、large。
    
3. **流式读取输入文件**到 input buffer 中，依次判断每个元素与 pivot 的关系，放入 small / middle / large buffer 中。
    
4. **某个 buffer 满时，写出到对应临时文件**。
    
5. 所有数据划分完毕后，对 `small*` 和 `large*` 临时文件**递归处理**。
    
6. 最后按顺序合并：`sorted_small` + `middle` + `sorted_large`，写入最终输出。
    

---

### ✅ 简化伪代码（Python 风格）

```python
def external_quicksort(input_file, output_file, memory_limit):
    if file_size(input_file) <= memory_limit:
        data = load_and_sort(input_file)  # 快速排序
        write_file(output_file, data)
        return

    pivot = choose_pivot(input_file)

    small_files, large_files, middle_data = [], [], []
    buffers = {
        'small': [], 'middle': [], 'large': []
    }
    
    with open(input_file, 'r') as f:
        for line in f:
            x = int(line)
            if x < pivot:
                buffers['small'].append(x)
            elif x == pivot:
                buffers['middle'].append(x)
            else:
                buffers['large'].append(x)

            for key in ['small', 'large']:
                if len(buffers[key]) * 4 > memory_limit // 4:
                    tmp_file = dump_to_temp(buffers[key], key)
                    (small_files if key == 'small' else large_files).append(tmp_file)
                    buffers[key] = []

    # flush middle to one temp file
    middle_file = dump_to_temp(buffers['middle'], 'middle')

    # sort recursively
    sorted_small = merge_sorted_partitions(
        [external_quicksort(f, f + '.sorted', memory_limit) for f in small_files]
    )
    sorted_large = merge_sorted_partitions(
        [external_quicksort(f, f + '.sorted', memory_limit) for f in large_files]
    )

    merge_final(sorted_small, middle_file, sorted_large, output_file)
```

---

### ✅ 优点

- 利用了快速排序“分区”的核心思想。
    
- 非常适合**不能全量加载数据进内存**的场景。
    
- 若选取的 pivot 合理，递归层数小，效率高。
    

---

### ✅ 注意事项

- **pivot 的选择策略很关键**，可用“随机样本中位数”来估计。
    
- 每一轮的文件操作都要**尽可能减少临时文件数量**，防止 I/O 过多。
    
- 可以将 middle 区数据先缓存完，再顺序写入，减少文件数量。
    

## 3

外排序中的归并排序（**External Merge Sort**）是**处理大规模数据排序的经典算法**，用于当数据**无法一次性全部加载进内存**的场景。

---

### ✅ 基本思想

> 把外部数据分块加载到内存中，每块排序后写回磁盘，然后再**多路归并**这些有序块。

---

### ✅ 操作流程

#### **第一阶段：生成初始有序子段（Run Generation）**

1. 将大文件**划分为若干个能放入内存的块**。
    
2. 每块读入内存后使用**内部排序算法（如快排或堆排）**排序。
    
3. 将排序后的块**写回磁盘**，形成若干个**有序临时文件**（称为**初始归并段**）。
    

📌 假设总数据量为 `100GB`，内存只能装 `100MB`，那么会生成约 `1000` 个临时文件。

---

#### **第二阶段：归并排序（Multi-way Merge）**

将多个有序临时文件**归并为一个大文件**，归并过程可以分为多轮，直到最终合并为一个文件：

- 每轮从多个文件中**各读取一部分数据**（如每个文件开一个缓冲区）。
    
- 使用**最小堆/优先队列**找出当前最小值，写入结果文件。
    
- 对应文件读取下一条数据继续归并。
    

📌 这种方法称为 **k 路归并**，k 通常由可用内存和缓冲区大小决定。

---

### ✅ 图示（逻辑步骤）

假设内存一次最多放 3 个文件的数据（k = 3）：

```
初始文件分块排序：
   输入文件 → [块1] → sort → 临时1.txt
               ↓
               [块2] → sort → 临时2.txt
               ↓
               ...
               [块N] → sort → 临时N.txt

第一轮归并：
   临时1.txt + 临时2.txt + 临时3.txt → 合并 → out1.txt
   临时4.txt + 临时5.txt + 临时6.txt → 合并 → out2.txt
   ...

第二轮归并：
   out1.txt + out2.txt + out3.txt → 合并 → 最终输出
```

---

### ✅ 时间复杂度分析

- 假设：
    
    - 总数据量为 `N`
        
    - 内存大小为 `M`
        
    - 块大小为 `B`
        
    - 最多能归并 `k = M / B` 路
        
- **排序阶段**：$O(N \log M)$（每块内部排序）
    
- **归并阶段**：
    
    - 归并轮数：$O(\log_k (N/M))$
        
    - 每轮需要扫描所有数据：$O(N)$
        
    - 总复杂度：$O(N \log_k (N/M))$
        

---

### ✅ 优点

- 能处理任意大规模的数据，只要磁盘空间足够。
    
- 利用归并的线性特性，**磁盘读写效率高**。
    
- 可通过多路归并降低归并轮数，提升效率。
    

---

### ✅ 实用技巧

- 使用 **最小堆/优先队列** 管理归并过程。
    
- 每个文件分配一个 **输入缓冲区**，避免频繁磁盘 I/O。
    
- 输出也使用缓冲，**批量写入**磁盘，提高效率。
    
- 支持**并行归并**或多线程加速归并过程。
