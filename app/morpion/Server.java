package morpion;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5001); // Port 1234

            System.out.println(serverSocket);
            
            System.out.println("Server waiting for players...");

            //Adresse IP du serveur            
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Server IP address: " + ip.getHostAddress());

            
            Socket playerOSocket = serverSocket.accept(); // Wait for player O
            System.out.println("Player O connected: " + playerOSocket);
            
            Socket playerXSocket = serverSocket.accept(); // Wait for player X
            System.out.println("Player X connected: " + playerXSocket);
            
            // Open input and output streams for player 1
            BufferedReader playerOIn = new BufferedReader(new InputStreamReader(playerOSocket.getInputStream()));
            PrintWriter playerOOut = new PrintWriter(playerOSocket.getOutputStream(), true);
            playerOOut.println("O"); // Send player 1 the instance
            
            // Open input and output streams for player 2
            BufferedReader playerXIn = new BufferedReader(new InputStreamReader(playerXSocket.getInputStream()));
            PrintWriter playerXOut = new PrintWriter(playerXSocket.getOutputStream(), true);
            playerXOut.println("X"); // Send player 2 the instance
            
            // Communication loop
            while (true) {
                // Player 1 sends a message
                String playerOMessage = playerOIn.readLine();
                if (playerOMessage != null) {
                    playerXOut.println(playerOMessage);
                }
                
                // Player 2 sends a message
                String playerXMessage = playerXIn.readLine();
                if (playerXMessage != null) {
                    playerOOut.println(playerXMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}