// Class: Implementatio n of BST in A2
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

    private BSTree getSentinal(){
        if (this == null) {
            return null;
        }
        BSTree temp = this;
        while (temp.parent != null){
            temp = temp.parent;
        }
        return temp;
    }

    private boolean isGreater(Dictionary bst1, Dictionary bst2){
        if (bst1.key > bst2.key){
            return true;
        }
        else if (bst1.key == bst2.key){
            if (bst1.address > bst2.address){
                return true;
            }
            else if (bst1.address == bst2.address){
            	if (bst1.size > bst2.size){
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

    private boolean isEqual(Dictionary bst1, Dictionary bst2){
        if (bst1.key == bst2.key && bst1.address == bst2.address && bst1.size == bst2.size){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isSmaller(Dictionary bst1, Dictionary bst2){
        if (bst1.key < bst2.key){
            return true;
        }
        else if (bst1.key ==  bst2.key){
            if (bst1.address < bst2.address){
                return true;
            }
            else if (bst1.address == bst2.address){
            	if (bst1.size < bst2.size){
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

    public BSTree Insert(int address, int size, int key) {

        if (this == null){
            return null;
        }

        BSTree curr = this.getSentinal();
        BSTree temp = new BSTree(address, size, key);

        if (curr.right == null){                          //empty tree
            curr.right = temp;
            temp.parent = curr;
            return temp;
        }

        curr = curr.right;
        BSTree prev = null;

        while (curr != null){                             //finding correct position
            if (isGreater(temp, curr)){
                prev = curr;
                curr = curr.right;
            }
            else{
                prev = curr;
                curr = curr.left;
            }
        }
                        
        if (isSmaller(prev, temp)){                       //insert now after finding correct position
            prev.right = temp;
            temp.parent = prev;
        }
        else{
            prev.left = temp;
            temp.parent = prev;
        }
        return temp;
    }

    public boolean Delete(Dictionary e){
        if (e == null){
            return false;
        }
        BSTree temp = this.getSentinal().right;
        while (temp != null){
            if (isGreater(temp, e)){
                temp = temp.left;
            }
            else if (isSmaller(temp, e)){
                temp = temp.right;
            }
            else{
                Delete_helper(temp);
                return true;
            }
        }
        return false;
    }
 
    private void Delete_helper(BSTree temp){
        if (temp.left == null && temp.right == null){      //deleting a leaf
            if (temp.parent.left == temp){
                temp.parent.left = null;
            }
            else{
                temp.parent.right = null;
            } 
        }
        else if (temp.left == null) {                      //having only right child
            if (temp.parent.left == temp){
                temp.parent.left = temp.right;
                temp.right.parent = temp.parent;
            }
            else {
                temp.parent.right = temp.right;
                temp.right.parent = temp.parent;
            }
        }
        else if (temp.right == null) {                      //having only left child
            if (temp.parent.left == temp){
                temp.parent.left = temp.left;
                temp.left.parent = temp.parent;
            }
            else {
                temp.parent.right = temp.left;
                temp.left.parent = temp.parent;
            }
        }
        else {                                              //having both child
            BSTree successor = temp.getNext();
            BSTree new_node = new BSTree(successor.address, successor.size, successor.key);
            if (temp.parent.left == temp){
                temp.parent.left = new_node;
            }
            else{
                temp.parent.right = new_node;
            }
            new_node.parent = temp.parent;
            temp.left.parent = new_node;
            new_node.left = temp.left;
            temp.right.parent = new_node;
            new_node.right = temp.right;
            Delete_helper(successor);
        }
    }   
    
    public BSTree Find(int key, boolean exact){
    	if (this == null){
            return null;
        }
        BSTree temp = this.getSentinal().right;
        BSTree answer = null;
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

    public BSTree getFirst(){
        if (this == null){
            return null;
        }
        BSTree temp = this.getSentinal().right;

        if (temp == null){
            return null;
        }
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    public BSTree getNext(){

        if (this == null){
            return null;
        }

        if (this.address == -1 && this.size == -1 && this.key == -1){
            return null;
        }

        BSTree temp = this;
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
        BSTree slow = this;
        BSTree fast = this.parent;
        while (fast != null && fast.parent != null){
            if (slow == fast){
                return false;
            }
            slow = slow.parent;
            fast = fast.parent.parent; 
        }

        BSTree temp = this.getSentinal();
        if (temp.address != -1 || temp.size != -1 || temp.key != -1 || temp.left != null){
            return false;
        }

        boolean bool1 = checkLinks(temp);

        boolean bool2 = true;
        if (temp.right != null){
            BSTree lowerBound = new BSTree(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
            BSTree upperBound = new BSTree(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
            bool2 = checkValues(temp.right, lowerBound, upperBound);
        }

        return bool1 && bool2;
    }

    private boolean checkLinks(BSTree node){
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

    private boolean checkValues(BSTree node, BSTree lowerBound, BSTree upperBound){
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
}