package www.Twilight;

import java.util.*;

public class Main {
}

/**
 * 1013 逆序对
 * 分治思想
 */
class T_1013 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int N = sc.nextInt();
            int[] nums = new int[N];
            for (int i = 0; i < N; i++) {
                nums[i] = sc.nextInt();
            }
            System.out.println(work(nums, 0, N - 1));
        }
    }

    /**
     * 把数组分成两部分，然后分别计算，然后合并
     * 和=左+右+合并
     * 合并时候原地排序好
     *
     * @param nums  原数组
     * @param left  左数组左边界下标
     * @param right 右数组右边界下标
     * @return 该部分逆序对数量（left到right）
     */

    public static int work(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int total = 0;
        int mid = (left + right) / 2;
        total += work(nums, left, mid);
        total += work(nums, mid + 1, right);
        for (int i = left; i <= mid; i++) {
            for (int j = mid + 1; j <= right; j++) {
                if (nums[i] > nums[j]) {
                    total += right - j + 1;
                    break;
                }
            }
        }


        List<Integer> temp = new ArrayList<>();
        int indexA = left, indexB = mid + 1;
        while (indexA <= mid && indexB <= right) {
            if (nums[indexA] >= nums[indexB]) {
                temp.add(nums[indexA++]);
            } else {
                temp.add(nums[indexB++]);
            }
        }

        while (indexA <= mid) {
            temp.add(nums[indexA++]);
        }
        while (indexB <= right) {
            temp.add(nums[indexB++]);
        }

        for (int i = 0; i < temp.size(); i++) {
            nums[left + i] = temp.get(i);
        }

        return total;
    }
}

/**
 * 1014 最大子数组和
 * 贪心
 */
class T_1014 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int N = sc.nextInt();
            int[] nums = new int[N];
            for (int i = 0; i < N; i++) {
                nums[i] = sc.nextInt();
            }

            int curSum = nums[0];
            int maxSum = nums[0];
            for (int i = 1; i < nums.length; i++) {
                //如果前面的子数组“拖累了”当前这个数字，就需要重新开始
                if (nums[i] > curSum + nums[i]) {
                    curSum = nums[i];
                } else {
                    curSum += nums[i];
                }
                maxSum = Math.max(maxSum, curSum);
            }
            System.out.println(maxSum);
        }
    }
}

/**
 * 1016 两数之和
 * （力扣经典，不多说明）
 */
class T_1016 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int N = sc.nextInt();
            int x = sc.nextInt();

            int[] nums = new int[N];
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < N; i++) {
                int temp = sc.nextInt();
                map.put(temp, 1);
                nums[i] = temp;
            }

            boolean flag = false;

            for (int i = 0; i < N; i++) {
                if (map.get(x - nums[i]) != null) {
                    System.out.println("yes");
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                System.out.println("no");
            }
        }
    }
}

/**
 * 1017 电路布线
 * 分治
 * （实际就是求逆序对，可以再理解一下思想，代码和1013相同）
 */
class T_1017 {
}

/**
 * 1018 0/1背包问题2
 * 动态规划
 * 变种背包，只有装满才能够带走
 */
class T_1018 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int N = sc.nextInt();
            int c = sc.nextInt();

            int[] weight = new int[N];
            int[] value = new int[N];

            for (int i = 0; i < N; i++) {
                weight[i] = sc.nextInt();
                value[i] = sc.nextInt();
            }

            int[][] dp = new int[N + 1][c + 1]; //dp[i][j]表示前i个物品中，容量为j的时候背包可以带走的最大价值，注意不是下标
            //表示为-1表示不能够带走，表示为0表示获得最大价值为0
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j <= c; j++) {
                    dp[i][j] = -1;
                }
            }
            for (int i = 0; i <= N; i++) {
                dp[i][0] = 0;
            }


            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= c; j++) {
                    //如果当前物品不能够放入
                    if (weight[i - 1] > j) {
                        dp[i][j] = dp[i - 1][j];
                    } else {//如果可以放入
                        //不放入
                        int a = dp[i - 1][j];
                        //放入
                        int b = dp[i - 1][j - weight[i - 1]];
                        if (b != -1) {
                            b = b + value[i - 1];
                        }
                        dp[i][j] = Math.max(a, b);
                    }
                }
            }

            System.out.println(dp[N][c] == -1 ? 0 : dp[N][c]);
        }
    }
}

