package src;
import src.Login;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    static Login login;
    static GameBoard gameboard;
    static Check check;
    static int turn;

    public static void main(String args[]){
        if(args.length != 1){
            System.out.println("USAGE: java Server 대화명");
            System.exit(0);
        }

        try {
            String serverIP = "127.0.0.1";
            // 소켓생성 -> 연결요청
            Socket socket = new Socket(serverIP,525);
            System.out.println("서버에 연결되었습니다");

            login = new Login();

            Thread sender= new Thread(new ClientSender(socket, args[0]));
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();
        } catch(ConnectException ce){
            ce.printStackTrace();
        }catch(Exception e){
            throw new RuntimeException();
        }
    }//main

    static class ClientSender extends Thread{
        Socket socket;
        public static DataOutputStream out;
        String name;

        ClientSender(Socket socket, String name){
            this.socket = socket;
            try{
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            }catch(Exception e){}
        }

        public void run(){
            Scanner scanner = new Scanner(System.in);
            try{
                    if(out!=null){
                      out.writeUTF(name);
                    }
                    while(out!=null) {
                        out.writeUTF("[" + name + "]" + scanner.nextLine());
                    }
            }catch(IOException e){}
        }// run()
    }//ClientSender

    static class ClientReceiver extends Thread{
        Socket socket;
        DataInputStream in;

        ClientReceiver(Socket socket){
            this.socket = socket;
            try{
                in = new DataInputStream(socket.getInputStream());
            }catch(IOException e){}
        }

        public void run(){
            while (in!=null){
                try{
                    String color =in.readUTF();
                    System.out.println(color);

                    if(color.equals("#black")||color.equals("#white")){
                        if(color.equals("#black")) {
                            turn = 0;
                        }else {
                            turn = 1;
                        }
                        continue;
                    }


                    if(!(color.charAt(0)=='0'||color.charAt(0)=='1'||color.charAt(0)=='-')) continue;

                    login.mainboard.gameBoard.receiveBoardData(color);
                    Check.ccount++;

                    int black_count =0;
                    int white_count=0;
                    int win_count=0;

                    if(Check.ccount%2 !=0) {
                        mainBoard.turn.setText("  TURN: BLACK");
                        mainBoard.turn.setOpaque(true);
                        mainBoard.turn.setBackground(Color.BLACK);
                        mainBoard.turn.setForeground(Color.WHITE);
                    } else if(Check.ccount%2 ==0) {
                        mainBoard.turn.setText("  TURN: WHITE");
                        mainBoard.turn.setOpaque(true);
                        mainBoard.turn.setBackground(Color.WHITE);
                        mainBoard.turn.setForeground(Color.BLACK);
                    }
                    for(int i = 0; i<8; i++){
                        for(int j = 0; j<8; j++){
                            if ( login.mainboard.gameBoard.board[i][j] == 1) black_count++;
                            else  if ( login.mainboard.gameBoard.board[i][j] == -1) white_count++;
                            else if ( login.mainboard.gameBoard.board[i][j] == 0) win_count ++;
                        }
                    }

                    mainBoard.bScore.setText("BLACK: "+ black_count);
                    mainBoard.wScore.setText("WHITE:  "+ white_count);
                    if (win_count == 0){
                        if(black_count>white_count)
                            JOptionPane.showMessageDialog(null, "🎇🎉🎊✨🎆BLACK WIN🎇🎉🎊✨🎆");
                        else if(black_count<white_count)
                            JOptionPane.showMessageDialog(null,"🎇🎉🎊✨🎆WHITE WIN🎇🎉🎊✨🎆");
                        else
                            JOptionPane.showMessageDialog(null, "🎇🎉🎊✨🎆BOTH WIN🎇🎉🎊✨🎆");
                    }
                    mainBoard.bScore.setText("BLACK: "+ black_count);
                    mainBoard.wScore.setText("WHITE:  "+ white_count);
                    login.mainboard.gameBoard.repaint();

                }catch(IOException e){}
            }
        }//run
    }//Client Receiver
}//class