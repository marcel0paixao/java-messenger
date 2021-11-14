/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.java.messenger;

import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author marcel0paixao
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println("Client '" + clientHandler.getClientEmail() + "' has connected");
            }
        } catch (Exception e) {
            closeServerSocket();
        }
    }
    private void closeServerSocket() {
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
