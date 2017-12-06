package ru.client;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Клиент справочника.
 */
public class Client {

    /**
     * Минимальное количество аргументов (Server name, command, word).
     */
    private static final int MIN_ARGS = 3;
    /**
     * Value Server from args.
     */
    private static final int SERVER = 0;
    /**
     * Позиция команды для словаря.
     */
    private static final int COMMAND = 1;
    /**
     * Позиция слова.
     */
    private static final int WORD = 2;
    /**
     * Позиция первого значения слова.
     */
    private static final int VALUE_FIRST = 3;
    /**
     * Порт.
     */
    private static final int PORT = 3345;

    /**
     * Список допустимых команда от клиента.
     */
    private static final String[] CORRECT_COMMANDS = {"add", "get", "delete"};

    /**
     * Точка входа.
     *
     * @param arg запрос пользователя
     * @throws IOException -
     */
    public static void main(final String[] arg)
            throws IOException {
        Client client = new Client();
        if (arg.length >= MIN_ARGS) {
            String server = arg[SERVER];
            String command = arg[COMMAND];
            String word = arg[WORD];

            String valueFirst = "-";
            if (arg.length > MIN_ARGS) {
                valueFirst = arg[VALUE_FIRST];
            }

            if (client.checkArg(server, "Server")
                    && client.checkArg(command, "command")
                    && client.checkArg(word, "word")
                    && client.checkArg(valueFirst, "value")) {
                try {
                    Socket socket = new Socket(server, PORT);

                    DataOutputStream oos =
                            new DataOutputStream(socket.getOutputStream());
                    DataInputStream ois =
                            new DataInputStream(socket.getInputStream());
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(arg));
                    list.remove(0);
                    String send = "";
                    for (String str : list) {
                        send += str + " ";
                    }
                    oos.writeUTF(send);
                    oos.flush();
                    while (!socket.isOutputShutdown()) {
                        if (ois.available() != 0) {
                            String in = ois.readUTF();
                            System.out.println(in);
                            break;
                        }
                    }
                } catch (IOException e) {
                    if(e.getMessage().equals("Connection refused: connect")){
                        System.err.println("Cервер " + server + " недоступен");
                    }else {
                        e.printStackTrace();
                    }
                }

            }
        } else {
            System.err.println("Недостаточно аргументов");
        }
    }

    /**
     * Проверка аргумента на пустоту и корректность команды.
     *
     * @param action аргумент
     * @param name   описание аргумента
     * @return true корректное значение; false -  неверное значение аргуммента
     */
    public final boolean checkArg(final String action, final String name) {
        boolean flag = false;
        if (action.equals("")) {
            System.err.println("Аргумент пустой " + name);
        } else {
            if (name == "command"
                    && !Arrays.asList(CORRECT_COMMANDS).contains(action.toLowerCase())) {
                System.err.println("Неверная команда " + action);
            } else {
                flag = true;
            }
        }
        return flag;
    }
}
