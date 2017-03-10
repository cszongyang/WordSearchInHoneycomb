import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WordSearch {
	
	private static String dictionaryPath = "/Users/zongyangli/Desktop/quantcast/WordSearch/dictionary.txt";
	private static String honeyCombPath = "/Users/zongyangli/Desktop/quantcast/WordSearch/honeycomb.txt";

	public static void main(String[] args) {
		Trie trie = new Trie();
		List<String> layers = new ArrayList<>();
		List<String> wordList = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		
		try {
			if (args.length > 0){
				dictionaryPath = args[0];
				honeyCombPath = args[1];
			}
				
			sc = new Scanner(new File(dictionaryPath));
			while(sc.hasNextLine()){
				trie.insert(sc.nextLine().trim());
			}
			
			sc = new Scanner(new File(honeyCombPath));
			sc.nextLine();
			while(sc.hasNextLine()){
				layers.add(sc.nextLine().trim());
			}
			
			
		} catch (Exception e) {

		} finally {
			sc.close();
		}
		

		
		List<List<LinkedPolygonNode>> positionArray = new ArrayList<>();
		
		populateLinkedNodesArray(layers, positionArray);
		
		setNeighbors(positionArray);
		
		
		List<String> wordsFound = new ArrayList<>();
		
		find(positionArray, trie, wordsFound);
		
		
		for(String word : wordsFound){
			System.out.println(word);
		}
		
	}
	
	
	public static void find(List<List<LinkedPolygonNode>> LinkedNodesArray, Trie trie, List<String> wordsFound){
		
		for (int i = 0; i < LinkedNodesArray.size(); i++) {
			for (int j = 0; j < LinkedNodesArray.get(i).size(); j++){
				
//				dfs(LinkedNodesArray.get(i).get(j), trie.root, wordsFound);
				
				trie.findWords(LinkedNodesArray.get(i).get(j), wordsFound);
			}
		}
	}	
	
	public static void populateLinkedNodesArray(List<String> layers, List<List<LinkedPolygonNode>> LinkedNodesArray){
		
		for (int i = 0; i < layers.size(); i++) {	
			LinkedNodesArray.add(new ArrayList<LinkedPolygonNode>());
			
			String layer = layers.get(i);
			
			for (int j = 0; j < layer.length(); j++) {
				
				LinkedPolygonNode node = new LinkedPolygonNode(layer.charAt(j));
				
				LinkedNodesArray.get(i).add(node);
			}
		}
	}
	
	
	public static void setNeighbors(List<List<LinkedPolygonNode>> LinkedNodesArray){
		
		int layerCount = LinkedNodesArray.size();
		
		for (int i = 0; i < layerCount; i++) {
			
			int charCount = LinkedNodesArray.get(i).size();
			
			for(int j = 0; j < charCount; j++){
				
				LinkedPolygonNode node = LinkedNodesArray.get(i).get(j);
				
				if(i > 0){
					
					//inside right neighbor
					if (j < charCount - 1) {
						node.adjList.add(LinkedNodesArray.get(i - 1).get((i-1) * (j / i) + (j % i)));
					} else {
						node.adjList.add(LinkedNodesArray.get(i - 1).get(0));
					}
					
					//inside left neighbor (only exists if not corner)
					if (!isCorner(i, j)) {
						node.adjList.add(LinkedNodesArray.get(i - 1).get((i-1) * (j / i) + (j % i) - 1));
					}
					
					//pre neighbor(same layer && clockwise)
					if (j > 0) {
						node.adjList.add(LinkedNodesArray.get(i).get(j - 1));
					} else {
						node.adjList.add(LinkedNodesArray.get(i).get(charCount - 1));
					}
					
					//next neighbor(same layer && clockwise)
					if (j < charCount - 1) {
						node.adjList.add(LinkedNodesArray.get(i).get(j + 1));
					} else {
						node.adjList.add(LinkedNodesArray.get(i).get(0));
					}
					
					if (i < layerCount - 1) {
						//outside left neighbor (only exists if corner)
						if (isCorner(i, j)) {
							if (j > 0) {
								node.adjList.add(LinkedNodesArray.get(i + 1).get((i + 1) * (j / i) - 1));
							} else {
								node.adjList.add(LinkedNodesArray.get(i + 1).get(charCount + 6 - 1));
							}
						}
						//outside middle neighbor
						node.adjList.add(LinkedNodesArray.get(i + 1).get((i + 1) * (j / i) + (j % i)));
						//outside right neighbor
						node.adjList.add(LinkedNodesArray.get(i + 1).get((i + 1) * (j / i) + (j % i) + 1));
					}
					

					
				} else if (layerCount > 1) {
					for (int k = 0; k < LinkedNodesArray.get(1).size(); k++) {
						node.adjList.add(LinkedNodesArray.get(1).get(k));
					}
				}
				
				
			}
		}
	}
	
	
	private static boolean isCorner(int layerIndex, int charIndex) {

		return layerIndex == 0 || (charIndex % layerIndex) == 0;
	}

}
