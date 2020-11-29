import java.util.HashSet;
import java.util.Stack;

// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the AVLTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    
    private AVLTree getRootSentinel() {
        AVLTree cur = this;
        while (cur.parent != null) {
            cur = cur.parent;
        }
        return cur;
    }


    private int getHeight(AVLTree node)
    {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    
    private int getBalance() 
    {
        return getHeight(this.left) - getHeight(this.right); 
    }

    
    private void updateHeight()
    {
        this.height = 1 + Math.max(getHeight(this.left), getHeight(this.right));
    }

    private AVLTree rightRotate()
    {
        
        AVLTree cur = this, leftChild = cur.left;
        AVLTree parent = cur.parent;

        cur.left = leftChild.right;
        if (cur.left != null) {
            cur.left.parent = cur;
        }
        
        leftChild.right = cur;
        cur.parent = leftChild;
        
        if (parent.left == cur) {
            parent.left = leftChild;
            leftChild.parent = parent;
        } else {
            parent.right = leftChild;
            leftChild.parent = parent;
        }

        leftChild.right.updateHeight();
        leftChild.updateHeight();
        parent.updateHeight();

        return leftChild;
    }

    private AVLTree leftRotate()
    {
        
        AVLTree cur = this, rightChild = cur.right;
        AVLTree parent = cur.parent;

        cur.right = rightChild.left;
        if (cur.right != null) {
            cur.right.parent = cur;
        }
        
        rightChild.left = cur;
        cur.parent = rightChild;
        
        if (parent.left == cur) {
            parent.left = rightChild;
            rightChild.parent = parent;
        } else {
            parent.right = rightChild;
            rightChild.parent = parent;
        }

        rightChild.left.updateHeight();
        rightChild.updateHeight();
        parent.updateHeight();

        return rightChild;
    }

    // Rebalance the tree and update the changed heights
    private void rebalance() 
    {
        AVLTree cur = this;
        while (cur != null) {

            cur.updateHeight();
            if (cur.parent == null) {
                return;
            }

            int balance = cur.getBalance();
            
            if (balance > 1) {
                if (cur.left.getBalance() >= 0) {
                    cur = cur.rightRotate();
                } else {
                    cur.left.leftRotate();
                    cur = cur.rightRotate();
                }
            }
            else if (balance < -1) {
                if (cur.right.getBalance() > 0) {
                    cur.right.rightRotate();
                    cur = cur.leftRotate();
                } else {
                    cur = cur.leftRotate();
                }
            }

            cur = cur.parent;
        }
    }
    
    @Override
    public AVLTree Insert(int address, int size, int key) 
    { 
        /*if (!this.sanity()) {
            System.out.println("Error");
        }*/

        AVLTree insertedNode = null;
        AVLTree cur = this.getRootSentinel();
        if (cur.right == null) {
            cur.right = new AVLTree(address, size, key);
            cur.right.parent = cur;
            insertedNode = cur.right;
        }
        cur = cur.right;
        while (insertedNode == null) {
            
            if (key < cur.key || (key == cur.key && address < cur.address)) {
                if (cur.left == null) {
                    cur.left = new AVLTree(address, size, key);
                    cur.left.parent = cur;
                    insertedNode = cur.left;
                    break;
                } else {
                    cur = cur.left;
                }
            } else {
                if (cur.right == null) {
                    cur.right = new AVLTree(address, size, key);
                    cur.right.parent = cur;
                    insertedNode = cur.right;
                    break;
                } else {
                    cur = cur.right;
                }
            }
        }

        
        insertedNode.rebalance();

        return insertedNode;
    }

    
    private AVLTree getMin() {
        AVLTree cur = this;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }


    private boolean isLeft() {
        return (this.parent.left == this);
    }

    private void assignValues(AVLTree cur) {
        this.address = cur.address;
        this.key = cur.key;
        this.size = cur.size;
    }

    public boolean Delete(Dictionary e)
    {
        /*if (!this.sanity()) {
            System.out.println("Error");
        }*/

        AVLTree cur = this.getRootSentinel().right;
        if (cur == null) {
            return false;
        }
        
        while (cur != null) {

            if (cur.key == e.key && cur.address == e.address) {

                if (cur.left == null && cur.right == null) {
                    if (cur.isLeft()) {
                        cur.parent.left = null;
                    } else {
                        cur.parent.right = null;
                    }
                    cur.parent.rebalance();
                    cur = null; // Making cur eligible for garbage constructor

                    return true;
                }
                else if (cur.right == null) {
                    if (cur.isLeft()) {
                        cur.parent.left = cur.left;
                        cur.left.parent = cur.parent;
                    } else {
                        cur.parent.right = cur.left;
                        cur.left.parent = cur.parent;
                    }
                    cur.parent.rebalance();
                    cur = null; // Making cur eligible for garbage constructor
                    return true;
                }

                else {  // cur.left != null and cur.right != null
                    AVLTree succ = cur.right.getMin();
                    cur.assignValues(succ);
                    succ.assignValues((AVLTree)e);
                    cur = succ;
                }

            }
            else if (e.key < cur.key || (e.key==cur.key && e.address < cur.address)) {
                cur = cur.left;
            }
            else {
                cur = cur.right;
            }
        }
        
        return false;
    }
     
    @Override   
    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree cur = this.getRootSentinel().right;
        AVLTree ans = null;
        
        while (cur != null) {
            if (cur.key == k) {
                if (ans == null) {
                    ans = cur;
                } 
                else if (ans.key > k) {
                    ans = cur;
                }
                else if (ans.address > cur.address) {
                    ans = cur;
                }
                cur = cur.left;
            }
            else if (cur.key < k) {
                cur = cur.right;
            } 
            else {
                if (!exact) {
                    if (ans == null) {
                        ans = cur;
                    } 
                    else if (ans.key > cur.key) {
                        ans = cur;
                    }
                    else if (ans.key == cur.key && ans.address > cur.address) {
                        ans = cur;
                    }
                    cur = cur.left;
                }
            }
        }
       
        return ans;
    }

    @Override
    public AVLTree getFirst()
    { 
        AVLTree cur = this.getRootSentinel().right;

        while (cur != null && cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    @Override
    public AVLTree getNext()
    {
        if (this.parent == null) {
            return null;
        }

        if (this.right != null) {
            return this.right.getMin();
        }

        AVLTree cur = this;
        while (cur.parent != null && !cur.isLeft()) {
            cur = cur.parent;
        }
        
        if (cur.parent != null) {
            return cur.parent;
        }

        return null;
    }

    // If root sentinel exist, then, return root sentinel, otherwise, return null
    private AVLTree rootExist()
    {
        HashSet<AVLTree> h = new HashSet<AVLTree>();

        // Find root sentinel and check if this path from root to "this" is cycle or not
        AVLTree cur = this;
        while (cur.parent != null) {
            if (h.contains(cur)) {
                return null;
            }
            h.add(cur);
            cur = cur.parent;
        }

        return cur;
    }

    // Function to check cycle in subtree rooted at "this"
    private boolean containCycle() 
    {
        HashSet<AVLTree> h = new HashSet<AVLTree>();
        Stack<AVLTree> s = new Stack<AVLTree>();
        
        s.push(this);
        // DFS (preorder traversal)
        while (!s.empty()) {
            AVLTree cur = s.pop();
            if (h.contains(cur)) {
                return true;
            }
            h.add(cur);
            if (cur.right != null) {
                s.push(cur.right);
            }
            if (cur.left != null) {
                s.push(cur.left);
            }
        }
        return false;
    }

    // Function to check BST order property and BST structural property, AVL properties
    private boolean checkBST()
    {
        if (this.height != 1+Math.max(getHeight(this.left), getHeight(this.right))) {
            return false;
        }

        if (Math.abs(this.getBalance()) > 1 && this.parent != null) {
            return false;
        }

        if (this.left != null) {
            if (this.left.parent != this) {
                return false;
            }
            if (this.left.key > this.key || (this.left.key == this.key && this.left.address > this.address)) {
                return false;
            }
            if (!this.left.checkBST()) {
                return false;
            }
        }

        if (this.right != null) {
            if (this.right.parent != this) {
                return false;
            }
            if (this.right.key < this.key || (this.right.key == this.key && this.right.address < this.address)) {
                return false;
            }
            if (!this.right.checkBST()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean sanity()
    { 
        AVLTree root = this.rootExist();

        if (root == null) {
            return false;
        }

        // INV :- root.left == null
        if (root.left != null) {
            return false;
        }

        // INV :- empty tree
        if (root.right == null) {
            return true;
        }

        // INV :- BST does not contains cycle
        if (root.right.containCycle()) {
            return false;
        }

        // INV :- BST follows structure and order property and AVL properties
        // Order property :- keys in left subtree <= root.key < key in right subtree
        // Structural property :- node.left.parent == node and node.right.parent == node

        if (!root.right.checkBST()) {
            return false;
        }

        return true;
    }
}


