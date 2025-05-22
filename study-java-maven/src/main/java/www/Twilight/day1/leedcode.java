package www.Twilight.day1;


import java.util.*;

public class leedcode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.generateParenthesis(3));
    }
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res=new ArrayList<>();

        solve(res,n,n,new StringBuilder());
        return  res;
    }

    private void solve(List<String> res,int left,int right,StringBuilder cur){
        if(left==0  && right==0){
            res.add(cur.toString());
            return;
        }
        if(left<right){
            right--;
            cur.append(')');
            solve(res,left,right,cur);
            cur.deleteCharAt(cur.length()-1);
            right++;
        }
        if(left>=1){
            left--;
            cur.append('(');
            solve(res,left,right,cur);
            cur.deleteCharAt(cur.length()-1);
            left++;
        }
    }


}
class LRUCache {
    class Node {
        //这里有没有public的区别是？
        int key;
        int value;
        Node front;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            next = null;
            front = null;
        }
    }

    int capacity;
    Map<Integer, Node> map;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.front = head;
    }

    public int get(int key) {
        Node temp = map.get(key);
        if (temp != null) {
            temp.front.next = temp.next;
            temp.next.front = temp.front;

            temp.front = tail.front;
            temp.next = tail;
            tail.front.next = temp;
            tail.front = temp;
            return temp.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node temp = map.get(key);
        if (temp != null) {
            temp.value = value;
            get(key);
        } else {
            if (map.size() == capacity) {
                Node node = head.next;
                map.remove(node.key);
                node.front.next = node.next;
                node.next.front = node.front;
                node.next = null;
                node.front = null;
            }
            Node node = new Node(key, value);
            Node f = tail.front;
            node.front = f;
            f.next = node;
            tail.front = node;
            node.next = tail;
            map.put(key, node);
        }
    }
}
