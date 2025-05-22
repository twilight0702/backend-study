package com.Twilight.Day2;

import java.math.BigInteger;
import java.util.*;

class Solution {
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] paths = path.split("/");
        for (String s : paths) {
            if (Objects.equals(s, "..")) {
                if(!stack.isEmpty()){
                    stack.pop();
                }
            }
            else if(!Objects.equals(s, "")&&!Objects.equals(s, ".")){
                stack.push(s);
            }
        }
        String res="";
        while  (!stack.isEmpty()) {
            res = "/" + stack.pop() + res;
        }
        return res.isEmpty() ? "/" : res;
    }
}


public class LeetCode {
    public static void main(String[] args) {
        String input="/home/";
        Solution s=new Solution();
        String res=s.simplifyPath(input);
        System.out.println(res);
    }
}