/**
 * 1019 0/1背包问题
 * 动态规划
 * 最经典原始背包问题
 */
class T_1019 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int N = sc.nextInt();//物品数量
            int c = sc.nextInt();//容量

            int[] weight = new int[N];//物品重量
            int[] value = new int[N];//物品价值

            for (int i = 0; i < N; i++) {
                weight[i] = sc.nextInt();
                value[i] = sc.nextInt();
            }

            int[][] dp = new int[N + 1][c + 1];//dp[i][j]表示前i个物品中，容量为j的时候背包可以带走的最大价值，注意不是下标

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= c; j++) {
                    //当前物品背包放不下，注意当前物品的下标是i-1
                    if (weight[i - 1] > j) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        //如果可以放下，则选择两种情况，放入当前物品或者不放入。取最大值
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + value[i - 1]);
                    }
                }
            }

            System.out.println(dp[N][c]);
        }
    }
}

/**
 * 1020 矩阵连乘
 * 动态规划
 */
class T_1020 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int N = sc.nextInt();
            int[][] matrix = new int[N][2];
            for (int i = 0; i < N; i++) {
                matrix[i][0] = sc.nextInt();
                matrix[i][1] = sc.nextInt();
            }

            int[][] dp = new int[N][N]; //表示矩阵i到j的最小乘法代价，注意使用的是下标

            //对角线初始化
            for (int i = 0; i < N; i++) {
                dp[i][i] = 0;
            }

            for (int len = 2; len <= N; len++) {
                for (int left = 0; left <= N - len; left++) {
                    int right = left + len - 1;
                    dp[left][right] = Integer.MAX_VALUE;
                    for (int k = left; k < right; k++) {
                        int temp = dp[left][k] + dp[k + 1][right] + matrix[left][0] * matrix[k][1] * matrix[right][1];
                        dp[left][right] = Math.min(dp[left][right], temp);
                    }
                }
            }

            System.out.println(dp[0][N - 1]);
        }
    }
}

/**
 * 1021 钢条切割
 * 动态规划
 */
class T_1021 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            int length = sc.nextInt();
            int N = sc.nextInt();

            int[][] prices = new int[N][2]; //prices[i][0]表示长度，prices[i][1]表示价格
            for (int i = 0; i < N; i++) {
                prices[i][0] = sc.nextInt();
                prices[i][1] = sc.nextInt();
            }

            int[] dp = new int[length + 1];
            dp[0] = 0;
            for (int len = 1; len <= length; len++) {
                dp[len] = 0;
                for (int i = 0; i < N; i++) {
                    if (len < prices[i][0]) {
                        continue;
                    } else {
                        dp[len] = Math.max(dp[len - prices[i][0]] + prices[i][1], dp[len]);
                    }
                }
            }
            System.out.println(dp[length]);
        }
    }
}

/**
 * 1022 最长公共子序列
 * 动态规划
 */
class T_1022 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for (int m = 0; m < M; m++) {
            String a = sc.next();
            String b = sc.next();

            int[][] dp = new int[a.length() + 1][b.length() + 1]; //dp[i][j]表示a前i个字符和b前j个字符的最长公共子序列长度,不是下标
            dp[0][0] = 0;
            dp[0][1] = 0;
            dp[1][0] = 0;
            for (int i = 1; i <= a.length(); i++) {
                for (int j = 1; j <= b.length(); j++) {
                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }

            System.out.println(dp[a.length()][b.length()]);
        }
    }
}

/**
 * todo:不太会
 * 1024 最优二叉搜索树
 * 动态规划
 * 关键点：将某个区间作为子树后，它所有节点的深度都会加一，所以区间的概率总和是增加的“总代价”
 * 状态转移方程：某区间的最优代价=左代价+右代价+当前区间概率之和
 * 区间左闭右开
 */
