import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080; // задаем номер порта для подключения сервера
    private static ServerSocket serverSocket; // сервер-сокет
    private static Socket clientSocket; // сокет для общения
    private static PrintWriter out; // поток для записи в сокет
    private static BufferedReader in; // поток для чтения из сокета

    public static void main(String[] args) {
        try {
            try {
                serverSocket = new ServerSocket(PORT); // сервер-сокет подключаем к порту
                clientSocket = serverSocket.accept(); // ждем подключения
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.
                            getInputStream())); // создаем поток ввода для приема сообщений
                    out = new PrintWriter(clientSocket.
                            getOutputStream(), true); // создаем поток вывода для отправки сообщений
                    System.out.println("New connection accepted"); // подтверждаем установление соединения
                    String name = in.readLine(); // читаем сообщение от клиента
                    out.println(String.format("Hi %s, your port is %d", name,
                            clientSocket.getPort())); // выводми на экран сообщение и номер порта
                } finally {
                    clientSocket.close(); // закрываем сокет
                    in.close(); // закрываем поток ввода
                    out.close(); // закрываем поток вывода
                }
            } finally {
                serverSocket.close(); // закрываем сервер
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
