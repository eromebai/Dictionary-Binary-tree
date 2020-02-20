
public class Pair {
	
	private String Word;
	private String Type;
	
	public Pair(String word, String type){
		Word = word;
		Type = type;
	}
	
	public String getWord(){
		return Word;
	}
	
	public String getType(){
		return Type;
	}
	
	public int compareTo(Pair k){
		if(this.getWord().equals(k.getWord())){
			return this.getType().compareTo(k.getType());
		}
		else{
			return this.getWord().compareTo(k.getWord());
		}	
	}
}
