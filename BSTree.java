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

    public BSTree Insert(int address, int size, int key) 
    {
    	BSTree newNode = new BSTree(address, size, key);
    	// first move to the root node
    	BSTree root = this;
    	while(root.parent != null) {
    		root = root.parent;
    	}
    	//Now root contains sentinel node
    	//If tree is empty --> right of sentinel is empty
    	if(root.right == null) {
    		root.right = newNode;
    		newNode.parent = root;
    		return newNode;
    	}
    	root = root.right; 
    	// now root is the root of the tree
    	BSTree temp = null;
    	
    	while (root != null) {
            temp = root;
            if (key > root.key) {
                root = root.right;
            } 
            else if( key < root.key){
                root = root.left;
            }
            // root.key == key
            else{
            	while(root != null && root.key == key ) {
            		temp = root;
            		if(address <= root.address) {
            			root = root.left;
            		}
            		else {
            			root = root.right;
            		}
            	}
            	if(root == null) {
            		if(temp.left == null && temp.right == null) {
            			if(address < temp.address) {
            				temp.left = newNode;
            				newNode.parent = temp;
            				return newNode;
            			}
            			else {
            				temp.right = newNode;
            				newNode.parent = temp;
            				return newNode;
            			}
            		}
            		else if ( temp.left == null) {
            			temp.left = newNode;
            			newNode.parent = temp;
            			return newNode;
            		}
            		else {//temp.right == null
            			temp.right = newNode;
            			newNode.parent = temp;
            			return newNode;
            		}
            	}
            	//root != null -> we have to insert newNode inbetween temp and root;
            	if(temp.right == root) {
            		temp.right = newNode;
            		newNode.parent = temp;
            		newNode.right = root;
            		root.parent = newNode;
            		return newNode;
            	}
            	if(temp.left == root ) {
            		temp.left = newNode;
            		newNode.parent = temp;
            		newNode.left = root;
            		root.parent = newNode;
            		return newNode;
            	}
            }
        }
    	//now temp contains the right place next to which newNode should be inserted
    	if(key>temp.key) {
    		temp.right = newNode;
    		newNode.parent = temp;
    		return newNode;
    	}
    	else {
    		temp.left = newNode;
    		newNode.parent = temp;
    		return newNode;
    	}
    }

    public boolean Delete(Dictionary e)
    {
    	// Searches for the e.key in the subtree--- can be called on any node so first go to root
    	// Deletes the element it is found in the subtree and returns true.
    	Dictionary t =e;
    	if(t==null) {
    		return false;
    	}
    	
    	BSTree temp = this;
    	
    	while(temp.parent != null) {
    		temp = temp.parent;
    	}
    	//Now temp contains sentinel node
    	//If tree is empty --> right of sentinel is empty
    	
    	if(temp.right == null) {
    		return false;
    	}
    	
    	temp = temp.right;
    	
    	while(temp != null){
    		if(temp.key == t.key ) {
    			while(temp!=null && temp.key == t.key && temp.address != t.address) {
    				if(t.address > temp.address) {
    					temp = temp.right;
    				}
    				else {
    					temp = temp.left;
    				}
    			}
    			if(temp!=null &&  temp.address == t.address && temp.size == t.size){
    				// Case 1 temp has two children --> successor will not be null i.e. successor exist
        			if(temp.left !=null && temp.right != null) {
        				BSTree successor = temp.getNext();
        				temp.address = successor.address;
        				temp.size = successor.size;
        				temp.key = successor.key;
        				temp = successor;
        			}
        			// Case 2 temp is a leaf node
        			if(temp.left == null && temp.right ==null) {
        				//temp is the left child of its parent 
        				if(temp.parent.left==temp) {
            				temp.parent.left = null;
            				temp =null;
            				return true;
            			}
        				else {
        					temp.parent.right = null;
        					temp = null;
        					return true;
        				}
        			}
        			//Case 3 temp has only one child
        			if(temp.left == null || temp.right == null) {
        				if(temp.right == null) {
        					//if temp is right child of its parent 
        					if( temp.parent.right == temp) {
        						temp.parent.right = temp.left;
        						temp.left.parent = temp.parent;
        						temp = null;
        						return true;
        					}
        					//temp is left child of its parent 
        					else {
        						temp.parent.left = temp.left;
        						temp.left.parent = temp.parent;
        						temp = null;
        						return true;
        					}
        				}
        				// temp.left == null;
        				else {
        					//if temp is right child of its parent 
        					if(temp.parent.right == temp) {
        						temp.parent.right = temp.right;
        						temp.right.parent = temp.parent;
        						temp = null;
        						return true;
        					}
        					//temp is left child of its parent 
        					else {
        						temp.parent.left = temp.right;
        						temp.right.parent = temp.parent;
        						temp = null;
        						return true;
        					}
        				}
        			}
            	}
        	}
    		else if(t.key > temp.key) {
    			temp = temp.right;
    		}
    		else {
    			temp = temp.left;
    		}
    	}
    	return false;
    }
    			
        
    public BSTree Find(int key, boolean exact)
    {
    	BSTree temp = this;
    	while(temp.parent != null) {
    		temp = temp.parent;
    	}
    	//Now temp contains sentinel node
    	
    	// tree is empty
    	if(temp.right == null) {
    		return null;
    	}
    	temp = temp.right;
    	//temp  is now the root of the tree
    	if(exact == true) {
    		while(temp != null ) {
    			if(temp.key == key) {
    				//there are no other element with (key,address) smaller than temp
    				if(temp.left == null ) {
    					return temp;
    				}
    				else if(temp.left.key == key ) {
    					temp = temp.left;
    				}
    				else {
    					return temp;
    				}
        		}
    			else if(key > temp.key ) {
    				temp = temp.right;
    			}
    			else {
    				temp = temp.left;
    			}
    		}
    		return null;
    	}
    	
    	else if(exact == false) {
    		// returns the element with SMALLEST key such that key >= k in the subtree. Returns null in case no such element found.
    		// let's store the last found key in tempfound
    		BSTree tempfound = null;
    		while(temp != null || tempfound != null) {
    			if(tempfound != null && tempfound.key >= key) {
    				if(tempfound.left == null && tempfound.right == null) {
    					return tempfound;
    				}
    				else if(tempfound.left != null && tempfound.left.key >= key ) {
    					tempfound = tempfound.left;
    					temp = tempfound.left;
    				}
    				else {
            			return tempfound;
    				}
        		}
    			else if(temp.key < key) {
    				temp = temp.right;
    			}else {
    				tempfound = temp;
        			temp = temp.left;
    			}
    		}
    		return null;
    	}
        return null;
    }

    public BSTree getFirst()
    {
    	// The getFirst() returns the first (smallest) element of the BST subtree and null if the subtree is empty
    	BSTree subtree = this;
    	//if called on sentinel node then temp is sentinel node;
    	if(subtree.parent == null ) {
    		subtree = subtree.right;
    		//now subtree is root
    	}
    	//if called on empty tree
    	if(subtree == null ) {
    		return null;
    	}
    	
    	//if the left subtree of "subtree" is empty and also if subtree is leaf--> return the leaf node itself
    	if(subtree.left == null ) {
    		return subtree;
    	}
    	
    	// Case -1 If left subtree if not null, then its smallest element will be the leftmost child of the left-subtree
    	
    	subtree = subtree.left;
    	while(subtree.left != null) {
    		subtree = subtree.left;
    	}
    	return subtree;
    }
    

    public BSTree getNext()
    {
    	// The getNext() returns the next element in the (inorder traversal, ordered by key) of the BST
    	BSTree temp = this;
    	//if this is sentinel node -> on sentinel return smallest 
    	if(temp.parent == null) {//return the smallest element of the tree	
    		return temp.getFirst();
    	}
    	/* Case-1
    	   If right subtree of node is not null, then successor(next largest element in the inorder traversal) lies in right subtree. 
    	   Do the following. 
		   Go to right subtree and return the node with minimum key value in the right subtree. 
    	 */
    	if(temp.right != null) {
			return temp.right.getFirst();
    	}
    	/* Case - 2
    	   If right subtree of node is null, then succ is one of the ancestors. Do the following. 
           Travel up using the parent pointer until you see a node which is left child of its parent. The parent of such a node is the succ.
    	*/
    	BSTree p = temp.parent; 
    	while (p != null && temp == p.right) { 
            temp = p; 
            p = p.parent;
    	} 
        return p;
    }
    

    public boolean sanity()
    {
    	BSTree current = this;
    	//if current is sentinel node --> left node of sentinel node must be null
    	if(current.parent == null) {
    		if(current.left != null) {
    			return false;
    		}
    	}
    	if(current.parent !=  null) {
    		//if current is left child of its parent
    		if(current.parent.left == null && current.parent.right == null) {
    			return false;
    		}
    		if(current.parent.left == null) {
    			if(current.parent.right != current) {
    				return false;
    			}
    		}
    		if(current.parent.right == null) {
    			if(current.parent.left != current ) {
    				return false;
    			}
    		}
    	}
    	if(current.left != null) {
    		if(current.left.parent != current) {
    			return false;
    		}
    		if(current.key < current.left.key) {
    			return false;
    		}
    	}
    	if(current.right != null) {
    		if(current.right.parent != current) {
    			return false;
    		}
    		if(current.key > current.right.key) {
    			return false;
    		}
    	}
    	while(current.parent !=null) {
    		current = current.parent;    		
    	}
    	current = current.right;
    	//now current is root of the tree
    	if( isBST(current) != true ) {
    		return false;
    	}
    	if(checkBST(current,Integer.MIN_VALUE,Integer.MAX_VALUE) != true) {
    		return false;
    	}
    	
    	
        return false;
    }
    
    private boolean isBST(BSTree root) {
    	if(root == null) {//empty tree
    		return true;
    	}
    	if(root.left == null && root.right == null) {//only one element
    		return true;
    	}
    	
    	/*
    	 * We are doing inorder traversal of the tree
    	 * and if in the inorder traversal any property violates 
    	 * then it is not a BST
    	 */
    	BSTree first = root.getFirst();
    	BSTree next = root.getFirst().getNext();
    	while(first!=null && next != null) {
    		if(first.key == next.key ) {
    			if(first.address>next.address) {
    				return false;
    			}
    		}
    		else if(first.key > next.key ) {
    			return false;
    		}
    		first = next;
    		next = first.getNext();
    	}
    	return true;
    }
    
    private boolean checkBST(BSTree node,int min , int max) {
    	//since in our case address is unique we will check with the help of address
    	if (node == null) {
    		return true;
    	}
    	if(node.address <= min || node.address >= max	) {
    		return false;
    	}
    		
    	if(!checkBST(node.left,min,node.address) || !checkBST(node.right,node.address,max)) {
    		return false;
    	}
    	return true;
    }

}


 


