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
    	/*
          this will insert after the current node
        */
        A1List newNode = new A1List(address, size, key);
        newNode.next = this.next;
        this.next.prev = newNode;
        this.next = newNode;
        newNode.prev = this;
        
        return newNode;
    }

    public boolean Delete(Dictionary d) 
    {
    	// forward search
    	A1List temp = this;
    	while(temp != null ) {
    		if(temp.key == d.key && temp.address == d.address && temp.size == d.size) {

        		temp.prev.next = temp.next;
        		temp.next.prev = temp.prev;
        		return true;
        	}
    		
    		temp = temp.next;
    	}
    	
    	// backward search 
    	A1List tempp = this;
    	while(tempp != null ) {
    		if(tempp.key == d.key && tempp.address == d.address && tempp.size == d.size) {

        		tempp.prev.next = tempp.next;
        		tempp.next.prev = tempp.prev;
        		
        		return true;
        	}
    		
    		tempp = tempp.prev;
    	}
    	
        return false;
    }
    
    public A1List Find(int k, boolean exact)
    {
    	if(exact == true) {
    		
    		A1List temp = this.getFirst();
    		
        	while(temp != null ) {
        		if(temp.key == k) {
            		return temp;
            	}
        		
        		temp = temp.next;
        	}
        	
    	}
    	
    	if(exact == false) {
    		
    		A1List temp = this.getFirst();
    		
        	while(temp != null ) {
        		if(temp.key >= k) {
            		return temp;
            	}
        		temp = temp.next;
        	}
        	
    	}
    	
        return null;
    }

    public A1List getFirst()
    {
    	A1List temp = this;
    	while(temp.prev != null) {
    		temp = temp.prev;
    	}
    	// if the list is empty temp.next == tailSentinel
    	if(temp.next.address == -1 && temp.next.size == -1 && temp.next.key == -1) {
    		return null;
    	}
    	//temp will now be the sentinel node so return the next of it
    	return temp.next;
    }
    
    public A1List getNext() 
    {
    	if(this.next.address == -1 && this.next.size == -1 && this.next.key == -1) {
    		return null;
    		// when the list is empty or next element is tail sentinel node then return null
    	}
    	return this.next;
    }
    
    public boolean sanity()
    {
    	//case1 cycle in the DLL
    	if(Loop(this)){
    		return false;
    	}
    	
    	if(this.next != null ){
    		if(this.next.prev != this) {
        		return false;
        	}
    	}
    	if(this.prev != null)  {
        	if(this.prev.next != this) {
        		return false;
        	}
    	}

        //if this is head  sentinel node
        if(this.next !=null &&  (this.key ==-1 || this.address == -1 || this.size == -1 ) ) {
            /*
             * prev of head sentinel node must be null
             */
            if( this.prev != null ) {
                return false;
            }
        }
        
        // if this is tail sentinel node
        
        if(this.prev != null && (this.key ==-1 || this.address == -1 || this.size == -1 ) ) {
            
            if( this.next != null) {
                return false;
            }
            
        }
        
    	/*
    	 * Can also do count of the number of elements, but since we don't store number of 
    	 * elements in the Dictionary so we cannot compare, but if we know the exact
    	 * number of elements in the Dictionary then we can also check sanity by that also.
    	 */
    	
        return true;
    }
    
    private boolean Loop(A1List head) {
    	if(head==null){
            return false;
        }
        A1List slow= head;
        A1List fast = head.next;
        while(fast!=null && fast.next!=null){
            if(fast==slow){
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

}


