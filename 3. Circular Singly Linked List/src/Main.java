public class Main {
    public static void main(String[] args) {
        CircularSingly cs = new CircularSingly();
        cs.addFirst(10);
        cs.addLast(20);
        cs.addLast(30);

        cs.print();
        cs.searchNode(20);
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

class CircularSingly {
    Node head;
    Node tail;

    CircularSingly(){
        this.head = null;
        this.tail = null;
    }

    void print(){
        Node curr = this.head;
        if (head != null){
            do {
                System.out.println(curr.data);
                curr = curr.next;
            } while (curr != head);
        }
    }

    void addFirst(int data){
        Node curr = new Node(data);
        if (head == null){
            //if list is empty, both tail and head will point to the current node.
            head = curr;
            tail = curr;
            curr.next = head;
        } else {
            Node temp = head;   //point the temporary node to the head
            curr.next = temp;   //point the next node after the current to the temp
            head = curr;        //make current as head
            tail.next = head;   //since it's circular, tail should point to head
        }
    }

    void addLast(int data){
        Node curr = new Node(data);
        if (head == null){
            head = curr;
            tail = curr;
            curr.next = head;
        } else {
            tail.next = curr;
            tail = curr;
            tail.next = head;
        }
    }

    void deleteFirst(){
        if (head == null){
            return;                 //if list is empty, do nothing
        } else {
            if (head != tail){
                head = head.next;
                tail.next = head;
            } else {                //if the list contains only one element
                head = tail = null; //then it will remove it and both head and tail will point to null
            }
        }
    }

    void searchNode(int data){
        Node curr = head;
        int count = 0;
        boolean check = false;
        if (head == null){
            System.out.println("List is empty.");
        } else {
            do {
                if (curr.data == data){
                    check = true;
                    break;
                }
                curr = curr.next;
                count++;
            } while (curr != head);
            if (check){
                System.out.println("Element is present in the linked list at index " + count);
            } else {
                System.out.println("Element is not present in the list");
            }
        }
    }
}