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
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the â€œdictionaryâ€� class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)

public int Allocate(int blockSize) {
    	
        if(blockSize <= 0 ) {
            return -1;
        }

    	Dictionary temp = freeBlk.Find(blockSize, false);
    	
    	if(temp!= null && temp.size == blockSize) {
    		
    		allocBlk.Insert(temp.address, temp.size, temp.key);
    		
    		freeBlk.Delete(temp);
    		
    		for(int i = temp.address;i<temp.address+ temp.size;i++) {
    		Memory[i]= 1; // 1 for setting its status to occupied
    		}
    		
    		return temp.address;
    	}
    	
    	else if (temp!= null && temp.size > blockSize){//splitting is required
    		
    		A1List d1 = new A1List(temp.address,blockSize,blockSize);
    		
    		freeBlk.Delete(temp);
    		
    		freeBlk.Insert(temp.address+blockSize,temp.size-blockSize,temp.key-blockSize);
    		
    		allocBlk.Insert(temp.address,blockSize,blockSize);
    		
    		for(int i = temp.address;i<temp.address+blockSize;i++) {
    			Memory[i]= 1; // 1 for setting its status to occupied
    		}
    		
    		return d1.address;
    	}
        return -1;
    } 
    
    public int Free(int startAddr) {
        
        if( startAddr < 0 ) {
            return -1;
        }

    	Dictionary temp = allocBlk;
    	while(temp!=null && temp.address != startAddr) {
    		temp = temp.getNext();
    	}
    	
    	if(temp == null) {
    		return -1;
    	}
    	
    	for(int i = temp.address; i < temp.address+temp.size; i++) {
    		Memory[i] = 0;
    	}
    	
    	freeBlk.Insert(temp.address, temp.size, temp.key);
    	
    	allocBlk.Delete(temp);
    	
    	return 0;
    }
    
}