package fgafa.datastructure.Trie;

/**
 * 
 * Given a Trie, print all words in the Trie
 *
 */
public class PrintTrie extends Trie{

    public void printTrie(){
        printTrie(getRoot(), new StringBuilder());
    }
    
    private void printTrie(TrieNode node, StringBuilder sb){
        if(node.isWord){
            System.out.println(sb.toString());
        }
        
        for(int i = 0; i < 26; i++){
            if(null != node.children[i]){
                sb.append((char)(i + 'a'));
                
                printTrie(node.children[i], sb);
                
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        
    }
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }


}
