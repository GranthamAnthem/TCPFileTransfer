

package fileserverprogram;

import java.io.*;
import java.net.*;
 

public class Client {
    public static void main(String[] args) throws IOException {
        
        String hostname = "localhost";
        
        try (
            Socket socket = new Socket(hostname, 7);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
            String fromUser, fromServer;
                
             while(true) {
                 fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
                
                if(fromUser.equals("quit")) {
                    out.println(fromUser);
                    fromServer = in.readLine();
                    System.out.println(fromServer);
                    System.out.println("Disconnecting Client");
                    socket.close();
                    break;
                }

                while ((fromServer = in.readLine()) != null) {
                    String temp = fromServer;
                    System.out.println(fromServer);
                    if(temp.equals("/e")) {
                        break;
                    }
                }
             }
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostname);
            System.exit(1);
        } 
    }
}

