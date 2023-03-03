package src;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainBoard extends JFrame implements ActionListener {
    JPanel jpanel = new JPanel();
    JButton btn_reset = new JButton("GAME RESET");
    GameBoard gameBoard = new GameBoard();
    String menu = "";
    static JLabel bScore = new JLabel("BLACK: 2");
    static JLabel wScore = new JLabel("WHITE:  2");
    static JLabel turn = new JLabel("  TURN: BLACK");


    public mainBoard(){
        setTitle("HELLO OTHELLO:  " + Login.userT.getText());
        setSize(700, 780);
        setLayout(null);

        jpanel.setBounds(0, 0, 700, 80);
        jpanel.setLayout(null);
        btn_reset.setBounds(20,25,120, 30);
        btn_reset.setBackground(new Color(169, 157, 145, 255));
        bScore.setBounds(170,10,80,30);
        bScore.setBorder(new LineBorder(Color.BLACK, 5));
        wScore.setBounds(170,40,80,30);
        wScore.setBorder(new LineBorder(Color.WHITE, 5));
        turn.setBounds(280,10,95,60);
        mainBoard.turn.setOpaque(true);
        mainBoard.turn.setBackground(Color.BLACK);
        mainBoard.turn.setForeground(Color.WHITE);
        jpanel.add(btn_reset);
        jpanel.add(bScore);
        jpanel.add(wScore);
        jpanel.add(turn);
        add(jpanel);
        gameBoard.setLocation(20,80);
        add(gameBoard);

        btn_reset.addActionListener(this);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        menu = e.getActionCommand();
        System.out.println(menu);
        if(menu.equals("GAME RESET")){
            for(int i = 0; i<8; i++){
                for(int j = 0; j<8; j++){
                    gameBoard.board[i][j] = 0;
                    if (((i==3)&&(j==3))||((i==4)&&(j==4))) gameBoard.board [i][j] = -1;
                    if (((i==4)&&(j==3))||((i==3)&&(j==4))) gameBoard.board [i][j] = 1;
                }
            }
            gameBoard.repaint();
            Check.ccount = 1;
        }
    }
}
