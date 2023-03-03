import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Server{
    HashMap clients;

    Server(){
        clients = new HashMap();
        Collections.synchronizedMap(clients); // hashmap이 들어가는 순서대로 정렬
    }

    public void start(){
        ServerSocket serverSocket = null;
        Socket socket = null;

        try{
            serverSocket = new ServerSocket(525);
            System.out.println("서버가 시작되었습니다");

            while(true){ //계속 client가 socket을 보내는지 아닌지를 확인
                socket = serverSocket.accept(); // client에서 하나씩 오는걸 대기하겠다.
                System.out.println("[" + socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속하였습니다"); // socket의 address+port보여줌
                ServerReceiver thread = new ServerReceiver(socket); // 소켓을 가지고 서버가 받는 것을 thread해줌
                thread.start(); // thread 실행 -> run으로 감
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void sendToAll(String msg){
        Iterator it = clients.keySet().iterator(); // iterator : pointer같은것
        while(it.hasNext()){ //다음이 있을때까지 (client가 나갔는지)
            try{
                DataOutputStream out = (DataOutputStream) clients.get(it.next()); //output저장한 걸 이름별로 가져옴
                out.writeUTF(msg); // message를 outputstream에 써준다. (콘솔아님!!)
            }catch(IOException e){}
        }//while
    }

    public static void main (String args[]) {
        new Server().start();
    }

    class ServerReceiver extends Thread { // 여기서 thread를 하겠다
        Socket socket;
        DataInputStream in; // 들어오는 것을 받고
        DataOutputStream out; // 내보내주고

        ServerReceiver(Socket socket){
            this.socket = socket; // 받은 소켓을 넣어주고
            try{
                in = new DataInputStream(socket.getInputStream()); // 받은 input을 넣어주고
                out = new DataOutputStream(socket.getOutputStream()); // 
            }catch(IOException e){}
        }

        public void run(){ // thread를 start하면 무조건 run으로 옴
            String name = "";
            try{
                name = in.readUTF(); // 지금 들어와있는 것을 read해서 name에 저장
                sendToAll("#"+name+"님이 들어오셨습니다");

                // 처음들어올때 흑돌, 두번째 들어오면 백돌
                if(clients.size()==0) {
                    System.out.println("black 보냄");
                    out.writeUTF("#black");
                } else if(clients.size()==1) {
                    System.out.println("white 보냄");
                    out.writeUTF("#white");
                }


                clients.put(name,out); //hashmap client에 name이랑 outputstream을 넣어줌
                System.out.println("현재 서버 접속자 수는"+clients.size()+"입니다."); // client에는 이름과 output갖고있음

                while(in!=null){ // null이 아닐때까지 계속 받아들인다
                    sendToAll(in.readUTF());
                }
            }catch(IOException e){
            }finally{ // client가 나가면 finally로 감
                sendToAll("#"+name+"님이 나가셨습니다");
                clients.remove(name);
                System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속을 종료하였습니다");
                System.out.println("현재 서버 접속자 수는"+clients.size()+"입니다");
            }//try
        }//run
    }//receiverThread
}//class

        
