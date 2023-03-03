package src;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static src.Client.check;

public class GameBoard extends JPanel implements MouseListener {
    Point clickedP = new Point(0,0);
    int board[][] = new int[8][8];
    int x,y;
    Client client;
    Graphics2D g2;

    GameBoard(){
        setSize(640,640);
        setBorder(new LineBorder(new Color(91, 78, 75), 4));
        setBackground(new Color(218, 181, 144));
        addMouseListener(this);

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                board[i][j] = 0;
                if (((i==3)&&(j==3))||((i==4)&&(j==4))) board [i][j] = -1;
                if (((i==4)&&(j==3))||((i==3)&&(j==4)))board [i][j] = 1;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        g2 = (Graphics2D) g;

        int width = 640;
        int height = 640;
        int side = width/8;

        for(int i = 0; i<9; i++){
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(0,80*i, width, 80*i);
            g2.drawLine(80*i,0, 80*i, height);
        }
        // 배열에 있는 0, -1 ,1 보고 돌 그려줌(0은 null 1은 검 -1은 백)
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if (board [i][j] == 1){
                    g2.setColor(Color.black);
                    g2.fillOval(i*80, j*80, side, side);
                }else if (board [i][j] == -1){
                    g2.setColor(Color.white);
                    g2.fillOval(i*80, j*80, side, side);
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("turn: "+ Client.turn+ "  ccount: "+ Check.ccount);
        // 시작때 주어진 turn(0:흑 / 1:백)이 ccount%2 와 같으면 (0:백 / 1:흑) => 놓으려는 돌이 다르고
        if(Check.ccount%2 == Client.turn) {
            if (Check.ccount % 2 == 0) { // 놓을 돌이 백이면
                JOptionPane.showMessageDialog(null, "NOT YOUR TURN. WHITE TURN"); // white 턴
            }else {
                JOptionPane.showMessageDialog(null, "NOT YOUR TURN. BLACK TURN"); // black 턴
            }
            return;
        }

        clickedP = e.getPoint();
        x = e.getX();
        y = e.getY();
        if (board[x/80][y/80] != 0) // 더블클릭할때 돌이 사라지는 것을 방지
            return;
        Check check = new Check(board,x,y);
        repaint();

        if(check.passCheck(x,y) == false) {
            check.ccount++;
            check.turn = (-1) * check.turn;
            System.out.println("PASS");
            check.score();
            JOptionPane.showMessageDialog(null, "NO MORE TO MOVE");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    public void sendBoardData(int [][] pboard){
        String send = "";

        for(int i = 0; i< 8; i++){
            for(int j =0; j<8; j++){
                send+= pboard[i][j]+",";
            }
            send=send.substring(0,send.length()-1);
            send+=".";
        }
        send=send.substring(0,send.length()-1);

        if(Client.turn == 1) {
            mainBoard.turn.setText("  TURN: BLACK");
            mainBoard.turn.setOpaque(true);
            mainBoard.turn.setBackground(Color.BLACK);
            mainBoard.turn.setForeground(Color.WHITE);
        } else if(Client.turn == 0) {
            mainBoard.turn.setText("  TURN: WHITE");
            mainBoard.turn.setOpaque(true);
            mainBoard.turn.setBackground(Color.WHITE);
            mainBoard.turn.setForeground(Color.BLACK);
        }

        try{
            Client.ClientSender.out.writeUTF(send);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void receiveBoardData(String s){
        String[] receive =s.split("\\.");
        for(int i =0; i<8;i++){
            String[] nums = receive[i].split(","); // "." 을 기준으로 자른걸 "," 기준으로 또 자르기
            for(int j=0; j<8;j++){
                int num = Integer.parseInt(nums[j]); // num[j]의 index를 integer 타입으로 변환
                board[i][j]=num; // 변환해서 하나씩 넣어주기
            }

        }
    }

}
