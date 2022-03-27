import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    private static final int PORT = 8080; // задаем номер порта для подключения сервера
    private static ServerSocket serverSocket; // сервер-сокет
    private static Socket clientSocket; // сокет для общения
    private static PrintWriter out; // поток для записи в сокет
    private static BufferedReader in; // поток для чтения из сокета
    private static String clientName; // переменная для записи имени клиента
    private static List<String> serverMessages = new ArrayList<>(Arrays.asList( // список фраз сервера
            "Write your name!",
            "Are you child (yes/no)?",
            "Welcome to the kids area, %username%! Let's play!",
            "Welcome to the adult zone, %username%! Have a good rest, or a good working day!"));
    static int messages = serverMessages.size() - 1; // количество сообщений
    private static List<String> clientMessages = new ArrayList<>(); // список фраз клиента

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
                    for (int i = 0 ; i < messages ; i++) {
                        String serverMessage;
                        String clientMessage;
                        if (i > 1) {
                            if ("no".equals(clientMessages.get(1))) {
                                serverMessage = serverMessages.get(i + 1).replace("%username%", clientName);
                            } else {
                                serverMessage = serverMessages.get(i).replace("%username%", clientName);
                            }
                        } else {
                            serverMessage = serverMessages.get(i);
                        }
                        out.println(serverMessage); // направляем сообщение клиенту
                        System.out.println("Я (сервер) сообщил:\n" + serverMessage);
                        if (i < 2) {
                            clientMessage = in.readLine(); // читаем сообщение от клиента
                            System.out.println("Клиент ответил: \n" + clientMessage);
                            clientMessages.add(clientMessage); // сохраняем ответ клиента
                            if (i == 0) {
                                clientName = clientMessage; // сохраняем имя клиента
                            }
                        }
                    }
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
