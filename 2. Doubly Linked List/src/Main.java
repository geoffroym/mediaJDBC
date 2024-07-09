public class Main {
    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();
        dll.add(2);
        dll.add(45);
        dll.add(5);
        dll.add(3);
        dll.addFirst(0);
        System.out.println("List before removal.");
        dll.print();
        System.out.println();
        dll.remove(dll.head.next);
        System.out.println("List after removal.");
        dll.print();

        System.out.println(dll.head.data);
        System.out.println(dll.tail.data);

    }
}

class Node {
    int data;
    Node next;
    Node prev;

    Node(int data){
        this.data = data;
        this.next = this.prev = null;
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;

    void add(int data){
        Node temp = new Node(data);
        if (head == null){
            head = temp;
            tail = head;
        } else {
            tail.next = temp;   //tail should point to temp
            temp.prev = tail;   //temp should also be pointing back to tail
            tail = temp;        //replace tail as the element
        }
    }

    void addFirst(int data){
        Node temp = new Node(data);
        temp.prev = null;
        temp.next = head;
        if (head!=null){
            head.prev = temp;
            head = temp;
        }
    }

    void print(){
        Node curr = head;
        while (curr!= null){
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
    }

    void remove(Node del){
        if (head == null || del == null){
            return;
        }
        if (head == del){
            head = del.next;
        }
        if (del.next != null){
            del.next.prev = del.prev;
        }
        if (del.prev != null){
            del.prev.next = del.next;
        }
        
        return;  //why???
    }
}