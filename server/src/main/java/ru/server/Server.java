package ru.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс сервера для словаря
 */
public class Server {

    /**
     * Режим отладки, отображает логи.
     */
    public static final boolean DEBUG = true;

    /**
     * Позиция команды в аргументах.
     */
    public static final int COMMAND = 0;

    /**
     * Позиция слова в аргументах.
     */
    public static final int WORD = 1;

    /**
     * Порт.
     */
    public static final int PORT = 3345;

    /**
     * Словарь.
     */
    public static final ArrayList<Word> DICITONARY = new ArrayList<Word>();

    /**
     * Количество создаваемых нитей.
     */
    public static final int COUNT_THREAD = 10;

    /**
     * ExecutorService.
     */
    private static ExecutorService executeIt = Executors.newFixedThreadPool(COUNT_THREAD);

    /**
     * Точка входа.
     *
     * @param args аргументы
     * @throws IOException -
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Start.");
        while (!server.isClosed()) {
            Socket client = server.accept();
            executeIt.execute(new MonoThread(client));
        }
        executeIt.shutdown();
    }

}