class T_1024 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt(); // M组数据

        for (int m = 0; m < M; m++) {
            int N = sc.nextInt(); // N个关键字
            int[] keys = new int[N];
            for (int i = 0; i < N; i++) {
                keys[i] = sc.nextInt(); // 关键字本身，题目中不使用，仅输入而已
            }

            double[] p = new double[N];     // 关键字概率
            double[] q = new double[N + 1]; // 外部区间概率

            for (int i = 0; i < N; i++) {
                p[i] = sc.nextDouble();
            }
            for (int i = 0; i <= N; i++) {
                q[i] = sc.nextDouble();
            }

            // 动态规划数组
            double[][] dp = new double[N + 1][N + 1]; // dp[i][j] 表示从 i 到 j-1 的最优代价
            double[][] w = new double[N + 1][N + 1];  // w[i][j] 是概率和

            // 初始化 w 和 dp
            for (int i = 0; i <= N; i++) {
                dp[i][i] = 0; // 空子树代价为 0
                w[i][i] = q[i]; // w[i][i] 表示区间为空时，只包含外部概率
                for (int j = i + 1; j <= N; j++) {
                    w[i][j] = w[i][j - 1] + p[j - 1] + q[j];
                }
            }

            // 递推 dp
            for (int len = 1; len <= N; len++) { // 子树长度
                for (int i = 0; i <= N - len; i++) {
                    int j = i + len;
                    dp[i][j] = Double.MAX_VALUE;
                    for (int r = i; r < j; r++) { // 枚举根节点
                        double cost = dp[i][r] + dp[r + 1][j] + w[i][j];
                        if (cost < dp[i][j]) {
                            dp[i][j] = cost;
                        }
                    }
                }
            }

            // 输出结果，保留 6 位小数
            System.out.printf("%.6f\n", dp[0][N]);
        }
    }
}

/**
 * TODO:正确性证明
 * 1025 最长非降子序列
 * 朴素写法是直接用动态规划dp，但是会超时 O(n^2)
 * 使用贪心+二分优化 O(nlogn)
 */
class T_1025 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int M=sc.nextInt();

        for(int m=0;m<M;m++){
            int N=sc.nextInt();
            int[] nums=new int[N];
            for(int i=0;i<N;i++){
                nums[i]=sc.nextInt();
            }

            List<Integer> tails=new ArrayList<>(); //tails[i] 表示长度为 i+1 的最长非降子序列的最小末尾元素
            tails.add(nums[0]);
            for(int i=1;i<N;i++){
                boolean flag=false;
                //这边还可以使用二分搜索优化
                for(int j=0;j<tails.size();j++){
                    if(nums[i]<tails.get(j)){
                        tails.set(j,nums[i]);
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    tails.add(nums[i]);
                }
            }

            System.out.println(tails.size());
        }
    }
}

/**
 * TODO 不太会
 * 1026 插入乘号
 * 动态规划
 * 递推公式：
 *                                  long left = dp[i][t][x];
 *                                 long right = dp[t + 1][j][k - 1 - x];
 *                                 dp[i][j][k] = Math.max(dp[i][j][k], left * right);
 * 对于每一个参数进行枚举
 */
class T_1026 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt(); // 组数

        while (M-- > 0) {
            int N = sc.nextInt(); // 数字个数
            int K = sc.nextInt(); // 乘号个数
            int[] a = new int[N + 1]; // 数字从1开始
            long[][][] dp = new long[N + 1][N + 1][K + 1]; // dp[i][j][k]: i~j之间插k个乘号的最大值
            long[][] sum = new long[N + 1][N + 1]; // 区间和

            // 读入数字
            for (int i = 1; i <= N; i++) {
                a[i] = sc.nextInt();
            }

            // 预处理区间和
            for (int i = 1; i <= N; i++) {
                sum[i][i] = a[i];
                for (int j = i + 1; j <= N; j++) {
                    sum[i][j] = sum[i][j - 1] + a[j];
                }
            }

            // 初始化：区间内不插乘号，只用加号
            for (int i = 1; i <= N; i++) {
                for (int j = i; j <= N; j++) {
                    dp[i][j][0] = sum[i][j];
                }
            }

            // 枚举区间长度
            for (int len = 2; len <= N; len++) {
                for (int i = 1; i + len - 1 <= N; i++) {
                    int j = i + len - 1;

                    for (int k = 1; k <= K; k++) {
                        // 枚举断点
                        for (int t = i; t < j; t++) {
                            //枚举乘号数量
                            for (int x = 0; x <= k - 1; x++) {
                                long left = dp[i][t][x];
                                long right = dp[t + 1][j][k - 1 - x];
                                dp[i][j][k] = Math.max(dp[i][j][k], left * right);
                            }
                        }
                    }
                }
            }

            System.out.println(dp[1][N][K]);
        }

        sc.close();
    }
}

