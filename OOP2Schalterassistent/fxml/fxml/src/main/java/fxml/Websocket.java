package fxml;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class Websocket {
    private WebSocketClient client;

    public Websocket(Standort standort) {
        URI uri = URI.create(standort.getWebSocketUrl());
        client = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to WebSocket server");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received message from WebSocket server: " + message);
                List<String> antwortliste = Arrays.asList(message.split("\n"));
                QRSchweiz.getParameterFromWebsocketList(antwortliste);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Disconnected from WebSocket server");
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("Error occurred in WebSocket connection: " + ex.getMessage());
            }
        };
    }

    public void connect() {
        client.connect();
    }

    public void disconnect() {
        client.close();
    }

    public void sendMessage(String message) {
        if (client.isOpen()) {
            client.send(message);
        }
    }
}
