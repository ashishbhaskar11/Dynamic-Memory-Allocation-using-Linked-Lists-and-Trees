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
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() 
    {
    	Dictionary tree;
    	if(type==2) {
    		tree = new BSTree();
    	}
    	else {
    		tree = new AVLTree();
    	}
    	
//    	Dictionary tree = new BSTree();
    	int count =0;
        Dictionary t = this.freeBlk.getFirst();
    	for(Dictionary d =t ; d != null ; d = d.getNext()) {
    		tree.Insert(d.address, d.size, d.address);
    		count++;
    	}
    	if(count<=1) {
    		return;
    	}
    	Dictionary first = tree.getFirst();
    	Dictionary next = tree.getFirst().getNext();
    	
    	while(first != null && next != null) {
    		if(( first.address + first.size ) == next.address ) {
    			// merge them
    			Dictionary n1,n2,merged;
    	    	if(type==2) {
    	    		n1 = new BSTree(first.address,first.size,first.size);
    	    		n2 = new BSTree(next.address,next.size,next.size);
    	    		merged = new BSTree(first.address, first.size+next.size, first.size+next.size);
    	    	}
    	    	else {
    	    		n1 = new AVLTree(first.address,first.size,first.size);
    	    		n2 = new AVLTree(next.address,next.size,next.size);
    	    		merged = new AVLTree(first.address, first.size+next.size, first.size+next.size);
    	    	}
    			//Dictionary n1 = new BSTree(first.address,first.size,first.size);
    			//Dictionary n2 = new BSTree(next.address,next.size,next.size);
    			this.freeBlk.Delete(n1);
    			this.freeBlk.Delete(n2);
//    			Dictionary merged = new BSTree(first.address, first.size+next.size, first.size+next.size);
    			this.freeBlk.Insert(merged.address, merged.size, merged.key);
    			
    			first = merged;
    			next = next.getNext();
    		}
    		else {
    			first = next;
    			next = first.getNext();
    		}
    	}
    	tree = first = next = null;
        return;
    }
    
    @Override
    public int Allocate(int blockSize) {
        if(blockSize <= 0 ) {
            return -1;
        }
    	Dictionary temp = freeBlk.Find(blockSize, false);
    	if(temp!= null && temp.size == blockSize) {
    		int ans = temp.address;
    		allocBlk.Insert(temp.address, temp.size, temp.key);
    		freeBlk.Delete(temp);
    		return ans;
    	}
    	else if (temp!= null && temp.size > blockSize){//splitting is required	
    		int anss = temp.address;
    		freeBlk.Delete(temp);
    		freeBlk.Insert(temp.address+blockSize,temp.size-blockSize,temp.key-blockSize);
    		allocBlk.Insert(temp.address,blockSize,blockSize);
    		return anss;
    	}
        return -1;
    } 
}




