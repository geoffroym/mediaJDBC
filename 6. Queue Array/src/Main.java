public class Main {
    public static void main(String[] args) {
        Queue queue = new Queue(3);

        queue.print();

        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(60);
        queue.enqueue(90);
        queue.enqueue(100);

        queue.print();

        queue.dequeue();
        queue.print();
    }
}

class Queue {
    int front, rear, capacity; //rear is the same as tail
    int array[];

    Queue(int capacity){
        front = rear = 0;
        this.capacity = capacity;
        array = new int[this.capacity];
    }

    void enqueue(int item){
        if (capacity == rear){
            System.out.println("Queue is full.");
        } else {
            array[rear] = item;   //add item as last one in queue
            rear ++;                        //increase the rear
            System.out.println(item + " enqueued to queue.");
        }
    }

    void dequeue(){
        if (front == rear){
            System.out.println("Queue is empty.");
        } else {
            for (int i = 0; i < rear - 1; i++){ //looping until one before rear
                array[i] = array[i + 1];        //add one index to array - shift elements to the left
            }
            if (rear < capacity){               //if the list is not full
                array[rear] = 0;                //turn last one into 0
            }
            rear --;                            //and decrease the rear
            System.out.println("Element dequeued.");
        }
    }

    void print(){
        System.out.println("Bellow follows elements of queue:");
        if (front == rear){
            System.out.println("Queue is empty.");
        } else {
            for (int i = front; i < rear; i++){
                System.out.println(array[i] + " ");
            }
        }
    }
}