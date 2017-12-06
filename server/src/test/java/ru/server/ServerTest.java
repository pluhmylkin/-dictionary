package ru.server;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование сервера.
 */
public class ServerTest {

    /**
     * Тестовый сервер.
     */
    private String TEST_SERVER = "10.0.1.5";

    /**
     * Некорректный запрос создания слова.
     */
    private String TEST_INCORRECT_SEND_ADD = TEST_SERVER + " add hello привет";

    /**
     * Корректный запрос создания слова.
     */
    private String TEST_CORRECT_SEND_ADD = "add hello здравствуйте привет";

    /**
     * Корректный запрос обновления слова.
     */
    private String TEST_CORRECT_SEND_UPDATE = "add hello привет здравствуйте";

    /**
     * Корректный запрос получения слова.
     */
    private String TEST_CORRECT_SEND_GET = "get hello";

    /**
     * Некорректный запрос получения слова.
     */
    private String TEST_INCORRECT_SEND_GET = "get hello2";

    /**
     * Корректный запрос удаления значений слова.
     */
    private String TEST_CORRECT_SEND_DELETE = "delete hello привет";

    /**
     * Некорректный запрос удаления значений слова.
     */
    private String TEST_INCORRECT_SEND_DELETE = "delete hello2 привет";


    /**
     * Socket.
     */
    Socket socket;

    /**
     * Выходящий поток данных.
     */
    DataOutputStream oos;

    /**
     * Входящий поток данных.
     */
    DataInputStream ois;


    /**
     * Конструктор.
     * @throws IOException -
     */
    public ServerTest() throws IOException {
        try {
            socket = new Socket(TEST_SERVER, Server.PORT);
            oos = new DataOutputStream(socket.getOutputStream());
            ois = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            assertThat("server not found", e.getMessage(), is("Connection refused: connect"));
        }

    }

    /**
     * Некорректно сформированный запрос к серверу.
     * @throws IOException -
     */
    @Test
    public void whenAnswerNotFound() throws IOException {
        if(oos != null) {
            oos.writeUTF(TEST_INCORRECT_SEND_ADD);
            oos.flush();
            while (!socket.isOutputShutdown()) {
                if (ois.available() != 0) {
                    assertThat("send incorrect request", ois.readUTF(), is("Ошибка запроса, повторите попытку"));
                    break;
                }
            }
        }
    }


    /**
     * Добавление слова.
     * @throws IOException -
     */
    @Test
    public void whenAddWord() throws IOException {

        if(oos != null) {
        oos.writeUTF(TEST_CORRECT_SEND_ADD);
        oos.flush();
        while (!socket.isOutputShutdown()) {
            if (ois.available() != 0) {
                assertThat("correct request add", ois.readUTF(), is("Значение слова успешно добавлено"));
                break;
            }
        }
        }
    }

    /**
     * Обновление слова.
     * @throws IOException -
     */
    @Test
    public void whenUpdateWord() throws IOException {
        if(oos != null) {
            oos.writeUTF(TEST_CORRECT_SEND_UPDATE);
            oos.flush();
            while (!socket.isOutputShutdown()) {
                if (ois.available() != 0) {
                    assertThat("update word", ois.readUTF(), is("Значение слова успешно добавлено"));
                    break;
                }
            }
        }
    }


    /**
     * Некорретный запрос удаления значений слова.
     * @throws IOException -
     */
    @Test
    public void whenIncorrectDeleteWord() throws IOException {
        if(oos != null) {
            oos.writeUTF(TEST_INCORRECT_SEND_DELETE);
            oos.flush();
            while (!socket.isOutputShutdown()) {
                if (ois.available() != 0) {
                    assertThat("incorrect delete", ois.readUTF(), is("Слово/значение отсутствует в словаре"));
                    break;
                }
            }
        }
    }

    /**
     * Корректное удаление слова
     * @throws IOException -
     */
    @Test
    public void whenCorrectDeleteWord() throws IOException {
        if(oos != null) {
            oos.writeUTF(TEST_CORRECT_SEND_DELETE);
            oos.flush();
            while (!socket.isOutputShutdown()) {
                if (ois.available() != 0) {
                    assertThat("correct delete", ois.readUTF(), is("Значения слова успешно удалены"));
                    break;
                }
            }
        }
    }

    /**
     * Корректное получение слова
     * @throws IOException -
     */
    @Test
    public void whenCorrectGetWord() throws IOException {
        if (oos != null) {
            oos.writeUTF(TEST_CORRECT_SEND_GET);
            oos.flush();
            while (!socket.isOutputShutdown()) {
                if (ois.available() != 0) {
                    assertThat("correct get", ois.readUTF(), is("здравствуйте\nпривет\n"));
                    break;
                }
            }
        }
    }

    /**
     * Поиск несуществующего слова
     * @throws IOException -
     */
    @Test
    public void whenIncorrectGetWord() throws IOException {
        if (oos != null) {
            oos.writeUTF(TEST_INCORRECT_SEND_GET);
            oos.flush();
            while (!socket.isOutputShutdown()) {
                if (ois.available() != 0) {
                    assertThat("incorrect get", ois.readUTF(), is("Слово отсутствует в словаре"));
                    break;
                }
            }
        }
    }
}