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

    //helper function for getting sentinal and root
    private AVLTree getSentinal(){
        if (this == null) {
            return null;
        }
        AVLTree temp = this;
        while (temp.parent != null){
            temp = temp.parent;
        }
        return temp;
    }

    //helper function for comparing two dictionary
    private boolean isGreater(Dictionary avl1, Dictionary avl2){
        if (avl1.key > avl2.key){
            return true;
        }
        else if (avl1.key == avl2.key){
            if (avl1.address > avl2.address){
                return true;
            }
            else if (avl1.address == avl2.address){
            	if (avl1.size > avl2.size){
            		return true;
            	}
            	else{
            		return false;
            	}
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    private boolean isEqual(Dictionary avl1, Dictionary avl2){
        if (avl1.key == avl2.key && avl1.address == avl2.address && avl1.size == avl2.size){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isSmaller(Dictionary avl1, Dictionary avl2){
        if (avl1.key < avl2.key){
            return true;
        }
        else if (avl1.key ==  avl2.key){
            if (avl1.address < avl2.address){
                return true;
            }
            else if (avl1.address == avl2.address){
            	if (avl1.size < avl2.size){
            		return true;
            	}
            	else{
            		return false;
            	}
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //private function to get height
    private int Height(AVLTree avl){
        if (avl == null){
            return 0;
        }
        return avl.height;
    }

    //private function to search in the tree - used in delete
    public boolean Contains(Dictionary e){
        AVLTree temp = this.getSentinal().right;
        while (temp != null){
            if (isEqual(temp, e)){
                return true;
            }
            else if (isGreater(temp,e)){
                temp = temp.left;
            }
            else{
                temp = temp.right;
            }
        }
        return false;
    }

    //private functions for rotating the tree
    private AVLTree balanceLeft(){
    	AVLTree z = this;
    	AVLTree y = this.left;
    	AVLTree x;
    	if (Height(y.left) >= Height(y.right)){
    		x = y.left;

    		z.left = y.right;
    		if (y.right != null){y.right.parent = z;}
    		z.height = 1 + Math.max(Height(z.left), Height(z.right));

    		y.right = z;
    		z.parent = y;
    		y.height = 1 + Math.max(Height(x), Height(z));
    		return y;
    	}
    	else{
    		x = y.right;

    		y.right = x.left;
    		if (x.left != null) {x.left.parent = y;}
    		y.height = 1 + Math.max(Height(y.left), Height(y.right));

    		z.left = x.right;
    		if (x.right != null){x.right.parent = z;}
    		z.height = 1 + Math.max(Height(z.left), Height(z.right));

    		x.left = y;
    		y.parent = x;
    		x.right = z;
    		z.parent = x;
    		x.height = 1 + Math.max(Height(y), Height(z));
    		return x;
    	}
    }

    private AVLTree balanceRight(){
    	AVLTree z = this;
    	AVLTree y = this.right;	
    	AVLTree x;
    	if (Height(y.left) <= Height(y.right)){
    		x = y.right;

    		z.right = y.left;
    		if (y.left != null) {y.left.parent = z;}
    		z.height = 1 + Math.max(Height(z.left), Height(z.right));

    		y.left = z;
    		z.parent = y;
    		y.height = 1 + Math.max(Height(x), Height(z));
    		return y;
    	}
    	else{
    		x = y.left;

    		z.right = x.left;
    		if (x.left != null) {x.left.parent = z;}
    		z.height = 1+ Math.max(Height(z.left), Height(z.right));

    		y.left = x.right;
    		if (x.right != null) {x.right.parent = y;}
    		y.height = 1 + Math.max(Height(y.left), Height(y.right));

    		x.left = z;
    		z.parent = x;
    		x.right = y;
    		y.parent = x;
    		x.height = 1 + Math.max(Height(y), Height(z));
    		return x;
    	}
    }

    //insertion by using recursive helper
    public AVLTree Insert(int address, int size, int key) {
    	AVLTree temp = new AVLTree(address, size, key);
    	if (this == null){
    		return null;
    	}

    	AVLTree sentinel = this.getSentinal();
    	AVLTree root = sentinel.right;

    	root = Insert_helper(root, temp);
    	sentinel.right = root;
    	root.parent = sentinel;

    	return temp;
    }

    private AVLTree Insert_helper(AVLTree subtree, AVLTree temp){
    	if (subtree == null){                                             
            temp.height += 1;
    		return temp;
    	}

    	if (isGreater(temp, subtree)){
    		subtree.right = Insert_helper(subtree.right, temp);
            subtree.right.parent = subtree;
	                                                                       
	        subtree.height = 1 + Math.max(Height(subtree.left), Height(subtree.right));                                                               
			if (Height(subtree.right) - Height(subtree.left) > 1) {subtree = subtree.balanceRight();}
    	}
    	else{
    		subtree.left = Insert_helper(subtree.left, temp);
            subtree.left.parent = subtree;

            subtree.height = 1 + Math.max(Height(subtree.left), Height(subtree.right));                                                               
			if (Height(subtree.left) - Height(subtree.right) > 1) {subtree = subtree.balanceLeft();}
    	}
    	return subtree;
    }


    //deletion using recursive helper
    public boolean Delete(Dictionary e){
        if (this == null || e == null){
            return false;
        }
        AVLTree sentinel = this.getSentinal();
        AVLTree root = sentinel.right;

        if (this.Contains(e)) {
            root = Delete_helper(root, e);
            sentinel.right = root;
            if (root != null) {root.parent = sentinel;}
            return true;
        }
        return false;
    }

    public AVLTree Delete_helper(AVLTree subtree, Dictionary temp){
        if (subtree == null){                                               //unlikely to arise 
            return null;
        }

        if (isGreater(temp, subtree)){
            subtree.right = Delete_helper(subtree.right, temp);
            if (subtree.right != null) {subtree.right.parent = subtree;}
            
            subtree.height = 1 + Math.max(Height(subtree.left), Height(subtree.right));                                                               
			if (Height(subtree.left) - Height(subtree.right) > 1) {subtree = subtree.balanceLeft();}
        }

        else if (isGreater(subtree, temp)){
            subtree.left = Delete_helper(subtree.left, temp);
            if (subtree.left != null) {subtree.left.parent = subtree;}
           
            subtree.height = 1 + Math.max(Height(subtree.left), Height(subtree.right));                                                               
			if (Height(subtree.right) - Height(subtree.left) > 1) {subtree = subtree.balanceRight();}
        }

        else {                                                        
            if(subtree.left == null && subtree.right == null){        
                return null;
            }
            else if(subtree.left == null || subtree.right == null){   

                if (subtree.left == null){return subtree.right;}
                return subtree.left; 

            }
            else{                                                     
                AVLTree successor = subtree.getNext();

                AVLTree new_node = new AVLTree(successor.address, successor.size, successor.key);
                if (subtree.parent.left == subtree){
                	subtree.parent.left = new_node;
	            }
	            else{
	                subtree.parent.right = new_node;
	            }
	            new_node.parent = subtree.parent;
	            subtree.left.parent = new_node;
	            new_node.left = subtree.left;
	            subtree.right.parent = new_node;
	            new_node.right = subtree.right;
	            subtree = new_node;

                subtree.right = Delete_helper(subtree.right, successor);
                if (subtree.right != null) {subtree.right.parent = subtree;}

                subtree.height = 1 + Math.max(Height(subtree.left), Height(subtree.right));                                                               
				if (Height(subtree.left) - Height(subtree.right) > 1) {subtree = subtree.balanceLeft();}
               
            }
        }
        return subtree;
    }

    public AVLTree Find(int key, boolean exact){
    	if (this == null){
            return null;
        }

        AVLTree temp = this.getSentinal().right;
        AVLTree answer = null;

        if (exact) {
            while (temp != null){
                if (temp.key == key) {
                    answer = temp;
                    temp = temp.left;
                }
                else if (temp.key < key){
                    temp = temp.right;
                }
                else{
                    temp = temp.left;
                }
            }
        }

        else{
            while (temp != null){
                if (temp.key >= key) {
                    answer = temp;
                    temp = temp.left;
                }
                else{ 
                    temp = temp.right;
                }
            }
        } 

        return answer;
    }

    public AVLTree getFirst(){
        if (this == null){
            return null;
        }
        AVLTree temp = this.getSentinal().right;

        if (temp == null){
            return null;
        }
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    public AVLTree getNext(){

        if (this == null){
            return null;
        }

        if (this.address == -1 && this.size == -1 && this.key == -1){
            return null;
        }

        AVLTree temp = this;

        if (this.right != null){                           //right subtree eists
            temp = temp.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }

        else{                                              //right subtree is null
            while (temp.parent != null && temp.parent.right == temp){
                temp = temp.parent;
            }
            if (temp.parent == null){
                return null;
            }
            return temp.parent;
        }

    }

    public boolean sanity(){
        if (this == null){
            return true;
        }
        //check this.getSentinal() works well i.e. doesn't get into infinite loop because of cycle 
        //from this to sentinal.
        AVLTree slow = this;
        AVLTree fast = this.parent;
        while (fast != null && fast.parent != null){
            if (slow == fast){
                return false;
            }
            slow = slow.parent;
            fast = fast.parent.parent; 
        }

        AVLTree temp = this.getSentinal();
        if (temp.address != -1 || temp.size != -1 || temp.key != -1 || temp.left != null){
            return false;
        }

        boolean bool1 = checkLinks(temp);

        boolean bool2 = true;
        if (temp.right != null){
            AVLTree lowerBound = new AVLTree(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
            AVLTree upperBound = new AVLTree(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
            bool2 = checkValues(temp.right, lowerBound, upperBound);
        }

        boolean bool3 = checkHeight(temp.right);
        return bool1 && bool2 && bool3;
    }

    private boolean checkLinks(AVLTree node){
        if (node == null){
            return true;
        }
        if (node.left != null && node.left.parent != node){
            return false;
        }
        if (node.right != null && node.right.parent != node){
            return false;
        }
        return checkLinks(node.left) && checkLinks(node.right);
    }

    private boolean checkValues(AVLTree node, AVLTree lowerBound, AVLTree upperBound){
        if (node == null) {
            return true;
        }
        if (isSmaller(node, lowerBound) ){
            return false;
        }
        if (isSmaller(upperBound, node) ){
            return false;
        }
        return checkValues(node.left, lowerBound, node) && checkValues(node.right, node, upperBound);
    }

    private boolean checkHeight(AVLTree node){
    	if (node == null) {return true;}

    	if (Height(node.left) - Height(node.right) > 1 || Height(node.left) - Height(node.right) < -1) {
    		return false;
    	}

    	return checkHeight(node.left) && checkHeight(node.right);
    }

}


