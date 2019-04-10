import java.io.*;

public class save {
		
	File file = new File(System.getProperty("user.dir") + "\\save.txt");
	
	String s;
	
	
	
	save(){
		System.out.println("save obj created");
		System.out.println("save directory is: " + System.getProperty("user.dir"));
	}
	
	
	void read() {
		
		try {
		 BufferedReader br = new BufferedReader(new FileReader(file));

				s = br.readLine();
		
				if(s.equals("")) {
					throw new Exception();
				}
	
		}
		
		catch (Exception e) {
			s = "0";
			System.out.println("read error");
		
		}
	}
		
	
	
	
	void write(int value) {
		
		try {
		PrintWriter writer = new PrintWriter("save.txt", "UTF-8");
     
    	   writer.println(value);
    	   
    	   writer.close();
       }
		
		catch (Exception e) {
			System.out.println("write error");
		}	
		 
	}

	
	
	int return_saved_value() {
		return Integer.valueOf(s);
	}
	
	
	
}
