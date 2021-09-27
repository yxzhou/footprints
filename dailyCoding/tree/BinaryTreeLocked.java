package dailyCoding.tree;

/**
 *
 * Implement locking in a binary tree. A binary tree node can be locked or unlocked only if all of its descendants or ancestors are not locked.

 Design a binary tree node class with the following methods:

 is_locked, which returns whether the node is locked
 lock, which attempts to lock the node. If it cannot be locked, then it should return false. Otherwise, it should lock it and return true.
 unlock, which unlocks the node. If it cannot be unlocked, then it should return false. Otherwise, it should unlock it and return true.

 You may augment the node to add parent pointers or any other property you would like. You may assume the class is used in a single-threaded program,
 so there is no need for actual locks or mutexes. Each method should run in O(h), where h is the height of the tree.
 *
 */

public class BinaryTreeLocked {
    class Node<V>{
        V value;
        Node left;
        Node right;
        Node parent;

        boolean locked = false;
        boolean lockable = true;
    }

    public boolean isLocked(Node node){
        if(node == null){
            throw new IllegalArgumentException("The input node could not be NULL");
        }

        return node.locked;
    }

    /**
     * Reentrant?  if the node is already locked, return true?  it's return false in the following implement.
     *
     *
     */
    public boolean lock(Node node){
        if(node == null || node.locked){
            return false;
        }

        if(isLockable(node)){
            node.locked = true;

            changeLockableUp(node, false);
            return true;
        }

        return false;
    }

    public boolean unlock(Node node){
        if(node == null || !node.locked){
            return false;
        }

        if(isLockable(node.parent)){
            node.locked = false;

            changeLockableUp(node, true);
            return true;
        }

        return false;
    }

    private boolean isLockable(Node node){
        Node curr = node;
        while(curr != null && curr.lockable){
            curr = curr.parent;
        }

        return curr == null;
    }

    private void changeLockableUp(Node node, boolean isLockable){
        Node curr = node;
        while(curr != null){
            curr.lockable = isLockable;
            curr = curr.parent;
        }
    }
}