/**
 * 1027 带权活动选择
 * 动态规划
 */
class T_1027{
    static class activity{
        int s;
        int f;
        int v;
        public activity(int s, int f, int v) {
            this.s = s;
            this.f = f;
            this.v = v;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for(int m=0;m<M;m++){
            int N=sc.nextInt();
            activity[] activities=new activity[N];
            for(int i=0;i<N;i++){
                activities[i]=new activity(sc.nextInt(),sc.nextInt(),sc.nextInt());
            }
            //按照结束时间升序排序
            Arrays.sort(activities, new Comparator<activity>() {
                @Override
                public int compare(activity o1, activity o2) {
                    return o1.f - o2.f;
                }
            });
            int[] dp=new int[N+1];//dp[i] 表示前i个活动中，选择活动后的最大价值

            for (int i = 1; i <= N; i++) {
                int idx = calIndex(activities, activities[i - 1].s);
                dp[i] = Math.max(dp[i - 1], dp[idx + 1] + activities[i - 1].v);
            }

            System.out.println(dp[N]);
        }
    }

    /**
     * 根据结束时间计算活动的索引
     * @return 活动的索引
     */
    public static int calIndex(activity[] activities,int target){
        int low = 0, high = activities.length - 1;
        int ans = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (activities[mid].f <= target) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

}

/**
 * TODO 重要！不太会 证明？
 * 1030 黑白连线
 * 贪心
 * 曼哈顿距离的变种
 */
class T_1030{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        for(int m=0;m<M;m++){
            int N = sc.nextInt();
            int[] black=new int[N];
            int[] white=new int[N];
            int a=0;
            int b=0;
            for(int i=0;i<2*N;i++){
                int temp=sc.nextInt();
                if(temp==1){
                    black[a++]=i;
                }
                else{
                    white[b++]=i;
                }
            }
            int total=0;
            for(int i=0;i<N;i++){
                total+=Math.abs(black[i]-white[i]);
            }
            System.out.println(total);
        }

    }
}

/**
 * 1032 岛国难题
 * 概率 树的基础知识
 * 树初始是联通的，连通块为1，每断一条边，连通块的数量 +1
 */
class T_1032{
    public static void main(String[] args) {
        List<Double> res=new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int i = 0; i < T; i++) {
            int n = scanner.nextInt();
            int totalProb = 0;

            for (int j = 0; j < n - 1; j++) {
                totalProb += scanner.nextInt();
            }

            double result = 1.0 + totalProb / 100.0;

            res.add(result * 1000000);
        }
        for (Double b : res) {
            System.out.printf("%.6f\n", b / 1000000);
        }
    }
}

/**
 * TODO 重要！
 * 1033 机器作业
 * 贪心
 */
class T_1033{

    static class work{
        int endTime;
        int value;
        work(int endTime,int value){
            this.endTime=endTime;
            this.value=value;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T=sc.nextInt();
        for(int t=0;t<T;t++){
            int N=sc.nextInt();
            List<work> works=new ArrayList<>();
            for(int i=0;i<N;i++){
                int endTime=sc.nextInt();
                int value=sc.nextInt();
                works.add(new work(endTime,value));
            }
            works.sort(new Comparator<work>() {
                @Override
                public int compare(work o1, work o2) {
                    return o1.endTime-o2.endTime;
                }
            });
            PriorityQueue<Integer> queue=new PriorityQueue<>();

            for(int i=0;i<N;i++){
                if(queue.size()<works.get(i).endTime){
                    queue.add(works.get(i).value);
                }
                else{
                    if(queue.peek()<works.get(i).value){
                        queue.poll();
                        queue.add(works.get(i).value);
                    }
                }
            }

            int total=0;
            while(!queue.isEmpty()){
                total+=queue.poll();
            }
            System.out.println(total);
        }

    }
}