package bgu.spl.mics;

import java.util.*;

public class Queue {

    public static int[] Queue;
    public static int len, front, rear, size;

    public Queue(int n) {
        len = 0;
        size = n;
        front = -1;
        rear = -1;
        Queue = new int[size];
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public boolean isFull() {
        return (front == 0 && rear == size - 1);
    }

    public int getsize() {
        return len;
    }

    public void insert(int i) {
        if (rear == -1) {
            rear = front = 0;
            Queue[rear] = i;
        } else if (rear + 1 >= size) {
            System.out.println("OverFlow Queue");
        } else if (rear + 1 < size) {
            Queue[++rear] = i;
        }
        len++;
    }

    public int remove() {
        int x = -99;
        if (front != -1) {
            x = Queue[front];
            if (front == rear) {
                front = rear = -1;
            } else if (front < rear) {
                front += 1;
            }
            len--;
        }
        return x;
    }

    /*  Function to check the front element of the queue */
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Underflow Exception");
        }
        return Queue[front];
    }

    public void display() {
        System.out.print("\nQueue = ");
        if (len == 0) {
            System.out.print("Empty\n");
            return;
        }
        for (int i = front; i <= rear; i++) {
            System.out.print(Queue[i] + " ");
        }
        System.out.println();
    }

}