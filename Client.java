import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
//        String serverHostname = new String ("127.0.0.1");
    	
    	String serverHostname = args[0];
    	int port = 8888;
        if (args.length > 0)
        	serverHostname = args[0];
        	port = Integer.parseInt(args[1]);
        System.out.println ("Attemping to connect to host " +
		serverHostname + " on port"+port);

        
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.print ("input: ");
	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
	    System.out.println(in.readLine());
            System.out.print ("input: ");
	}

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}
