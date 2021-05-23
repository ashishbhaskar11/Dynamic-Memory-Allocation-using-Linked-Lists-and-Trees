// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    {
    	AVLTree newNode = new AVLTree(address, size, key);
    	
    	AVLTree root = this;
    	
    	while( root.parent!=null) {
    		root = root.parent;
    	}
    	// Tree is empty 
    	if(root.right == null) {
    		root.right = newNode;
    		newNode.parent = root;
    		return newNode;
    	}
    	
    	root = root.right;
    	// Now root is the root of the tree
    	
    	AVLTree temp = null;
    	while(root != null) {
    		temp = root;
    		if(key > root.key) {
    			root = root.right;
    		}
    		else if(key < root.key) {
    			root = root.left;
    		}
    		// key == root.key;
    		else {
    			if(address > root.address) {
    				root = root.right;
    			}
    			// since we will not have same address, we take address to be unique -> address < root.address
    			else {
    				root = root.left;
    			}
    		}
    	}
    	// after this while loop root == null and temp is the right place where we have to insert newNode
    	if(key < temp.key) {
    		temp.left = newNode;
    		newNode.parent = temp;
    	}
    	else if( key > temp.key) {
    		temp.right = newNode;
    		newNode.parent = temp;
    	}
    	else {//key = temp.key
    		if(address < temp.address) {
        		temp.left = newNode;
        		newNode.parent = temp;
    		}
    		else {
    			temp.right = newNode;
    			newNode.parent = temp;
    		}
    	}
    	//Now newNode is inserted below temp, now we have to check the balancing property
    	updateHeight(newNode);
    	
    	AVLTree t = newNode;
    	while(t.parent!=null) {
    		int bf = balancingFactor(t);
    		if(bf>1 || bf<-1) {
    			reBalance(t,bf);
    			break;
    		}
    		t = t.parent;
    	}
    	
