//BY EVAN ROME-BAILEY, STUDENT #250867976, LIST #195

public class OrderedDictionary implements OrderedDictionaryADT{
	private Node root = null;
	
	//Retrieves the record representing a given word and type if it is held in the dictionary
	public Record get(Pair k){
		
		if(root == null){
			return null;
		}
		else{
			Node node = getHelper(root, k);
			if(node == null){
				return null;
			}
			else{
				return node.getRecord();
			}
		}
		
	}
	
	//Helper method for get
	private Node getHelper(Node r, Pair k){
		
		if(r.getRecord() == null){
			return null;
		}
		
		int compare = r.getRecord().getKey().compareTo(k);
		
		if(compare == 0){
			return r;
		}
		
		else if(compare < 0){
			return getHelper(r.getrChild(), k);
		}
		
		else if(compare > 0){
			return getHelper(r.getlChild(), k);
		}
		else{
			return null;
		}
	}
	
	//Puts a new record in the dictionary containing the given name, type and data
	public void put(Record r) throws DictionaryException{
		
		if(root == null){
			Node lC = new Node(null, null, null, null);
			Node rC = new Node(null, null, null, null);
			root = new Node(null, rC, lC, r);
			lC.SetParent(root);
			rC.SetParent(root);
		}
		
		else{
			
			Node node = putHelper(root, r);
			
			if(node == null){
				throw(new DictionaryException());
			}
			
			int compare = node.getRecord().getKey().compareTo(r.getKey());
			
			if(compare < 0){
				Node lC = new Node(null, null, null, null);
				Node rC = new Node(null, null, null, null);
				Node rN = new Node(node, rC, lC,  r);
				lC.SetParent(rN);
				rC.SetParent(rN);
				node.SetrChild(rN);
			}
			
			else if(compare > 0){
				Node lC = new Node(null, null, null, null);
				Node rC = new Node(null, null, null, null);
				Node lN = new Node(node, rC, lC, r);
				lC.SetParent(lN);
				rC.SetParent(lN);
				node.SetlChild(lN);
			}
		}
	}
	
	//Helper method for put
	public Node putHelper(Node r, Record d){
		
		if(r.getRecord() == null){
			return r.getParent();
		}
		
		int compare = r.getRecord().getKey().compareTo(d.getKey());
		
		if(compare == 0){
			return null;
		}
		
		else if(compare < 0){
			return putHelper(r.getrChild(), d);
		}
		
		else if(compare > 0){
			return putHelper(r.getlChild(), d);
		}
		else{
			return null;
		}
		
	}
	
	//Removes an entry in the dictionary if it contains the given word and type, throws a DictionaryException if no such record exists
	public void remove(Pair k) throws DictionaryException{
		
		if(root == null){
			throw(new DictionaryException());
		}
		
		if(root.getlChild().getRecord() == null && root.getrChild().getRecord() == null && root.getRecord().getKey().compareTo(k) == 0){
			root = null;
			return;
		}
		
		boolean check = removeHelper(root, k);
		
		if(check == false){
			throw(new DictionaryException());
		}
		
	}
	
	//Helper method for removal
	private boolean removeHelper(Node r, Pair k){
		
		Node node = getHelper(r, k);
		
		if(node == null){
			return false;
		}
		
		else{
			if(node.getlChild().getRecord() == null){
				Node p = node.getParent();
				Node rC = node.getrChild();
				if(p == null){
					rC.SetParent(null);
					root = rC;
					return true;
				}
				else{
					if(p.getlChild().getRecord() != null && p.getlChild().getRecord().getKey().compareTo(node.getRecord().getKey()) == 0){
						p.SetlChild(rC);
						rC.SetParent(p);
						return true;
					}
					else if(p.getrChild().getRecord() != null && p.getrChild().getRecord().getKey().compareTo(node.getRecord().getKey()) == 0){
						p.SetrChild(rC);
						rC.SetParent(p);
						return true;
					}
					else{
						return false;
					}
				}
			}
			else if(node.getrChild().getRecord() == null){
				Node p = node.getParent();
				Node lC = node.getlChild();
				if(p == null){
					lC.SetParent(null);
					root = lC;
					return true;
				}
				else{
					if(p.getlChild().getRecord() != null && p.getlChild().getRecord().getKey().compareTo(node.getRecord().getKey()) == 0){
						p.SetlChild(lC);
						lC.SetParent(p);
						return true;
					}
					else if(p.getrChild().getRecord() != null && p.getrChild().getRecord().getKey().compareTo(node.getRecord().getKey()) == 0){
						p.SetrChild(lC);
						lC.SetParent(p);
						return true;
					}
					else{
						return false;
					}
					
				}
			}
			else{
				Node small = smallestHelper(node.getrChild());
				node.setRecord(small.getRecord());
				return removeHelper(small, small.getRecord().getKey());
			}
		}
	}
	
