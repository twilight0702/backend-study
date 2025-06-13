package www.Twilight.day1;

import java.util.*;

public class leedcode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        //[7,1,5,3,6,4]
        System.out.println(solution.maxProfit(new int[]{7,1,5,3,6,4}));

    }
}


class Solution {
    public int maxProfit(int[] prices) {
        int start=prices[0];

        int sum=0;
        for(int i=1;i<prices.length;i++){
            if(prices[i]<prices[i-1]){
                sum+=prices[i-1]-start;
                start=prices[i];
            }
        }
        sum+=prices[prices.length-1]-start;

        return sum;
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

//class LRUCache {
//    class Node {
//        //这里有没有public的区别是？
//        int key;
//        int value;
//        Node front;
//        Node next;
//
//        Node(int key, int value) {
//            this.key = key;
//            this.value = value;
//            next = null;
//            front = null;
//        }
//    }
//
//    int capacity;
//    Map<Integer, Node> map;
//    Node head;
//    Node tail;
//
//    public LRUCache(int capacity) {
//        this.capacity = capacity;
//        map = new HashMap<>();
//        head = new Node(-1, -1);
//        tail = new Node(-1, -1);
//        head.next = tail;
//        tail.front = head;
//    }
//
//    public int get(int key) {
//        Node temp = map.get(key);
//        if (temp != null) {
//            temp.front.next = temp.next;
//            temp.next.front = temp.front;
//
//            temp.front = tail.front;
//            temp.next = tail;
//            tail.front.next = temp;
//            tail.front = temp;
//            return temp.value;
//        }
//        return -1;
//    }
//
//    public void put(int key, int value) {
//        Node temp = map.get(key);
//        if (temp != null) {
//            temp.value = value;
//            get(key);
//        } else {
//            if (map.size() == capacity) {
//                Node node = head.next;
//                map.remove(node.key);
//                node.front.next = node.next;
//                node.next.front = node.front;
//                node.next = null;
//                node.front = null;
//            }
//            Node node = new Node(key, value);
//            Node f = tail.front;
//            node.front = f;
//            f.next = node;
//            tail.front = node;
//            node.next = tail;
//            map.put(key, node);
//        }
//    }
//}


class LRUCache {

    int capacity;
    Node head;
    Node tail;
    Map<Integer,Node> map;

    class Node{
        int key;
        int value;
        Node front;
        Node next;
        Node(int key,int value){
            this.key=key;
            this.value=value;
            front=null;
            next=null;
        }
    }

    public LRUCache(int capacity) {
        this.capacity=capacity;
        head=new Node(-1,-1);
        tail=new Node(-1,-1);
        head.next=tail;
        tail.front=head;
        map=new HashMap<>();
    }

    public int get(int key) {
        Node temp=map.get(key);
        if(temp==null){
            return -1;
        }
        else{
            temp.front.next=temp.next;
            temp.next.front=temp.front;

            Node f=tail.front;
            f.next=temp;
            temp.next=tail;
            tail.front=temp;
            temp.front=f;

            return temp.value;
        }
    }

    public void put(int key, int value) {
        Node getNode=map.get(key);
        if(getNode!=null){
            getNode.value=value;
            get(key);
            return;
        }

        if(map.size()==capacity){
            Node delete=head.next;
            Node n=head.next.next;
            head.next=n;
            n.front=head;
            delete.next=null;
            delete.front=null;
            map.remove(delete.key);
        }

        Node temp=new Node(key,value);
        Node f=tail.front;
        f.next=temp;
        temp.next=tail;
        tail.front=temp;
        temp.front=f;
        map.put(key,temp);
    }

    public static void main(String[] args) {
        LRUCache lruCache=new LRUCache(2);
        lruCache.put(1,1);
        lruCache.put(2,2);
        System.out.println(lruCache.get(1));
        lruCache.put(3,3);
        System.out.println(lruCache.get(2));
        System.out.println(lruCache.get(3));
        lruCache.put(4,4);
    }
}

class Answer{
    public List<List<Character>> solve(char[] chars){
        List<List<Character>> res=new ArrayList<>();
        Arrays.sort(chars);
        boolean[] check=new boolean[chars.length];
        f(chars,check,new ArrayList<>(),res);
        return res;
    }

    private void f(char[] chars,boolean[] check,List<Character> cur,List<List<Character>> res){
        if(cur.size()==chars.length){
            res.add(new ArrayList<>(cur));
            return;
        }
        for(int i=0;i<chars.length;i++){
            if(check[i]||(i-1>=0&&chars[i]==chars[i-1]&&!check[i-1])){
                continue;
            }
            else{
                check[i]=true;
                cur.add(chars[i]);
                f(chars,check,cur,res);
                cur.remove(cur.size()-1);
                check[i]=false;
            }
        }
    }
}