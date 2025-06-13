package www.Twilight;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 给你一个字符串 s 和一个整数 k ，请你找出 至多 包含 k 个 不同 字符的最长子串，并返回该子串的长度。
 *
 * 示例 1：
 * 输入：s = "eceba", k = 2
 * 输出：3
 * 解释：满足题目要求的子串是 "ece" ，长度为 3 。
 * 示例 2：
 * 输入：s = ""abaccc"", k = 2
 * 输出：4
 * 解释：满足题目要求的子串是 "accc" ，长度为 4 。
 * 提示：
 * •	1 <= s.length <= 5 * 104
 * •	0 <= k <= 50
 * •	算法时间复杂度<O(n^2)
 */
public class Test {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String s=sc.next();
        int k=sc.nextInt();

        int left=0;
        int right=0;
        int maxLen=-1;
        Map<Character,Integer> map=new HashMap<>();
        while(right<s.length()){
            //如果还没有超过k个不同字符就继续右移右指针
            if(map.size()<=k){
                if(map.get(s.charAt(right))!=null){
                    map.put(s.charAt(right),map.get(s.charAt(right))+1);
                }
                else{
                    map.put(s.charAt(right),1);
                }

                right++;
            }
            else{
                maxLen=Math.max(maxLen,right-left-1);
                //左移左指针直到有小于等于k个不同字符
                while(map.size()>k){
                    map.put(s.charAt(left),map.get(s.charAt(left))-1);
                    if(map.get(s.charAt(left))==0){
                        map.remove(s.charAt(left));
                    }
                    left++;
                }
            }
        }
        if(map.size()<=k) {
            maxLen = Math.max(maxLen, right - left);
        }

        System.out.println(maxLen);
    }
}