	//Helper method for successor and predecessor, finds the position where a record would be located regardless of whether or not it's in the dictionary
	private Node findPos(Node r, Pair k){
		
		
		if(r.getRecord() == null){
			return r.getParent();
		}
		
		int compare = r.getRecord().getKey().compareTo(k);
		
		if(compare == 0){
			return r;
		}
		
		else if(compare < 0){
			return findPos(r.getrChild(), k);
		}
		
		else if(compare > 0){
			return findPos(r.getlChild(), k);
		}
		else{
			return r;
		}
	}
	
	//Finds the lexicographical successor to the given key, regardless of if key is in the dictionary
	public Record successor(Pair k){
		
		if(root == null){
			return null;
		}
		
		else{
			Node n = findPos(root, k);
			
			if(k.compareTo(n.getRecord().getKey()) < 0){
				return n.getRecord();
			}
			
			else if(n.getrChild() != null && n.getrChild().getRecord() != null){
				return smallestHelper(n.getrChild()).getRecord();
			}
			
			else{
				Node p = n;
				Node pPrime = p.getParent();
				while(p.getParent() != null && p.getParent().getrChild() == p){
					pPrime = p.getParent();
					p = pPrime;
				}
				if(p.getParent() == null){
					return null;
				}
				else{
					return pPrime.getParent().getRecord();
				}
			}	
		}
	}
	
	//Finds the lexicographical predecessor to the given key, regardless of if key is in the dictionary
	public Record predecessor(Pair k){
		if(root == null){
			return null;
		}
		
		else{
			Node n = findPos(root, k);
			
			if(k.compareTo(n.getRecord().getKey()) > 0){
				return n.getRecord();
			}
			
			if(n.getlChild() != null && n.getlChild().getRecord() != null){
				return largestHelper(n.getlChild()).getRecord();
			}
			
			else{
				Node p = n;
				Node pPrime = p.getParent();
				while(p.getParent() != null && p.getParent().getlChild() == p){
					pPrime  = p.getParent();
					p  = pPrime;
				}
				if(p.getParent() == null){
					return null;
				}
				else{
					return pPrime.getParent().getRecord();
				}
			}
		}
		
	}
	
	//Finds the lexicographically smallest record in the dictionary
	public Record smallest(){
		
		if(root == null){
			return null;
		}
		
		else if(root.getlChild().getRecord() == null){
			return root.getRecord();
		}
		
		else{
			Node n = root;
			while(n.getlChild().getRecord() != null){
				n = n.getlChild();
			}
			return n.getRecord();
		}
	}
	
	//Helper method for smallest
	private Node smallestHelper(Node r){
		if(r.getlChild().getRecord() == null){
			return r;
		}
		
		else{
			Node n = r;
			while(n.getlChild().getRecord() != null){
				n = n.getlChild();
			}
			return n;
		}
	}
	
	//Finds the lexicographically largest record in the dictionary
	public Record largest(){
		
		if(root == null){
			return null;
		}
		
		else if(root.getrChild().getRecord() == null){
			return root.getRecord();
		}
		
		else{
			Node n = root;
			while(n.getrChild().getRecord() != null){
				n = n.getrChild();
			}
			return n.getRecord();
		}
	}
	
	//Helper method for largest
	private Node largestHelper(Node r){
		if(r.getrChild().getRecord() == null){
			return r;
		}
		
		else{
			Node n = r;
			while(n.getrChild().getRecord() != null){
				n = n.getrChild();
			}
			return n;
		}
	}
}
