package ru.kaulina;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    public static final Integer PORT = 8080;
    public static Queue<String> question;

    public static void main(String[] args) {
        question = new LinkedList<>();
        question.add("Напишите свое имя русскими буквами: ");
        question.add("Проверка на робота.Напиши 378 ");
        question.add("Укажи: ты мужчина (мальчик) - М или женщина (девочка) - Ж ?");
        question.add("Подтверди, что тебе больше 18 лет?");

        System.out.println("Server starting");

        try (ServerSocket server = new ServerSocket(PORT);
             Socket client = server.accept();
             // каналы записи и чтения в сокет
             DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())
        ){

            out.writeUTF(question.poll());
            out.flush();

            ArrayList<String> ansver = new ArrayList<>();

            while (!client.isClosed()) {
                System.out.println("Server написал клиенту");
                // сервер ждёт  получения данных клиента
                String entry = in.readUTF();
                ansver.add(entry);

                if (entry.equalsIgnoreCase("Нет") || entry.equalsIgnoreCase("нет")) {
                    out.writeUTF("ок " + ansver.get(0) + ", добро пожаловать на детский сайт\n приятного просмотра");
                    out.flush();
                } else if (entry.equalsIgnoreCase("Да") || entry.equalsIgnoreCase("да")) {
                    out.writeUTF("ок " + ansver.get(0) + ", добро пожаловать на взрослый сайт\n хорошего дня!");
                    out.flush();
                } else if (entry.equalsIgnoreCase("М") || entry.equalsIgnoreCase("м")) {
                    out.writeUTF("Отлично " + ansver.get(0) + ", просмотрите эту ссылу '1111111'\n "
                            + ansver.get(0) + " " + question.poll());
                    out.flush();
                } else if (entry.equalsIgnoreCase("Ж") || entry.equalsIgnoreCase("ж")) {
                    out.writeUTF("Отлично  " + ansver.get(0) + ", просмотрите эту ссылу '8888888'\n"
                            + ansver.get(0) + " " + question.poll());
                    out.flush();
                } else if (entry.equalsIgnoreCase("777")) {
                    out.writeUTF("Отлично  " + ansver.get(0) + ", вы человек\n"
                            + ansver.get(0) + " " + question.poll());
                    out.flush();
                } else {
                    out.writeUTF("Очень приятно " + ansver.get(0) + ", " + question.poll());
                    out.flush();
                }
            }
            System.out.println("Closing connections & channels - DONE.");

        } catch(
                IOException e){
            e.printStackTrace();
        }

    }
}