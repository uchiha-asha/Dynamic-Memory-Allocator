// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys 
    //in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address
    // since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, 
    //use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside 
    //the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {

        // Create new dictionary with address as key
        Dictionary new_freeBlk;
        if (this.type == 2) {
            new_freeBlk = new BSTree();
        } 
        else if (this.type == 3) {
            new_freeBlk = new AVLTree();
        } 
        else {
            return;
        }

        for (Dictionary d = this.freeBlk.getFirst(); d != null; d = d.getNext()) {
            new_freeBlk.Insert(d.address, d.size, d.address);
        }

        // Defragmentation
        Dictionary cur = new_freeBlk.getFirst();

        while (cur != null) {

            Dictionary cur_next = cur.getNext();
            if (cur_next == null) {
                break;
            }
            if (cur.address+cur.size == cur_next.address) {
                // Remove contiguous blocks from this.freeBlk
                this.freeBlk.Delete(cur);
                this.freeBlk.Delete(cur_next);

                // Merge contiguous blocks
                cur.size += cur_next.size;

                // Insert merged block to this.freeBlk
                this.freeBlk.Insert(cur.address, cur.size, cur.size);

                // cur_next is now merged into cur, so, show it the gates to hell :-D
                new_freeBlk.Delete(cur_next);
            }
            else {
                cur = cur.getNext();
            }
        }
        // Delete new dictionary
        new_freeBlk = null;

        return ;
    }
}