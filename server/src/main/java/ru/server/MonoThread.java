package ru.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Работа с потоком.
 */
public class MonoThread implements Runnable {

    /**
     * Клиент.
     */
    private Socket clientDialog;


    /**
     * Конструктор.
     *
     * @param client клиент
     */
    public MonoThread(Socket client) {
        this.clientDialog = client;
    }

    @Override
    public final void run() {
        try {
            Dictionary dictionary = new Dictionary();
            DataInputStream in =
                    new DataInputStream(clientDialog.getInputStream());
            DataOutputStream out =
                    new DataOutputStream(clientDialog.getOutputStream());
            while (!clientDialog.isClosed()) {
                String[] args = in.readUTF().split(" ");
                String command = args[Server.COMMAND];
                String word = args[Server.WORD];
                String answer = "Ошибка запроса, повторите попытку";
                if (command.equals("add")) {
                    Word wordReturn = dictionary.add(word,
                            dictionary.getValuesFromArgs(args));
                    if (wordReturn != null) {
                        answer = "Значение слова успешно добавлено";
                    } else {
                        answer = "Ошибка при добавлении слова";
                    }

                } else if (command.equals("get")) {
                    Word wordReturn = dictionary.get(word);
                    if (wordReturn != null) {
                        answer = "";
                        for (String value : wordReturn.getValues()) {
                            answer += value + "\n";
                        }
                    } else {
                        answer = "Слово отсутствует в словаре";
                    }
                } else if (command.equals("delete")) {
                    if (dictionary.delete(word,
                            dictionary.getValuesFromArgs(args))) {
                        answer = "Значения слова успешно удалены";
                    } else {
                        answer = "Слово/значение отсутствует в словаре";
                    }
                }
                out.writeUTF(answer);
                clientDialog.close();
                out.flush();
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