//    	checkBalance(newNode);
    	// it may be possible that after balancing tree height of node is not correct
    	updateHeight(newNode);
    	
    	return newNode;
    }
    
    private void checkBalance(AVLTree node) {
    	if(node.parent == null) {//recursively reached root of tree
    		return;
    	}
    	int bf = balancingFactor(node);
    	if(bf>1 || bf<-1) {
    		reBalance(node,bf);
    		updateHeight(node);
    	}
    	// recursive call up the tree
    	checkBalance(node.parent);
    	return;
    }
    
    private void reBalance(AVLTree node, int bf) {
    	if(bf > 1) {//either LL or LR case
    		if(height(node.left.left) >= height(node.left.right)) {//LL->RightRotate
    			AVLTree p = node.parent;
    			if(p.left == node) {
    				node = rightRotate(node);
    				p.left = node;
    				node.parent = p;
    				return;
    			}
    			else {
    				node = rightRotate(node);
    				p.right = node;
    				node.parent = p;
    				return;
    			}
    			
    		}
    		else {//LR
    			AVLTree p = node.parent;
    			AVLTree p1 = node;
    			node.left = leftRotate(node.left);
    			node.left.parent = p1;
    			if(p.left == node) {
    				node = rightRotate(node);
    				p.left = node;
    				node.parent = p;
    				return;
    			}
    			else {
    				node = rightRotate(node);
    				p.right = node;
    				node.parent = p;
    				return;
    			}
    		}
    	}
    	else {//either RR or RL case
    		if(height(node.right.right) > height(node.right.left)) {//RR
    			AVLTree p = node.parent;
    			if(p.left == node) {
    				node = leftRotate(node);
    				p.left = node;
    				node.parent = p;
    				return;
    			}
    			else {
    				node = leftRotate(node);
    				p.right = node;
    				node.parent = p;
    				return;
    			}
    		}
    		else {//RL case
    			AVLTree p = node.parent;
    			AVLTree p1 = node;
    			node.right = rightRotate(node.right);
    			node.right.parent = p1;
    			if(p.left == node) {
    				node = leftRotate(node);
    				p.left = node;
    				node.parent = p;
    				return;
    			}
    			else {
    				node = leftRotate(node);
    				p.right = node;
    				node.parent = p;
    				return;
    			}
    		}
    	}
    }

    public boolean Delete(Dictionary e)
    {
    	AVLTree temp = this;
	
    	while( temp.parent!=null) {
    		temp = temp.parent;
    	}
    	temp = temp.right;
    	if(temp == null) {
    		return false;
    	}
    	while(temp != null){
    		if(temp.key == e.key ) {
    			if(temp.address == e.address && temp.size == e.size){
    				// Case 1 temp has two children --> successor will not be null i.e. successor exist
        			if(temp.left !=null && temp.right != null) {
        				AVLTree successor = temp.getNext();
        				temp.address = successor.address;
        				temp.size = successor.size;
        				temp.key = successor.key;
        				temp = successor;
        			}
        			// Case 2 temp is a leaf node
        			if(temp.left == null && temp.right ==null) {
        				//temp is the left child of its parent 
        				if(temp.parent.left==temp) {
        					AVLTree p = temp.parent;
            				temp.parent.left = null;
            				updateHeight(p);
        					checkBalance(p);
//        					updateHeight(p);
            				temp =null;
            				return true;
            			}
        				else {
        					AVLTree p = temp.parent;
//        					System.out.println("here");
        					temp.parent.right = null;
        					updateHeight(p);
        					checkBalance(p);
//        					updateHeight(p);
        					temp = null;
        					return true;
        				}
        			}
        			//Case 3 temp has only one child
        			if(temp.left == null || temp.right == null) {
        				if(temp.right == null) {
        					//if temp is right child of its parent 
        					if( temp.parent.right == temp) {
        						AVLTree p = temp.parent;
        						temp.parent.right = temp.left;
        						temp.left.parent = temp.parent;
        						updateHeight(p);
        						checkBalance(p);
//        						updateHeight(p);
        						temp = null;
        						return true;
        					}
        					//temp is left child of its parent 
        					else {
        						AVLTree p = temp.parent;
        						temp.parent.left = temp.left;
        						temp.left.parent = temp.parent;
        						updateHeight(p);
        						checkBalance(p);
//        						updateHeight(p);
        						temp = null;
        						return true;
        					}
        				}
        				// temp.left == null;
        				else {
        					AVLTree p = temp.parent;
        					//if temp is right child of its parent 
        					if(temp.parent.right == temp) {
        						temp.parent.right = temp.right;
        						temp.right.parent = temp.parent;
        						updateHeight(p);
        						checkBalance(p);
//        						updateHeight(p);
        						temp = null;
        						return true;
        					}
        					//temp is left child of its parent 
        					else {
        						temp.parent.left = temp.right;
        						temp.right.parent = temp.parent;
        						updateHeight(p);
        						checkBalance(p);
//        						updateHeight(p);
        						temp = null;
        						return true;
        					}
        				}
        			}
            	}
    			else if (address > temp.address) {
    				temp = temp.right;
    			}
    			else if (address < temp.address) {
    				temp = temp.left;
    			}
        	}
    		else if(e.key > temp.key) {
    			temp = temp.right;
    		}
    		else {
    			temp = temp.left;
    		}
    	}
    	
        return false;
    }
        
    public AVLTree getFirst()
    {
    	// The getFirst() returns the first (smallest) element of the BST subtree and null if the subtree is empty
    	AVLTree subtree = this;
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
    
    public AVLTree getNext()
    {
    	// The getNext() returns the next element in the (in-order traversal, ordered by key) of the BST
    	AVLTree temp = this;
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
    	AVLTree p = temp.parent;
    	while (p != null && temp == p.right) {
            temp = p;
            p = p.parent;
    	} 
        return p;
    }
    
    public AVLTree Find(int key, boolean exact)
    {
    	AVLTree temp = this;
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
    		AVLTree found = null;
    		while(temp != null ) {
    			if(temp.key == key) {
    				found = temp ;
    				//there are no other element with (key,address) smaller than temp
    				if(temp.left == null ) {
    					return temp;
    				}
    				else {
    					temp = temp.left;
    				}
        		}
    			else if(key > temp.key ) {
    				temp = temp.right;
    			}
    			else {
    				temp = temp.left;
    			}
    		}
    		if(found!=null && found.key == key ) {
    			return found;
    		}
    		return null;
    	}
    	
    	else if(exact == false) {
    		// returns the element with SMALLEST key such that key >= k in the subtree. Returns null in case no such element found.
    		// let's store the last found key in tempfound
    		AVLTree found = null;
    		while(temp != null ) {
    			if(temp.key >= key) {
    				found = temp ;
    				//there are no other element with (key,address) smaller than temp
    				if(temp.left == null ) {
    					return temp;
    				}
    				else {
    					temp = temp.left;
    				}
        		}
    			else if(key > temp.key ) {
    				temp = temp.right;
    			}
    			else {
    				temp = temp.left;
    			}
    		}
    		if(found!=null && found.key >= key ) {
    			return found;
    		}
//    		AVLTree tempfound = null;
//    		while(temp != null || tempfound != null) {
//    			if(tempfound != null && tempfound.key >= key) {
//    				if(tempfound.left == null && tempfound.right == null) {
//    					return tempfound;
//    				}
//    				else if(tempfound.left != null && tempfound.left.key >= key ) {
//    					tempfound = tempfound.left;
//    					temp = tempfound.left;
//    				}
//    				else {
//            			return tempfound;
//    				}
//        		}
//    			else if(temp.key < key) {
//    				temp = temp.right;
//    			}else {
//    				tempfound = temp;
//        			temp = temp.left;
//    			}
//    		}
//    		return null;
    	}
        return null;
    }
    
    public boolean sanity()
    {
    	AVLTree current = this;
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
    	if( isAVL(current) != true ) {
    		return false;
    	}
    	if(checkAVL(current,Integer.MIN_VALUE,Integer.MAX_VALUE) != true) {
    		return false;
    	}
        return true;
    }
    
    private boolean checkAVL(AVLTree node,int min , int max) {
    	if (node == null) {
    		return true;
    	}
    	if(node.key <= min || node.key >= max	) {
    		return false;
    	}
    		
    	if(!checkAVL(node.left,min,node.key) || !checkAVL(node.right,node.key,max)) {
    		return false;
    	}
    	return true;
    }
    
    private boolean isAVL(AVLTree root) {
    	if(root == null) {//empty tree
    		return true;
    	}
    	if(root.left == null && root.right == null) {//only one element
    		return true;
    	}
    	
    	/*
    	 * We are doing in-order traversal of the tree
    	 * and if in the in-order traversal any property violates 
    	 * then it is not a AVLTree
    	 */
    	AVLTree first = root.getFirst();
    	AVLTree next = root.getFirst().getNext();
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
    
    private void updateHeight(AVLTree node) {//TimeComplexity -> O(log(n))
    	while(node.parent!=null) {
    		node.height = Math.max(height(node.left), height(node.right)) + 1;
    		node = node.parent;
    	}
    	return;
    }
    
    private int height(AVLTree node) {//Time Complexity = O(1)
		if(node == null) {
			return -1;
		}
		return node.height;
    }
	
	private int balancingFactor(AVLTree node) {//Time Complexity = O(1)
		if (node == null) {
			return 0;
		}
		return height(node.left)-height(node.right);
	}
    
	private AVLTree rightRotate(AVLTree c) {
		AVLTree b = c.left;
		AVLTree T3 = b.right;
		
		b.right = c;
		c.left = T3;
		c.parent=b;
		if(T3!=null) {
			T3.parent=c;
		}
		
		c.height = Math.max(height(c.left), height(c.right))+1;
		b.height = Math.max(height(b.left), height(b.right))+1;
		return b;
	}
	
	private AVLTree leftRotate(AVLTree c) {
		AVLTree b = c.right;
		AVLTree T2 = b.left;

		// rotate
		b.left = c;
		c.right = T2;
		c.parent=b;
		if(T2!=null) {
			T2.parent=c;
		}

		// height update
		c.height = Math.max(height(c.left), height(c.right))+1;
		b.height = Math.max(height(b.left), height(b.right))+1;
		return b;
	}
    
}


