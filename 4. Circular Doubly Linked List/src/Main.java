public class Main {
    public static void main(String[] args) {
        CircularDoubly cd = new CircularDoubly();

        cd.push_front(10);
        cd.push_front(15);
        cd.push_front(20);
        cd.push_front(35);

        cd.printList();
    }
}

class Node {
    int data;
    Node prev;
    Node next;
}

class CircularDoubly {
    Node head;

    CircularDoubly(){
        head = null;
    }

    void push_front(int data){
        Node newNode = new Node();  //create new node
        newNode.data = data;           //make new node have the given data
        newNode.next = newNode.prev = null;        //set prev and next to null
        if (head == null){          //if list is empty
            head = newNode;         //everything will be the head
            newNode.next = head;
            newNode.prev = head;
        } else {
            Node curr;     //else, create a temporary node
            curr = head;                //put it as the head
            while (curr.next != head){  //traverse it until it reached the head again
                curr = curr.next;
            }
            curr.next = newNode;        //since it's circular, the temp node will have the next one as the head
            //so we put the new node pointing to the head too
            newNode.prev = curr;        //we replace the temp with the new node
            newNode.next = head;        //so prev will be temp and next will be head
            head.prev = newNode;
            head = newNode;             //and make it as the head
        }
    }

    void printList(){
        Node temp;
        temp = this.head;
        if (temp != null){
            System.out.println("Bellow follows the list:");
            while (true){
                System.out.println(temp.data + " ");
                temp = temp.next;
                if(temp == this.head){
                    break;
                }
            }
        } else {
            System.out.println("The list is empty.");
        }
    }
}
