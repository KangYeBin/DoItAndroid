package org.techtown.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText input1;
    Button sendButton, startServerButton;
    TextView output1;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.input1);
        output1 = findViewById(R.id.output1);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = input1.getText().toString();

                //네트워킹은 스레드를 사용해야하고(UI 업데이트가 필요하다면 핸들러 이용)
                //인터넷 권한이 필요하다
                new Thread(new Runnable() { //스레드 상속 없이 바로 스레드 객체 생성 가능
                    @Override
                    public void run() {
                        send(data);     //소켓을 통해서 서버쪽으로 전송하는 함수
                    }
                }).start();     //run 메소드 바로 실행
            }
        });

        startServerButton = findViewById(R.id.startServerButton);
        startServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();
            }
        });

    }

    public void send(String data) { //이더넷의 포트번호를 지정해서 데이터를 주고받는다
        int port = 5001;
        try {
            //서버로 응답 보내기
            Socket socket = new Socket("localhost", port);  //보내려는 ip와 port번호
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());   //바이트어레이로 전송하기 위해
            outputStream.writeObject(data);
            outputStream.flush();   //버퍼에 남아있는 것을 전부 출력

            //서버에서 응답 받기
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String input = inputStream.readObject().toString();
            socket.close();     //사용이 끝나면 꼭 close

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        int port = 5001;
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();  //대기하다가 실행
                InetAddress localHost = socket.getLocalAddress();
                int clientPort = socket.getLocalPort();
                println("클라이언트 연결됨 : " + localHost + ", " + clientPort);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String input = inputStream.readObject().toString();
                println("데이터 받음 : " + input);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(input + "from server.");
                outputStream.flush();
                println("데이터 보냄");
                socket.close();
            }



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void println(String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                output1.append(data + "\n");
            }
        });
    }
}