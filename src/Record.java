
public class Record {
	
	Pair Key; 
	String Data;
	
	public Record(Pair key, String data){
		Key = key;
		Data = data;
	}
	
	public Pair getKey(){
		return Key;
	}
	
	public String getData(){
		return Data;
	}
}
