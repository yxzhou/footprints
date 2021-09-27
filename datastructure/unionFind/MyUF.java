package datastructure.unionFind;

/**
 * Wrong !!
 */

public class MyUF implements UF {

    Root[] roots;
    int count;

    public MyUF(int capacity){
        if(capacity < 1){
            throw new IllegalArgumentException("The valid capacity is larger than 0.");
        }

        this.roots = new Root[capacity];

        for(int i = 0; i < capacity; i++){
            roots[i] = null;
        }

        this.count = 0;
    }

    private class Root{
        int value;

        public Root(int value){
            this.value = value;
        }
    }


    // add connection between p and q
    public void union(int p, int q){
        if(roots[p] == null & roots[q] == null){
            roots[p] = new Root(p);
            roots[q] = roots[p];

            count++;
        }else if(roots[p] == null & roots[q] != null){
            roots[q].value = p;
            roots[p] = roots[q];

        }else if(roots[p] != null & roots[q] == null){
            roots[q] = roots[p];

        }else{ //roots[p] != null & roots[q] != null
            roots[q] = roots[p];

            count --;
        }

    }

    // return component identifier for p (0 to n-1)
    public int find(int p){
        return roots[p].value;

    }

    // return true if p and q are in the same component
    public boolean isConnected(int p, int q){
        return roots[p].value == roots[q].value;

    }

    // return number of components
    public int count(){
        return count;
    }

    /**
     *
     *
     */
    public static void main(String[] args){



    }

}
