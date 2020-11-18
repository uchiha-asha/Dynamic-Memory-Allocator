// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        if (!this.sanity()) {
            System.out.println("Error");
        }
        //  INV :- this is not tail sentinel
        if (this.next == null) {
            return null;
        }

        A1List oldNextNode = this.next;
        A1List newNextNode = new A1List(address, size, key);
        /*
            LIST TRANSFORMATION :- this->oldNextNode to this->newNextNode->oldNextNode
        */

        this.next = newNextNode;
        newNextNode.next = oldNextNode;
        newNextNode.prev = this;
        oldNextNode.prev = newNextNode;
        if (!this.sanity()) {
            System.out.println("Error");
        }
        return newNextNode;
    }

    public boolean Delete(Dictionary d) 
    {
        if (!this.sanity()) {
            System.out.println("Error");
        }
        A1List cur = this, toBeDeleted = null;

        // Forward Search

        for (; cur != null; cur = cur.getNext()) {
            if (cur.key == d.key && cur.address == d.address) {
                toBeDeleted = cur;
                break;
            }
        }

        // Backward Search

        if (toBeDeleted != null) {
            for (cur = this; cur != null && cur.prev != null; cur = cur.prev) {
                if (cur.key == d.key && cur.address == d.address) {
                    toBeDeleted = cur;
                    break;
                }
            }
        } 

        if (toBeDeleted != null) {

            // LIST TRANSFORMATION :- prev -> toBeDeleted -> next to prev -> next

            toBeDeleted.prev.next = toBeDeleted.next;
            toBeDeleted.next.prev = toBeDeleted.prev;

            
            if (!this.sanity()) {
                System.out.println("Error");
            }
            return true;
        }

        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
        for (A1List cur = this.getFirst(); cur != null; cur = cur.getNext()) {
            if (cur.key == k) {
                return cur;
            }
            else if (cur.key > k && !exact) {
                return cur;
            }
        }
        return null;
    }

    public A1List getFirst()
    {   
        // this is head sentinel
        if (this.prev == null) {
            // this.next is tail sentinel
            if (this.next.next == null) {
                return null;
            } else {
                return this.next;
            }
        }
        // this is not head sentinel
        for (A1List cur = this; cur.prev != null; cur = cur.prev) {
            if (cur.prev.prev == null) {
                return cur;
            }
        }
        return null;
    }
    
    public A1List getNext() 
    {
        // INV :- this != tailSentinel and this.next != tailSentinel
        if (this.next == null || this.next.next == null) {
            return null;
        }

        return this.next;
    }

    public boolean sanity()
    {
        // INV :- None of the node of list (including sentinels) have both next and prev pointers to be null

        if (this.prev == null && this.next == null) {
            return false;
        }

        A1List head = this.getFirst();

        // EMPTY LIST

        if (head == null) {
            // this is head sentinel and this.next is not tail sentinel
            if (this.next != null && this.next.next != null) {
                return false;
            }
            // this is tail sentinel and this.prev is not head sentinel
            if (this.prev != null && this.prev.prev != null) {
                return false;
            }
            return true;
        }

        // INV :- head sentinel is not null and head.prev == headSentinel and head.next != null
        if (head.prev == null || head.prev.prev != null || head.next == null) {
            return false;
        }

        // INV :- node.next.prev == node 
        // Above invariant will fail if list contain cycle as a node cannot have two prev or two next pointers


        A1List node = head;

        while (node.next != null) { // INV :- node != tailSentinel
            if (node.next.prev != node) {
                return false;
            }
            node = node.next;
        }

        if (node.prev.next != node) { // node is tail Sentinel
            return false;
        }


        return true;
    }

}


