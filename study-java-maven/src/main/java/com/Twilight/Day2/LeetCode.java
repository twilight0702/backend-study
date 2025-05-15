package com.Twilight.Day2;

import org.w3c.dom.Node;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.max;

public class LeetCode {

}

class WaitNotifyExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 生产者线程
        Thread producer = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Producer: Producing...");
                    Thread.sleep(2000);
                    System.out.println("Producer: Production finished. Notifying consumer.");
                    // 唤醒等待的线程
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Consumer: Waiting for production to finish.");
                    // 进入等待状态
                    lock.wait();
                    System.out.println("Consumer: Production finished. Consuming...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.start();
        producer.start();
    }
}