package ru.client;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование клиента словаря.
 */
public class ClientTest {

    /**
     *  Отправление пустой команды.
     */
    @Test
    public void whenCommandEmptyThenReturnFalse() {
        Client client = new Client();
        boolean result = client.checkArg("", "Server");
        assertThat("empty command", result, is(false));
    }

    /**
     * Отправление корректной команды.
     */
    @Test
    public void whenCommandCorrectThenReturnTrue() {
        Client client = new Client();
        boolean result = client.checkArg("add", "command");
        assertThat("add command", result, is(true));
    }

    /**
     * Отправление некорректной команды.
     */
    @Test
    public void whenCommandNotCorrectThenReturnFalse() {
        Client client = new Client();
        boolean result = client.checkArg("value", "command");
        assertThat("value command", result, is(false));
    }
}