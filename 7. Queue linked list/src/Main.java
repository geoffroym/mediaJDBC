public class Main {
    public static void main(String[] args) {
        //first in first out

        Queue q = new Queue();
        q.enqueue(10);
        q.enqueue(60);
        q.enqueue(90);
        q.enqueue(666);
        q.enqueue(888);
        q.dequeue();
        q.enqueue(900);
        q.dequeue();

        q.display();
    }
}

class Node {
    int data;
    Node next;

    Node(int data){
        this.data = data;
        this.next = null;
    }
}

class Queue {
    Node front, rear;

    Queue(){
        this.front = this.rear = null;
    }

    void enqueue(int data){
        Node temp = new Node(data);
        if (this.front == null){
            this.front = this.rear = temp;
        }
        this.rear.next = temp;
        this.rear = temp;
    }

    void dequeue(){
        if (this.front == null){
            return;
        }

        this.front = this.front.next;
        if (this.front == null){
            rear = null;
        }
    }

    void display(){
        if (this.front == null){
            System.out.println("Queue is empty.");}

        Node temp = this.front;
        System.out.println("Here are all elements: ");
        while (temp != null){
            System.out.println(temp.data + " ");
            temp = temp.next;
        }
    }
}
