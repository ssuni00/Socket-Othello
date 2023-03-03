package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    JPanel jpanel = new JPanel();
    JLabel userL = new JLabel("USER NAME", JLabel.CENTER);
    static JTextField userT = new JTextField(10);
    JButton btn_start = new JButton("START");

    static mainBoard mainboard;

    public Login(){
        setTitle("LOGIN FOR THE NEW GAME");
        setSize(400,100);
        setLayout(null);

        jpanel.setBounds(50,10,300,100);
        userL.setBounds(100,100,100,20);
        userT.setBounds(100,100,100,20);
        btn_start.setBounds(100,300,100,60);
        jpanel.add(userL);
        jpanel.add(userT);
        jpanel.add(btn_start);

        btn_start.addActionListener(this);
        add(jpanel);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String b = e.getActionCommand();

        if(b.equals("START")){
            System.out.println("START");
            mainboard = new mainBoard();
            setVisible(false);
        }
    }
}
