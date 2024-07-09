public class Main {
    public static void main(String[] args) {
        SearchTree st = new SearchTree();
        Node root = new Node(100);
        root.left = new Node(20);
        root.right = new Node(200);
        root.left.left = new Node(10);
        root.left.right = new Node(30);
        root.right.left = new Node(150);
        root.right.right = new Node(300);

        System.out.println("In-order traversal:");
        st.printInorder(root);
        System.out.println();

        System.out.println("Pre-order traversal:");
        st.printPreorder(root);
        System.out.println();

        System.out.println("Post-order traversal");
        st.printPostorder(root);

    }
}

class Node {
    int data;
    Node left;
    Node right;

    Node(int v){
        this.data = v;
        this.left = this.right = null;
    }
}

class SearchTree {
    public void printInorder (Node node){
        if (node == null){
            return;
        }

        printInorder(node.left);
        System.out.print(node.data + " ");
        printInorder(node.right);
    }

    public void printPreorder(Node node){
        if (node == null){
            return;
        }

        System.out.print(node.data + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }

    void printPostorder(Node node){
        {
            if (node == null)
                return;

            printPostorder(node.left);
            printPostorder(node.right);
            System.out.print(node.data + " ");
        }
    }
}
