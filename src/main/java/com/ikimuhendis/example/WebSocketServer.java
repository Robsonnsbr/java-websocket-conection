package com.ikimuhendis.example;

import com.ikimuhendis.server.WSServer;

import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.websocket.DeploymentException;

import java.io.IOException;

public class WebSocketServer {

    public static void main(String[] args) { 
        Server server = new Server("localhost", 7788, "/pub", WSServer.class);
        try {
            server.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command;
            while (true) {
                System.out.print("Enter a message to send to all clients (or 'exit' to quit): ");
                command = reader.readLine();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }
                sendCommand(command);
            }
        } catch (IOException | DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
    public static String sendCommand(String command) {    	
    	return command;
    }
    
}
