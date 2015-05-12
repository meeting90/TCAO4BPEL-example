package cn.edu.nju.cs.utils;

import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ODEResultProducer {
	
	
	public static void main(String []args) throws ParseException{
		InputStream is= ODEResultProducer.class.getResourceAsStream("newode.log");
		  Pattern pattern = Pattern.compile("([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3})");
		  
		Scanner scanner = new Scanner(is);
		while(scanner.hasNext()){
			String line =scanner.nextLine();
			 Matcher matcher = pattern.matcher(line);
			 if (matcher.find()){
				 String time = matcher.group();
				 SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
				 Date  date =sdf.parse(time);
				 String event = scanner.nextLine().trim();
				
				 if(event.startsWith("ProcessInstanceStartedEvent")){
					scanner.nextLine();
					  scanner.nextLine();
					 scanner.nextLine();
					scanner.nextLine();
					 
					
					 String processId = scanner.nextLine();
					 System.out.println(processId.trim() +"\t" + event.trim() + "\t" + date.getTime());
					 
				 }
				 if(event.startsWith("ProcessCompletionEvent")){
					 scanner.nextLine();
					 scanner.nextLine();
					 String processId = scanner.nextLine();
					 System.out.println(processId.trim() +" \t" + event.trim() + "\t" + date.getTime());
				 }
				 
				 
				
				
			 }
		}
		scanner.close();
		
	}

}
