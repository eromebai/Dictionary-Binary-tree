//BY EVAN ROME-BAILEY, STUDENT #250867976, LIST #195

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserInterface {

	//Static method used to determine the type of a given record for entry
	private static String typeTest(String def){
		if(def.toLowerCase().endsWith(".wav") || def.toLowerCase().endsWith(".mid")){
			return "audio";
		}
		else if(def.toLowerCase().endsWith(".jpg") || def.toLowerCase().endsWith(".gif")){
			return "image";
		}
		else{
			return "text";
		}
	}
	
	//The main method, takes user input
	public static void main(String[] args) {
		
		String filename = "small.txt";
		
		OrderedDictionary dict = new OrderedDictionary();
		
		//Reads the given file and enters records in dictionary
		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));
			String name = input.readLine();
			String def;
			try {
				while(name != null){
					def = input.readLine();
					dict.put(new Record(new Pair(name.toLowerCase(), typeTest(def)), def));
					name = input.readLine();
				}
			}catch(DictionaryException e){
				System.out.println("Error in inputting file to dictionary");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Takes user input and performs action requested
		String usrinpt;
		StringReader keyboard = new StringReader();
		boolean yes = true;
		do{
			usrinpt = keyboard.read("Enter a command: ");
			
			if(usrinpt.toLowerCase().startsWith("finish")){
				yes = false;
			}
			
			else if(usrinpt.toLowerCase().startsWith("get")){
				String[] list = usrinpt.split(" ");
				String word = list[1].toLowerCase(); 
				
				Record image = dict.get(new Pair(word, "image"));
				Record audio = dict.get(new Pair(word, "audio"));
				Record text = dict.get(new Pair(word, "text"));
				
				boolean checker = true;
				
				if(image != null){
					PictureViewer pic = new PictureViewer();
					try{
						pic.show(image.getData());
						checker = false;
					}catch (MultimediaException e){
						System.out.println("Image file not found for " + image.getKey().getWord());
					}
				}
				if(audio != null){
					SoundPlayer sound = new SoundPlayer();
					try {
						sound.play(audio.getData());
					} catch (MultimediaException e) {
						System.out.println("Audio file not found for " + audio.getKey().getWord());
					}
					checker = false;
				}
				if(text != null){
					System.out.println(text.getData());
					checker = false;
				}
				
				if(checker){
					System.out.println("The word " + word + " is not in the ordered dictionary.");
					Record pred = dict.predecessor(new Pair(word, "audio"));
					if(pred != null){
						System.out.println("Preceding word: " + pred.getKey().getWord());
					}
					else{
						System.out.println("Preceding word: ");
					}
					Record succ = dict.successor(new Pair(word, "audio"));
					if(succ !=null){
						System.out.println("Following word: "  + succ.getKey().getWord());
					}
					else{
						System.out.println("Following word: ");
					}
				}
				
			}
			
			else if(usrinpt.toLowerCase().startsWith("delete")){
				String[] list = usrinpt.split(" ");
				String word = list[1].toLowerCase(); 
				String type = list[2].toLowerCase();
				
				try{
					dict.remove(new Pair(word, type));
				}catch(DictionaryException e){
					System.out.println("No record in the ordered dictionary has key (" + word + "," + type + ")");
				}
				
			}
			
			else if(usrinpt.toLowerCase().startsWith("put")){
				String[] list = usrinpt.split(" ");
				String word = list[1].toLowerCase(); 
				String type = list[2].toLowerCase();
				String dat = "";
				for(int i = 3; i < list.length; i++){
					dat = dat + list[i];
					dat = dat + " ";
				}
				String data = dat;
				
				try{
					dict.put(new Record(new Pair(word, type), data));
				}catch(DictionaryException e){
					System.out.println("A record with the given key (" + word + "," + type + ") is already in the ordered dictionary");
				}
			}
			
			else if(usrinpt.toLowerCase().startsWith("list")){
				String[] list = usrinpt.split(" ");
				String word = list[1].toLowerCase(); 
				
				Record rec = dict.get(new Pair(word, "audio"));
				if(rec == null){
				}
				else{
					System.out.println(rec.getKey().getWord());
				}
				Record succ = dict.successor(new Pair(word, "audio"));
				while(succ.getKey().getWord().startsWith(word)){
					System.out.println(succ.getKey().getWord());
					Record r = dict.successor(succ.getKey());
					succ = r;
				}
				
								
			}
			
			else if(usrinpt.toLowerCase().startsWith("smallest")){
				Record small = dict.smallest();
				System.out.println(small.getKey().getWord() + ","  + small.getKey().getType()  + "," + small.getData());
			}
			
			else if(usrinpt.toLowerCase().startsWith("largest")){
				Record large = dict.largest();
				System.out.println(large.getKey().getWord() + ","  + large.getKey().getType()  + "," + large.getData());
			}
			
			else{
				System.out.println("That was an invalid command.");
			}
			
		}while(yes);
		System.out.println("Goodbye");		

	}

}
