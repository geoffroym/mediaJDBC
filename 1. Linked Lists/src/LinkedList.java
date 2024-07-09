public  class LinkedList {
    Node head; //create first node

    void printLinkedList() {
        Node curr = this.head;              //make current node the first one

        while (curr != null){               //the last node is null, so while it's not the last node:
            System.out.println(curr.data); //keep printing the element
            curr = curr.next;              //keep making next one be the next one
        }
    }

    void add(int data){                    //method to add element to the linked list
        Node temp = new Node(data);        //create new node
        if (head == null){              //if the first element is null, means the linked list is empty
            head = temp;                //do make the element the first node
        } else {
            Node curr = head;           //else, make current be the head
            while (curr.next != null){  //and while the next one is not the last one
                curr = curr.next;       //make the current the next in a loop
            }
            curr.next = temp;           //at last, make the next element after the current, the node you want to add.
        }
    }

    void addFirst(int data){
        Node temp = new Node(data);
        if(head == null){
            head = temp;
        } else {
            temp.next = head;
            head = temp;
            /*if (head != null)
             * temp.next = head;
             * }
             * head = temp;*/
        }
    }

    void  add(int index, int data){      //method to add element anywhere in the list, giving index and element
        if(index == 0){                     //if index is 0, then just call addFirst method
            addFirst(data);
        } else {
            Node temp = new Node(data);  //else, create new node
            int count = 0;                  //create a counter to keep track of the placement of elements
            Node curr = this.head;          //make current element the first one
            while (count < index - 1){      //traverse until the last index before the index you want to place the new element
                curr = curr.next;
                count ++;
            }
            temp.next = curr.next;          //make the temp element point to the same element as the current one
            curr.next = temp;               //and replace the current with the temp
        }
    }
}
