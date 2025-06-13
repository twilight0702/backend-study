# 常用的库

## Numpy

**背景介绍**：

- 最早是为了进行**高效数值计算**设计的库，几乎所有科学计算库（如Pandas、TensorFlow）底层都依赖它。
    
- 解决了Python原生列表速度慢、功能弱的问题。
    

🔹 **主要特点**：

- **ndarray**：核心数据结构，多维数组，比Python自带list高效很多。
    
- 支持**广播（Broadcasting）**机制：不同形状数组也能一起计算。
    
- 提供**线性代数（矩阵运算）**、**傅里叶变换**、**随机数生成**等模块。
    

🔹 **常见功能模块**：

- `np.array()` 创建数组
    
- `np.linalg` 线性代数
    
- `np.fft` 傅里叶变换
    
- `np.random` 随机数模块
    
- `np.save()`、`np.load()` 数组存取
    

🔹 **应用场景**：

- 科学计算、工程模拟
    
- 图像处理（把图片当作数组处理）
    
- 机器学习数据预处理（如归一化、矩阵特征提取）

## Pandas

**背景介绍**：

- 针对**结构化数据**处理（类似数据库中的表），非常适合做数据分析、金融统计。
    
- 由Wes McKinney于2008年发起。
    

🔹 **主要特点**：

- 两大数据结构：
    
    - **Series**：一维数组，带索引。
        
    - **DataFrame**：二维表格（行列都有名字）。
        
- 方便的数据清洗、缺失值处理、分组统计、数据透视表等功能。
    
- 支持**多种文件格式**读取（如CSV、Excel、SQL数据库等）。
    

🔹 **常见功能模块**：

- `pd.read_csv()`、`pd.to_csv()` 文件读写
    
- `df.groupby()` 分组统计
    
- `df.merge()`、`df.concat()` 表格合并
    
- `df.pivot_table()` 透视表
    
- `df.fillna()`、`df.dropna()` 缺失值处理
    

🔹 **应用场景**：

- 数据科学前期的数据清洗（Data Cleaning）
    
- 数据可视化准备（配合Matplotlib、Seaborn）
    
- 业务数据分析、报表制作

##  Scikit-Learn

🔹 **背景介绍**：

- 2007年由INRIA（法国国家研究院）开发，建立在NumPy、SciPy、Matplotlib之上。
    
- 面向**传统机器学习**，而非深度学习。
    

🔹 **主要特点**：

- 内置大量**经典机器学习算法**：线性回归、决策树、SVM、KNN、随机森林等。
    
- 流程标准化：
    
    1. 数据预处理
        
    2. 划分训练集、测试集
        
    3. 模型训练
        
    4. 模型预测
        
    5. 模型评估
        
- API统一，学习和切换算法非常简单。
    

🔹 **常见功能模块**：

- `sklearn.model_selection` 划分数据（如train_test_split）
    
- `sklearn.preprocessing` 数据预处理（如标准化）
    
- `sklearn.linear_model` 线性回归、逻辑回归
    
- `sklearn.tree` 决策树
    
- `sklearn.cluster` 聚类分析
    
- `sklearn.metrics` 评估指标
    

🔹 **应用场景**：

- 特征工程、模型选择
    
- 小规模项目的快速建模
    
- 原型验证（Proof of Concept）
## PyTorch

🔹 **背景介绍**：

- 由Facebook AI Research Lab（FAIR）开发，2016年首次发布。
    
- 特点是**动态图计算（eager execution）**，代码执行即构建计算图。
    

🔹 **主要特点**：

- 灵活、直观，像写普通Python代码一样训练神经网络。
    
- 提供**自动求导（autograd）**功能，方便反向传播。
    
- 兼容GPU加速（用CUDA）。
    
- 丰富的生态，如**TorchVision**（图像）、**TorchText**（文本）、**TorchAudio**（语音）。
    
- 后期可以使用TorchScript进行部署优化。
    

🔹 **常见功能模块**：

- `torch.tensor` 张量对象
    
- `torch.nn` 模型构建模块
    
- `torch.optim` 优化器（SGD、Adam）
    
- `torch.utils.data` 数据加载
    
- `torchvision` 预处理、预训练模型
    

🔹 **应用场景**：

- 计算机视觉（CV）项目（如图像分类、目标检测）
    
- 自然语言处理（NLP）（如文本分类、机器翻译）
    
- 研究实验（如新的神经网络架构）

## TensorFlow

**背景介绍**：

- 由Google Brain团队开发，2015年发布。
    
- 最初为大规模分布式系统设计，强调**高性能**和**可部署性**。
    

🔹 **主要特点**：

- 早期是**静态计算图**（先建图后执行），2.x版本后支持动态图（与PyTorch更接近）。
    
- **Keras**作为默认高级API，易用性大幅提升。
    
- 支持多种部署方式：服务器、移动端（TensorFlow Lite）、浏览器端（TensorFlow.js）。
    
- 可以分布式训练，适合工业生产级任务。
    

🔹 **常见功能模块**：

- `tf.Tensor` 张量对象
    
- `tf.keras` 高层API（构建模型）
    
- `tf.data` 数据流水线
    
- `tf.distribute` 分布式训练
    
- `tf.saved_model` 模型保存与加载
    

🔹 **应用场景**：

- 生产环境部署（如谷歌搜索引擎推荐系统）
    
- 大规模训练任务（如多GPU、多服务器）
    
- 跨平台应用（移动端/浏览器端推理）