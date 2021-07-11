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

    public void Defragment() {
        if (this == null){
            return;
        }
        Dictionary newTree;
        if (this.type == 2) {
            newTree = new BSTree();
        }
        else{
            newTree = new AVLTree(); 
        }
        
        Dictionary temp = this.freeBlk.getFirst();                                  //adding nodes
        while (temp != null) {
            newTree.Insert(temp.address, temp.size, temp.address);
            temp = temp.getNext();
        }

        Dictionary prev = newTree.getFirst();
        Dictionary curr = null;
        if (prev != null) {
            curr = prev.getNext();
        }

        Dictionary prev_, curr_;
        while (curr != null) {
            if (prev.address + prev.size == curr.address) {                         //checking contiguous
                int address, size;
                address = prev.address;                                             //merging small blocks
                size = prev.size + curr.size;

                if (this.type == 2){
                    prev_ = new BSTree(prev.address, prev.size, prev.size);                
                    curr_ = new BSTree(curr.address, curr.size, curr.size);
                }
                else{
                    prev_ = new AVLTree(prev.address, prev.size, prev.size);
                    curr_ = new AVLTree(curr.address, curr.size, curr.size);
                }                                                                          
                this.freeBlk.Delete(prev_);                                         //deleting small blocks
                this.freeBlk.Delete(curr_);
                newTree.Delete(prev);
                newTree.Delete(curr);

                this.freeBlk.Insert(address, size, size);                           //Inserting merged blocks
                prev = newTree.Insert(address, size, address);
                curr = prev.getNext();
            }
            else{
                prev = curr;
                curr = curr.getNext();
            }
        }
        return ;  
    }
}