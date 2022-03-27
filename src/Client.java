import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final String HOST = "localhost"; // задаем локальный хост
    private static final int PORT = 8080; // задаем номер порта для подключения к серверу
    private static Socket clientSocket; // сокет для общения
    private static PrintWriter out; // поток для записи в сокет
    private static BufferedReader in; // поток для чтения из сокета

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket(HOST, PORT); // запрашиваем у сервера доступ на соединение
                out = new PrintWriter(clientSocket.
                        getOutputStream(), true); // создаем поток вывода для отправки сообщений
                in = new BufferedReader(new InputStreamReader(clientSocket.
                        getInputStream())); // создаем поток ввода для приема сообщений
                System.out.println("Введите Ваше имя:");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine(); // читаем имя из консоли
                out.println(name); // отправляем сообщение на сервер
                String serverMessage = in.readLine(); // читаем сообщение с сервера
                System.out.println("Сервер сообщает:" + serverMessage);
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
