// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if (blockSize <= 0) {
            return -1;
        }
        
        Dictionary memoryBlk = freeBlk.Find(blockSize, false);
        
        if (memoryBlk != null) {
            int address = memoryBlk.address, size = memoryBlk.size;

            // freeBlk and allockBlk should have memory blocks with size > 0
            
            allocBlk.Insert(address, blockSize, blockSize);
            
            if (size-blockSize > 0) {
                freeBlk.Insert(address+blockSize, size - blockSize, size - blockSize);
            }
            
            freeBlk.Delete(memoryBlk);
        
            return address;
        }
        return -1;
    } 
    
    public int Free(int startAddr) {
        if (startAddr < 0) {
            return -1;
        }
        
        Dictionary memoryBlk;

        for (memoryBlk = allocBlk.getFirst(); memoryBlk != null; memoryBlk = memoryBlk.getNext()) {
            if (memoryBlk.address == startAddr) {
                break;
            }
        }

        if (memoryBlk != null) {
            freeBlk.Insert(memoryBlk.address, memoryBlk.size, memoryBlk.size);
            allocBlk.Delete(memoryBlk);
            
            return 0;
        }

        return -1;
    }
}