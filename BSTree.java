// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

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

    public boolean sanity()
    { 
        return false;
    }

}


 


