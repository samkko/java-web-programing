/**
 * Created by hs kim on 2020/01/13
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(8080)) {
            System.out.println("Http Server started at 8080 port");
            while (true) {
                try (Socket socket = listener.accept()) {
                    System.out.printf("New Client Connect! Connected IP : %s, Port : %d\n",
                            socket.getInetAddress(), socket.getPort());

                    InputStream in = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    //Request Header 확인
                    System.out.println(br.readLine());

                    OutputStream out = socket.getOutputStream();

                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = "Hello World".getBytes();

                    dos.writeBytes("HTTP/1.1 200 OK \r\n");
                    dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                    dos.writeBytes("Content-Length: " + body.length + "\r\n");
                    dos.writeBytes("\r\n");

                    dos.write(body, 0, body.length);
                    dos.writeBytes("\r\n");
                    dos.flush();
                }
            }
        }
    }
}
