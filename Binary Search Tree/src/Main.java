public class Main {
    public static void main(String[] args) {
        //time complexity: O(h) where h is the heigh of the BST
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(new Node(5));
        tree.insert(new Node(1));
        tree.insert(new Node(9));
        tree.insert(new Node(2));
        tree.insert(new Node(7));
        tree.insert(new Node(3));
        tree.insert(new Node(6));
        tree.insert(new Node(4));
        tree.insert(new Node(8));
        tree.display();
        System.out.println();
        System.out.println(tree.search(10));
        tree.remove(1);
        tree.display();
        tree.remove(7);
        System.out.println();
        tree.display();
    }
}

class Node {
    int data;
    Node left, right;

    public Node(int data){
        this.data = data;
    }
}

class BinarySearchTree {
    Node root;
    public void insert(Node node){
        root = insertHelper(root, node);
    }
    private Node insertHelper(Node root, Node node){
        int data = node.data;
        if (root == null){
            root = node;
            return root;
        } else if (data < root.data) {
            root.left = insertHelper(root.left, node);
        /*so if the data is less than the root node, we go to the left*/
        } else {
            root.right = insertHelper(root.right, node);
        }
        return root;
    }
    public void display(){
        displayHelper(root);
    }
    private void displayHelper(Node root){
        //in-order traversal
        if (root != null){
            displayHelper(root.left);
            System.out.print(root.data + " ");
            displayHelper(root.right);
        }
    }
    public boolean search(int data){
        return searchHelper(root, data);
    }
    private boolean searchHelper(Node root, int data){
        if (root == null) {
            return false;
        } else if (root.data == data) {
            return  true;
        } else if (root.data > data) {
            return searchHelper(root.left, data);
        } else {
            return searchHelper(root.right, data);
        }
    }
    public void remove(int data){
        if (search(data)) {
            removeHelper(root, data);
        } else {
            System.out.println(data + " could not be found.");
        }
    }
    public Node removeHelper(Node root, int data){
        if (root == null){
            return root;
        } else if (data < root.data) {
            root.left = removeHelper(root.left, data);
        } else if (data > root.data) {
            root.right = removeHelper(root.right, data);
        } else { // found node
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) { //find a successor to replace this node
                root.data = successor(root);
                root.right = removeHelper(root.right, root.data);
            } else { //find a predecessor to replace this node
                root.data = predecessor(root);
                root.left = removeHelper(root.left, root.data);
            }
        }
        return root;
    }
    private int successor(Node root){ //find the least value bellow the right children
        root = root.right;
        while (root.left != null){
            root = root.left;
        }
        return root.data;
    }
    private int predecessor(Node root){
        root = root.left;
        while (root.right != null){
            root = root.left;
        }
        return root.data;
    }
}