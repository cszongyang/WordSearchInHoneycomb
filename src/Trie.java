import java.util.List;

public class Trie {
    public TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        
        for (char c : word.toCharArray()) {
            if (node.children[c] == null) {
                node.children[c] = new TrieNode(c);
            }
            node = node.children[c];
        }
        node.word = word;
        node.isWord = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if(node.children[c] == null) {
                return false;
            }
            node = node.children[c];
        }
        return node.isWord;
    }
    
    //
    public void findWords(LinkedPolygonNode node, List<String> res){
    	dfs(node, root, res);
    }
    
    public static void dfs(LinkedPolygonNode node, TrieNode root, List<String> res){
        
        
    	if(node.isVisited == true || root.children[node.val] == null){
            return;
        }

        root = root.children[node.val];
        if(root.word != null){
        	res.add(root.word);
            root.word = null;
        }
        
        node.isVisited = true;
        
        for(LinkedPolygonNode newNode : node.adjList){
            dfs(newNode, root, res);
        }
        
        node.isVisited = false;
    }
    

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (node.children[c] == null) {
                return false;
            }
            node = node.children[c];
        }
        return node != null;
    }
    
    
}