import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static final String HOST = "netology.homework"; // задаем локальный хост
    private static final int PORT = 8080; // задаем номер порта для подключения к серверу
    private static Socket clientSocket; // сокет для общения
    private static PrintWriter out; // поток для записи в сокет
    private static BufferedReader in; // поток для чтения из сокета
    private static int messages = Server.messages; // количество сообщений

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket(HOST, PORT); // запрашиваем у сервера доступ на соединение
                out = new PrintWriter(clientSocket.
                        getOutputStream(), true); // создаем поток вывода для отправки сообщений
                in = new BufferedReader(new InputStreamReader(clientSocket.
                        getInputStream())); // создаем поток ввода для приема сообщений
                Scanner scanner = new Scanner(System.in);
                for (int i = 0 ; i < messages ; i++) {
                    String serverMessage = in.readLine(); // читаем сообщение с сервера
                    System.out.println("Сервер сообщает:\n" + serverMessage);
                    if (i < 2) {
                        System.out.println("Наш ответ:");
                        String clientMessage = scanner.nextLine(); // читаем имя из консоли
                        out.println(clientMessage); // отправляем сообщение на сервер
                    }
                }
            } finally {
                clientSocket.close(); // закрываем сокет
                in.close(); // закрываем поток ввода
                out.close(); // закрываем поток вывода
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
