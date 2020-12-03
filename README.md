# Dynamic-Memory-Allocator
## COL106 assignment
A Dynamic memory allocator using Double Linked List, Binary Search Trees, AVL trees.


### How to run
* Install make command 
* run.sh {input_file} {output file}

### What is a dynamic memory allocator?
It is a system to allocate memories to programs as requested by them (Just like Malloc in c++). 

### How does dynamic memory allocator work?
Assume the memory of the system to be an array of size M (bytes). Each element of this array represents a memory address of size 1 byte. 
The Memory Allocation System will be maintaining two data structures, one for the free blocks and one for the allocated blocks. So initially, the data structure for allocated blocks will be empty and the data structure for the free blocks will be having just one element which is the entire memory.
Here, the system will be allocating memory using a variant of First Fit and Best Fit algorithm. These variants will be called First Split Fit and Best Split Fit algorithm. Here during the allocation, these algorithms will split the found free block into two segments; one block of size k (that is requested by the program) and the other block of the remaining size. For example, if the First Fit algorithm had returned a memory block of the range 5KB → 10KB for a request of 2KB, then the semantics of the First Split Fit algorithm will be to divide that 5KB block into two segments: 5KB → 7KB and 7KB → 10KB. Now, the first segment will be returned to the program that requested for memory (and marked as occupied) and other one shall still remain free. Thus you can see that we are creating holes in our free memory (thus fragmenting it) during this Split step. Thus the First Split Fit algorithm is just having this Split step on top of the First Fit algorithm before returning the block to the requesting program (and the same for Best Split Fit algorithm).
The main functionality of the Memory Allocator will be To allocate free memory and To free allocated memory. In order to solve the issue of Fragmentation, the system will also Defragment the free memory. It basically searches for consecutive free blocks and merges them into one bigger block.

NOTE - Above points were taken from assignment statement.

### Why do we need different data structures?
The time taken by allocation, free and defragment operation is greatly affected by the type of data structure we use. Though inserting blocks in DLL is an O(1) operation but deleting blocks will take O(n) time. Efficient implementation of Defragment will require sorting the DLL. BSTs allows to store blocks in sorted order and hence, we can implement best fit startegy but insertion, deletion in BSTs take O(n) time in worst case. AVL Trees provide all the benefits of BSTs and also allows O(log(n)) insertion and deletion. Clearly, a system having large memory will require AVL Tree implementation for efficient memory allocation.
