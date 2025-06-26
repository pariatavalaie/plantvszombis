package com.example.plantvszombie;

import java.io.*;
import java.net.*;

public class GameClient {
    private ObjectOutputStream out;

    public interface StateListener {
        void onState(GameState s);
    }

    public GameClient(String host, int port, StateListener listener) throws IOException {
        Socket socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // دریافت کننده
        new Thread(() -> {
            try {
                while (true) {
                    GameState state = (GameState) in.readObject();
                    listener.onState(state);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendState(GameState state) {
        try {
            out.writeObject(state);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
