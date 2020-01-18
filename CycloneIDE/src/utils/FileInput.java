package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//This class contains a static method to convert the contents of a file to a string
public class FileInput {
	
	//This method takes the contents of a file at a specified path and converts it to a string
	public static String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		
		//Read each line from the file
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				 builder.append(line + "\n");
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//Return the file contents as a string
		return builder.toString();
		
	}
	
}
