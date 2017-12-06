package ru.server;

import java.util.ArrayList;

/**
 * Cущность слова.
 */
public class Word {

    /**
     * Название слова.
     */
    private String word;

    /**
     * Значения слова.
     */
    private ArrayList<String> values = new ArrayList<String>();

    /**
     * Конструктор.
     *
     * @param wordNew   слово
     * @param valuesNew значения
     */
    public Word(String wordNew, ArrayList<String> valuesNew) {
        this.word = wordNew;
        this.values = valuesNew;
    }

    /**
     * Получение названия слова.
     * @return слово
     */
    public final String getWord() {
        return word;
    }

    /**
     * Получение значений слова.
     *
     * @return значения слова
     */
    public final ArrayList<String> getValues() {
        return values;
    }

    /**
     * Установка/обновление значений слова.
     *
     * @param valuesNew значения слова
     */
    public final void setValues(ArrayList<String> valuesNew) {
        this.values = valuesNew;
    }
}
