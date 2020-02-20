
public class Node {
	
	Node Parent;
	Node rChild;
	Node lChild;
	Record data;
	
	public Node(Node parent, Node rchild, Node lchild, Record Data){
		Parent = parent;
		rChild = rchild;
		lChild = lchild;
		data = Data;
	}
	
	public void setRecord(Record Data){
		data  = Data;
	}
	
	public void SetParent(Node parent){
		Parent = parent;
	}
	
	public void SetrChild(Node child){
		rChild = child;
	}
	
	public void SetlChild(Node child){
		lChild = child;
	}
	
	public Node getParent(){
		return Parent;
	}
	
	public Node getrChild(){
		return rChild;
	}
	
	public Node getlChild(){
		return lChild;
	}
	
	public Record getRecord(){
		return data;
	}

}
