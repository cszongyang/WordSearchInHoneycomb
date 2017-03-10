
class TrieNode {
    
    public char val;
    public TrieNode[] children = new TrieNode[128];
    public String word;
    public boolean isWord;
    public TrieNode(char c) {
        this.val = c;
    }
    public TrieNode() {
    }
}
