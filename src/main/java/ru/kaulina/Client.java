package ru.kaulina;
import java.io.*;
import java.net.Socket;

public class Client {
    public static final String HOST = "netology.homework";

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, Server.PORT);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {

            // проверяем живой ли канал и работаем если живой
            while (!socket.isOutputShutdown()) {
                // получаем вопрос
                System.out.println(ois.readUTF());
                String clientCommand = br.readLine();
                // пишем данные с консоли в канал сокета для сервера
                oos.writeUTF(clientCommand);
                oos.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}