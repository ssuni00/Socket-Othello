package src;

import javax.swing.*;
import java.awt.*;

public class Check {
    int check_board[][] = new int[8][8];
    static int ccount = 1;
    static boolean[] direcCheck = new boolean[8]; // 8방향 boolean 체크
    static boolean check = false;
    static int turn;
    static int bCount;
    static int wCount;
    int check_win;

    Check(int[][] pboard, int x, int y) {
        check_board = pboard;
//        int cx = x;
//        int cy = y;

        if (check_board[x / 80][y / 80] == 0) {
            if (ccount % 2 != 0) { // 홀수면 검
                check_board[x / 80][y / 80] = 1;
            } else if (ccount % 2 == 0) { // 짝수면 백
                check_board[x / 80][y / 80] = -1;
            }
        }

        // 방향체크 boolean. 8방향 체크할건데 어느방향으로 추적해야할 지 true/false 값 가져옴
        direcCheck[0] = up(x / 80, y / 80);
        direcCheck[1] = up_left(x / 80, y / 80);
        direcCheck[2] = left(x / 80, y / 80);
        direcCheck[3] = down_left(x / 80, y / 80);
        direcCheck[4] = down(x / 80, y / 80);
        direcCheck[5] = down_right(x / 80, y / 80);
        direcCheck[6] = right(x / 80, y / 80);
        direcCheck[7] = up_right(x / 80, y / 80);

        check_board[x / 80][y / 80] = 0;


        for(int c=0; c<8; c++){
            if(direcCheck[c]) 
                check = true; // 하나라도 true면 check를 true 로
            System.out.println(direcCheck[c]);
        }

        if(check){
            if (ccount % 2 != 0) { // 흑이 둘 차례면
                check_board[x/80][y/80] = 1; // 흑을 놓고
            }else{ // 아니면
                check_board[x/80][y/80] = -1; // 백을 놓고
            }
//            ccount++;
            turn = check_board[x/80][y/80];
        }
        else return;
        System.out.println("놓을 수 있는지: " + check + "  다음 차례 돌 (홀:흑, 짝:백) : "+ ccount);


        if(direcCheck[0] == true) change_up(x/80,y/80);
        if(direcCheck[1] == true) change_up_left(x/80,y/80);
        if(direcCheck[2] == true) change_left(x/80,y/80);
        if(direcCheck[3] == true) change_down_left(x/80,y/80);
        if(direcCheck[4] == true) change_down(x/80,y/80);
        if(direcCheck[5] == true) change_down_right(x/80,y/80);
        if(direcCheck[6] == true) change_right(x/80,y/80);
        if(direcCheck[7] == true) change_up_right(x/80,y/80);

       bCount = 0;
       wCount = 0;
       check_win = 0;
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if (check_board[i][j] == 1) bCount++;
                else if (check_board[i][j] == -1) wCount++;
                else if (check_board[i][j] == 0) check_win ++;
            }
        }
        mainBoard.bScore.setText("BLACK: "+ bCount);
        mainBoard.wScore.setText("WHITE:  "+ wCount);
        if(ccount%2 !=0) {
            mainBoard.turn.setText("  TURN: BLACK");
            mainBoard.turn.setOpaque(true);
            mainBoard.turn.setBackground(Color.BLACK);
            mainBoard.turn.setForeground(Color.WHITE);
        } else if(ccount%2 ==0) {
            mainBoard.turn.setText("  TURN: WHITE");
            mainBoard.turn.setOpaque(true);
            mainBoard.turn.setBackground(Color.WHITE);
            mainBoard.turn.setForeground(Color.BLACK);
        }

        if (check_win == 0){
            if(bCount>wCount)
                JOptionPane.showMessageDialog(null, "🎇🎉🎊✨🎆BLACK WIN🎇🎉🎊✨🎆");
            else if(bCount<wCount)
                JOptionPane.showMessageDialog(null,"🎇🎉🎊✨🎆WHITE WIN🎇🎉🎊✨🎆");
            else
                JOptionPane.showMessageDialog(null, "🎇🎉🎊✨🎆BOTH WIN🎇🎉🎊✨🎆");
        }
        Client.login.mainboard.gameBoard.sendBoardData(Client.login.mainboard.gameBoard.board);

    }
    
    // 돌의 방향을 확인해주고 true / false 를 리턴해주는 함수들
    public boolean up(int x, int y){
        if(y==0) return false;
        if ((check_board[x][y-1] != check_board[x][y]) &&(check_board[x][y-1] != 0)){ // 색이 다르고 0이 아니면
            for(int i =y-1; i>=0; i--){
                if(check_board[x][i] == 0){
                    return false;
                }else  if (check_board[x][i] == check_board[x][y]){ // 두 돌이 같으면
                        return true;
                    }
                }
            }
            return false;
    } // x,y 기준 바로 위 체크
    public boolean up_left(int x, int y){
        if((x==0)||(y==0)) return false;
        int i, j;
        if ((check_board[x-1][y-1] != check_board[x][y]) &&(check_board[x-1][y-1] != 0)){ // 색이 다르고 0이 아니면
            for(i =x-1, j= y-1; i>=0&&j>=0; i--,j--){
                if((check_board[i][j] == 0)){
                    return false;
                }else if (check_board[i][j] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                    }
                }
            }
        return false;
    } // x,y 기준 위, 왼쪽 대각선 체크
    public boolean left(int x, int y){
        if(x==0) return false;
        if ((check_board[x-1][y] != check_board[x][y]) &&(check_board[x-1][y] != 0)){ // 색이 다르고 0이 아니면
            for(int i =x-1; i>=0; i--){
                if(check_board[i][y] == 0){
                    return false;
                }else  if (check_board[i][y] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                }
            }
        }
        return false;
    }  // x,y 기준 왼쪽 체크
    public boolean down_left(int x, int y){
        if((x==0)||(y==7)) return false;
        int i, j;
        if ((check_board[x-1][y+1] != check_board[x][y]) &&(check_board[x-1][y+1] != 0)){ // 색이 다르고 0이 아니면
            for(i =x-1, j= y+1; i>=0&&j<8; i--,j++){
                if((check_board[i][j] == 0)){
                    return false;
                }else if (check_board[i][j] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                }
            }
        }
        return false;
    } // x,y 기준 아래, 왼쪽 대각선 체크
    public boolean down(int x, int y){
        if(y==7) return false;
        if ((check_board[x][y+1] != check_board[x][y]) &&(check_board[x][y+1] != 0)){ // 색이 다르고 0이 아니면
            for(int i =y+1; i<8; i++){
                if(check_board[x][i] == 0){
                    return false;
                }else  if (check_board[x][i] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                }
            }
        }
        return false;
    } // x,y 기준 바로 아래 체크
    public boolean down_right(int x, int y){
        if((x==7)||(y==7)) return false;
        int i, j;
        if ((check_board[x+1][y+1] != check_board[x][y]) &&(check_board[x+1][y+1] != 0)){ // 색이 다르고 0이 아니면
            for(i =x+1, j= y+1; i<8&&j<8; i++,j++){
                if((check_board[i][j] == 0)){
                    return false;
                }else if (check_board[i][j] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                }
            }
        }
        return false;
    } // x,y 기준 위, 오른쪽 대각선 체크
    public boolean right(int x, int y){
        if(x==7) return false;
        if ((check_board[x+1][y] != check_board[x][y]) &&(check_board[x+1][y] != 0)){ // 색이 다르고 0이 아니면
            for(int i =x+1; i<8; i++){
                if(check_board[i][y] == 0){
                    return false;
                }else  if (check_board[i][y] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                }
            }
        }
        return false;
    } // x,y 기준 오른쪽 체크
    public boolean up_right(int x, int y){
        if((x==7)||(y==0)) return false;
        int i, j;
        if ((check_board[x+1][y-1] != check_board[x][y]) &&(check_board[x+1][y-1] != 0)){ // 색이 다르고 0이 아니면
            for(i =x+1, j= y-1; i<8&&j>=0; i++,j--){
                if((check_board[i][j] == 0)){
                    return false;
                }else if (check_board[i][j] == check_board[x][y]){ // 두 돌이 같으면
                    return true;
                }
            }
        }
        return false;
    } // x,y 기준 위, 오른쪽 대각선 체크

    // 돌의 색을 바꾸어주는 함수들
    public void change_up(int x, int y){
        for(int i =y-1; i>=0; i--){
            if(check_board[x][i]==turn) break;
            else if(check_board[x][i] == (-1)*turn){
                check_board[x][i] = (-1)*check_board[x][i];
            }
        }
    }
    public void change_up_left(int x, int y){
        for(int i =x-1, j= y-1; i>=0&&j>=0; i--,j--) {
            if(check_board[i][j]==turn) break;
            else if(check_board[i][j] == (-1*turn)){
                check_board[i][j]= (-1)*check_board[i][j];
            }
        }
    }
    public void change_left(int x, int y){
        for(int i =x-1; i>=0; i--) {
            if(check_board[i][y]==turn) break;
            else if (check_board[i][y] == (-1)*turn) {
                check_board[i][y] = (-1) * check_board[i][y];
            }
        }
    }
    public void change_down_left(int x, int y){
        for(int i =x-1, j= y+1; i>=0&&j<8; i--,j++){
            if(check_board[i][j]==turn) break;
            else if (check_board[i][j] == (-1)*turn) {
                check_board[i][j] = (-1) * check_board[i][j];
            }
        }
    }
    public void change_down(int x, int y){
        for(int i =y+1; i<8; i++) {
            if(check_board[x][i]==turn) break;
            else if (check_board[x][i] == (-1)*turn) {
                check_board[x][i] = (-1) * check_board[x][i];
            }
        }
    }
    public void change_down_right(int x, int y){
        for(int i =x+1, j= y+1; i<8&&j<8; i++,j++) {
            if(check_board[i][j]==turn) break;
            else if (check_board[i][j] == (-1)*turn) {
                check_board[i][j] = (-1) * check_board[i][j];
            }
        }
    }
    public void change_right(int x, int y) {
        for (int i = x + 1; i < 8; i++) {
            if(check_board[i][y]==turn) break;
            else if(check_board[i][y]==(-1)*turn){
                check_board[i][y] = (-1) * check_board[i][y];
            }
        }
    }
    public void change_up_right(int x, int y) {
        for(int i =x+1, j= y-1; i<8&&j>=0; i++,j--) {
            if(check_board[i][j]==turn) break;
            else if (check_board[i][j] == (-1) * turn) {
                check_board[i][j] = (-1) * check_board[i][j];
            }
        }
    }

    public boolean passCheck(int x, int y) {
        turn = (-1) * turn;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(check_board[i][j]==0){
                    if (ccount % 2 != 0) { // 홀수면 검
                        check_board[i][j] = 1;
                    } else if (ccount % 2 == 0) { // 짝수면 백
                        check_board[i][j] = -1;
                    }
                    direcCheck[0] = up(i,j);
                    direcCheck[1] = up_left(i,j);
                    direcCheck[2] = left(i,j);
                    direcCheck[3] = down_left(i,j);
                    direcCheck[4] = down(i,j);
                    direcCheck[5] = down_right(i,j);
                    direcCheck[6] = right(i,j);
                    direcCheck[7] = up_right(i,j);
                    check_board[i][j] = 0;
                    for(int k =0;k<8;k++){
                        if(direcCheck[k] == true){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public void score(){
        mainBoard.bScore.setText("BLACK: "+ bCount);
        mainBoard.wScore.setText("WHITE:  "+ wCount);
        if(ccount%2 !=0) {
            mainBoard.turn.setText("  TURN: BLACK");
            mainBoard.turn.setOpaque(true);
            mainBoard.turn.setBackground(Color.BLACK);
            mainBoard.turn.setForeground(Color.WHITE);
        } else if(ccount%2 ==0) {
            mainBoard.turn.setText("  TURN: WHITE");
            mainBoard.turn.setOpaque(true);
            mainBoard.turn.setBackground(Color.WHITE);
            mainBoard.turn.setForeground(Color.BLACK);
        }
    }

}
