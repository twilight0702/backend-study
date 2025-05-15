package www.Twilight.day1;

import java.util.HashMap;
import java.util.Objects;

public class leedcode {
    public static void main(String[] args) {
        int[] nums = {3,2,4};
        int target = 6;
        Solution s = new Solution();
        int[] res = s.twoSum(nums,target);
        System.out.println(res[0]+" "+res[1]);
    }
}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);
        }

        for(int i=0;i<nums.length;i++){
            int t=target-nums[i];
            if(map.get(t)!=null&&map.get(t)!=i){
                return new int[]{i,map.get(t)};
            }
        }
        return new int[]{-1,-1};
    }
}