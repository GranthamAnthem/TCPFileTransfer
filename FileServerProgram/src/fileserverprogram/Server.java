

package fileserverprogram;

import java.net.*;
import java.io.*;

public class Server {
    
    public final static int PORT = 7;
    
    public static void main(String[] args) throws IOException {
        String directory = args[0];
        System.out.println(directory);
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;
                 
        try (
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket connection = serverSocket.accept();     
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
        ) {
            
            String inputLine, outputLine;
            
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.equals("index")) {
                    String name = "";
                    File dir = new File(directory);
                     File[] filesList = dir.listFiles();
                     for (File file : filesList) {
                         if (file.isFile() && file.getName().endsWith(".txt") ) {
                         name += file.getName() + " ";
                         }
                     }
                     out.println("Available files: " + name);
                     out.println("/e");
                }
                
                if(inputLine.equals("")) {
                    out.println("No input entered. Try another request");
                    out.println("/e");
                }
                
                else if(inputLine.substring(0,3).equals("get")) {
                    outputLine = inputLine.substring(4);
                    File file = new File(outputLine);
                    if(file.exists()) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String currentLine;
                            String reconstruct = "";
                            while ((currentLine = reader.readLine()) != null)  {
                                reconstruct += currentLine + "\n";
                            }
                            out.println("ok");
                            out.println(reconstruct);
                            out.println("/e");
                        } 
                    }
                    else {
                        out.println("Error file does not exist. Try another request");
                        out.println("/e");
                    }
                }
                
                else if(inputLine.equals("quit")) {
                    out.println("Disconnecting Server");
                    out.println("/e");
                    break;
                }
                
            }
            connection.close();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + PORT + " or listening for a connection");
            System.out.println(e.getMessage());
        } 
    }
}