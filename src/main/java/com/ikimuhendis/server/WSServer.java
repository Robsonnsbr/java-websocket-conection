package com.ikimuhendis.server;

//import com.google.gson.Gson;
//import com.ikimuhendis.example.WebSocketServer;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.ikimuhendis.example.WebSocketServer;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat")
public class WSServer {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    //private static final Gson gson = new Gson();
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            for (Session sess : sessions) {
                if (sess.isOpen()) {
                    sess.getBasicRemote().sendText(msg); // Aqui está o comando para o equipamento.
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        sessions.remove(session);
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.info("ERROR");
        logger.info("SessionId " + session.getId());
        logger.info("ErrorMsg " + error.getMessage());
    }
    
    
    public void sendMessageToAll() throws IOException { //parei aqui.
    	WebSocketServer command = new WebSocketServer();
    	WebSocketServer.sendCommand(null);
        for (Session session : sessions) {
        	System.out.println("Entrei");
            if (session.isOpen()) {
                try {
					session.getBasicRemote().sendObject(command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EncodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }

}
