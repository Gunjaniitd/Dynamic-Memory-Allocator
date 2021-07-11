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

    public A1List Insert(int address, int size, int key){
        if (this == null || this.next == null) {
            return null; 
        }

        A1List node = new A1List(address, size, key);

        A1List temp = this.next;
        this.next = node;
        node.prev = this;
        temp.prev = node;
        node.next = temp;

        return node;
    }

    public boolean Delete(Dictionary d){
        if (d == null){
            return false;
        }
        
        A1List temp = this.getFirst();
        while (temp != null){
            if (temp.address == d.address && temp.size == d.size && temp.key == d.key){
                break;
            }
            temp = temp.getNext();
        }

        if (temp == null){
            return false;
        }

        if (temp != this){
            A1List u = temp.prev;
            A1List v = temp.next;

            u.next = v;
            v.prev = u;
        }
        else{
            if (temp.getNext() != null){
                A1List v = temp.next;
                temp.address = v.address;
                temp.size = v.size;
                temp.key = v.key;

                temp.next = v.next;
                v.next.prev = temp;
            }
            else if (temp.prev.prev != null){
                A1List u = temp.prev;
                temp.address = u.address;
                temp.size = u.size;
                temp.key = u.key;

                u.prev.next = temp;
                temp.prev = u.prev;
            }
            else{
                A1List u = temp.prev;
                temp.address = u.address;
                temp.size = u.size;
                temp.key = u.key;

                temp.prev = null;
                u.next = null;
            }
        }
        return true;  
    }


    public A1List Find(int k, boolean exact){
        if (this == null) {
            return null;
        }
        A1List temp = this.getFirst();
        if (exact){
            while (temp != null){
                if (temp.key == k){
                    return temp;
                }
                temp = temp.getNext();
            }
        }
        else{
            while (temp != null){
                if (temp.key >= k){
                    return temp;
                }
                temp = temp.getNext();
            }
        }
        return null;
    }

    public A1List getFirst(){

        if (this == null) {
            return null;
        }

        A1List temp = this;
        while (temp.prev != null){
            temp = temp.prev;
        }

        if (temp.next.next == null){
            return null;
        }
        else{
            return temp.next;
        }

    }
    
    public A1List getNext(){

        if(this == null || this.next == null || this.next.next == null){
            return null;
        }
        else{
            return this.next;
        }

    }

    public boolean sanity(){

        if (this == null){                          //empty list
            return true;
        }

        //testing absence of cycle
        A1List slow = this;
        A1List fast = this.next;
        while (fast != null && fast.next != null){                  //checking in forward direction
            if (slow == fast){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        slow = this;
        fast = this.prev;
        while (fast != null && fast.prev != null){                  //checking in backward direction
            if (slow == fast){
                return false;
            }
            slow = slow.prev;
            fast = fast.prev.prev;
        }

        //testing links
        A1List temp = this;
        while(temp.next != null){                  //checking in forward direction
            if (temp.next.prev != temp){
                return false;
            }
            temp = temp.next;
        }

        if (temp.address != -1 || temp.size != -1 || temp.key != -1) {
            return false;
        }
         
        temp = this;
        while(temp.prev != null){                  //checking in backward direction
            if (temp.prev.next != temp){
                return false;
            }
            temp = temp.prev;
        }

        if (temp.address != -1 || temp.size != -1 || temp.key != -1) {
            return false;
        }

        return true;
    }
}