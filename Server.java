import java.net.*; 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*; 

public class Server 
{ 
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null;
    try {
        serverSocket = new ServerSocket(8888); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10007."); 
         System.exit(1); 
        }

    Socket clientSocket = null; 
    System.out.println ("Waiting for connection.....");

    try { 
         clientSocket = serverSocket.accept(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Accept failed."); 
         System.exit(1); 
        } 

    System.out.println ("Connection successful");
    System.out.println ("Waiting for input.....");

    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
                                      true); 
    BufferedReader in = new BufferedReader( 
            new InputStreamReader( clientSocket.getInputStream())); 

    String inputLine; 

    while ((inputLine = in.readLine()) != null) 
    {
    	
    	
    	//HELO
    	if(inputLine.startsWith("HELO")){
    		
    		try {
        		out.println("Server: 210 Hello "+inputLine.substring(5)+", pleased to meet you.");
			} catch (Exception e) {
				out.println("Server: 510 Sorry I can not service your request at this time.Connection closed.");
			}
    	}
    	//REQTIME 
    	else if(inputLine.equals("REQTIME")){
    		try {
    			Date date = new Date();
    			out.println("Server: "+(new SimpleDateFormat("HH:mm:ss")).format(date));
    			
			} catch (Exception e) {
				out.println("Server : 520 Sorry, time is not available right now.");
			}
    	}
    	
    	
    	//REQDATE
    	else if(inputLine.equals("REQDATE")){
    		try {
    			Date date = new Date();
    			out.println("Server 230 "+(new SimpleDateFormat("yyyy-MM-dd")).format(date));
    			
			} catch (Exception e) {
				out.println("Server 520 Sorry, date is not available right now.");
			}
    	}
    	
    	//ECHO
    	else if(inputLine.startsWith("ECHO")){
    		try {
        		out.println("Server 240: " + inputLine.substring(5));  
			} catch (Exception e) {
				out.println("Server: 540 Sorry I can't seem to echo right now!");
			}
    	}
    	
    	//REQIP
    	else if(inputLine.equals("REQIP")){
    		try {
    			String ip  = clientSocket.getRemoteSocketAddress().toString();
				out.println("Server: "+ip.substring(1, ip.lastIndexOf(':')));
			} catch (Exception e) {
				out.println("Server: 550 Sorry, I can't get your in address.");
			}
    	}
    	//BYE
    	else if(inputLine.equals("BYE")){
    		try {
				out.println("Server: 600 See ya later");
			} catch (Exception e) {
				out.println("Wrong input try again ... ");
			}
    	}
    	else{
    		out.println("Wrong command, try again ... ");
//    		out.println(inputLine);
          }
        }

    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
   } 
} 