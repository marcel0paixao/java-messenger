package com.mycompany.java.messenger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String email;
    private String pass;

    public Client(Socket socket, String email, String pass){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.email = email;
            this.pass = pass;
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(email);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(email + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String groupChatMessage;
                while (socket.isConnected()) {
                    try {
                        groupChatMessage = bufferedReader.readLine();
                        System.out.println(groupChatMessage);
                    } catch (Exception e) {
                        if (e.getMessage() == "Connection reset") {
                            System.out.println("Connection has been lost, please try again later");
                            closeEverything(socket, bufferedReader, bufferedWriter);
                            System.exit(0);
                        }
                    }
                }
            }
            
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try{
            if (bufferedReader != null || bufferedWriter != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String pass = "";
        String ip = "localhost";
        Integer port = 1234;
        System.out.println("=========== Select ===========");
        System.out.println("1 - Login \n2 - Change IP & Port and login\nElse - Exit");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                scanner.nextLine();
                System.out.println("Email to enter de group chat: ");
                email = scanner.nextLine();
                System.out.println("Password to enter de group chat: ");
                pass = scanner.nextLine();
                break;
            case 2:
                scanner.nextLine();
                System.out.println("Enter the ip and port:");
                ip = scanner.nextLine();
                System.out.println("Enter the port:");
                port = scanner.nextInt();
                System.out.println("Email to enter de group chat: ");
                email = scanner.nextLine();
                System.out.println("Password to enter de group chat: ");
                pass = scanner.nextLine();
                break;
            default:
                System.exit(0);
                break;
        }
        System.out.println("==============================");
        try {
            Socket socket = new Socket(ip, port);
            Client clientConn = new Client(socket, email, pass);
        
            clientConn.listenForMessage();
            clientConn.sendMessage();
        } catch (Exception e) {
            if (e.getMessage().equals("Connection refused: connect")) {
                System.out.println("The connection could not be established, please try again later.");
            }
            else{
                System.out.println(e);
            }
        }
    }
    
}
