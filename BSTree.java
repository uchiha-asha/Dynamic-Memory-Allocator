// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

import java.util.HashSet;
import java.util.Stack;

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree getRootSentinel() {
        BSTree cur = this;
        while (cur.parent != null) {
            cur = cur.parent;
        }
        return cur;
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree cur = this.getRootSentinel();
        if (cur.right == null) {
            cur.right = new BSTree(address, size, key);
            cur.right.parent = cur;
            return cur.right;
        }
        cur = cur.right;
        while (true) {
            if (key <= cur.key) {
                if (cur.left == null) {
                    cur.left = new BSTree(address, size, key);
                    cur.left.parent = cur;
                    return cur.left;
                } else {
                    cur = cur.left;
                }
            } else {
                if (cur.right == null) {
                    cur.right = new BSTree(address, size, key);
                    cur.right.parent = cur;
                    return cur.right;
                } else {
                    cur = cur.right;
                }
            }
        }

        
    }

    private BSTree getMin() {
        BSTree cur = this;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    private void assignValues(BSTree cur) {
        this.address = cur.address;
        this.key = cur.key;
        this.size = cur.size;
    }

    private boolean isLeft() {
        return (this.parent.left == this);
    }

    public boolean Delete(Dictionary e)
    { 
        BSTree cur = this.getRootSentinel().right;
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
                    cur = null; // Making cur eligible for garbage constructor
                    return true;
                }
                else {  // cur.left != null and cur.right != null
                    BSTree succ = cur.right.getMin();
                    cur.assignValues(succ);
                    succ.assignValues((BSTree)e);
                    cur = succ;
                }

            }
            else if (e.key <= cur.key) {
                cur = cur.left;
            }
            else {
                cur = cur.right;
            }
        }

        return false;
    }
        
    public BSTree Find(int key, boolean exact)
    { 
        BSTree cur = this.getRootSentinel().right;
        BSTree ans = null;

        while (cur != null) {
            if (cur.key == key) {
                return cur;
            }
            else if (cur.key < key) {
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
                    cur = cur.left;
                }
            }
        }
        if (!exact) {
            return ans;
        }
        return null;
    }

    public BSTree getFirst()
    { 
        BSTree cur = this.getRootSentinel().right;

        while (cur != null && cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public BSTree getNext()
    { 
        if (this.parent == null) {
            return this.right;
        }

        if (this.right != null) {
            return this.right.getMin();
        }

        if (this.isLeft()) {
            return this.parent;
        }

        return null;
    }

    // If root sentinel exist, then, return root sentinel, otherwise, return null
    private BSTree rootExist()
    {
        HashSet<BSTree> h = new HashSet<BSTree>();

        // Find root sentinel and check if this path from root to "this" is cycle or not
        BSTree cur = this;
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
        HashSet<BSTree> h = new HashSet<BSTree>();
        Stack<BSTree> s = new Stack<BSTree>();
        
        // DFS (preorder traversal)
        while (!s.empty()) {
            BSTree cur = s.pop();
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

    // Function to check BST order property and BST structural property
    private boolean checkBST()
    {
        if (this.left != null) {
            if (this.left.parent != this) {
                return false;
            }
            if (this.left.key > this.key) {
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
            if (this.right.key <= this.key) {
                return false;
            }
            if (!this.right.checkBST()) {
                return false;
            }
        }

        return true;
    }

    public boolean sanity()
    { 
        BSTree root = this.rootExist();

        if (root == null) {
            return false;
        }

        // INV :- root.left == null
        if (root.left != null) {
            return false;
        }

        // INV :- BST does not contains cycle
        if (root.containCycle()) {
            return false;
        }

        // INV :- BST follows structure and order property
        // Order property :- keys in left subtree <= root.key < key in right subtree
        // Structural property :- node.left.parent == node and node.right.parent == node

        if (!root.checkBST()) {
            return false;
        }

        return true;
    }

}


 


