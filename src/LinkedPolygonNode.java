import java.util.ArrayList;
import java.util.List;


public class LinkedPolygonNode {
	
	private final int adjNum = 6;
	
	public char val;
	
	public boolean isVisited;
	
	List<LinkedPolygonNode> adjList;
	
	public LinkedPolygonNode(char c) {
		adjList = new ArrayList<>();
		this.val = c;
		isVisited = false;
	}
}
