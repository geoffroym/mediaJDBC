public class LinkedList {
    node head;
    static class node {
        int data;
        node next;
        public node(int data){
            this.data = data;
        }
    }
    node sortedMerge(node a, node b){
        node result;
        if (a == null) return b;
        if (b == null) return a;

        if (a.data <= b.data){
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }

    node mergeSort(node h){
        if (h == null || h.next == null) return h;

        node middle = getMiddle(h);
        node nextofmiddle = middle.next;

        middle.next = null;
        node left = mergeSort(h);
        node right = mergeSort(nextofmiddle);

        node sortedList = sortedMerge(left, right);
        return sortedList;
    }

    node getMiddle(node head) {
        if (head == null) return head;
        node fast = head, slow = head;

        while (fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    void push(int data){
        node newNode = new node(data);
        if(head != null) {
            newNode.next = head;
        }
        head = newNode;
    }

    void printList(){
        node curr = this.head;
        while (curr != null){
            System.out.println(curr.data + " ");
            curr = curr.next;
        }
    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ll.push(17);
        ll.push(8);
        ll.push(12);
        ll.push(13);
        ll.push(1);
        ll.push(20);
        System.out.println("LinkedList with sorting:");
        ll.printList();

        ll.head = ll.mergeSort(ll.head);
        System.out.println("sorted linked list:");
        ll.printList();
    }
}



