package ru.server;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Функции словаря.
 */
public class Dictionary {

    /**
     * Добавление нового слова (если слово существует,
     * то будут добавлены недостающие значения слова).
     *
     * @param wordNew слова для добавления
     * @param values значения слов
     * @return добавленное / обновленное слов
     */
    public synchronized Word add(String wordNew, ArrayList<String> values) {
        Word word = this.getWordFromDictionary(wordNew);
        if (word != null) {
            for (String value : word.getValues()) {
                if (!values.contains(value)) {
                    values.add(value);
                }
            }
            word.setValues(values);
            if (Server.DEBUG) {
                System.err.println("Update word " + wordNew);
            }
        } else {
            word = new Word(wordNew, values);
            Server.DICITONARY.add(word);
            if (Server.DEBUG) {
                System.out.println("Add word " + wordNew);
                System.err.println("Add values " + values);
            }
        }

        if (Server.DEBUG) {
            this.showWordsToConsole();
        }
        return word;
    }

    /**
     * Получение слова из словаря.
     *
     * @param word искомое слово
     * @return слово
     */
    public final Word get(String word) {
        if (Server.DEBUG) {
            System.err.println("Get word " + word);
        }
        return this.getWordFromDictionary(word);

    }

    /**
     * Удаление значений слова.
     *
     * @param wordFound  Слово для удаления
     * @param values     перечень значений для удаления
     * @return true - удален, false - не найден
     */
    public synchronized boolean delete(String wordFound, ArrayList<String> values) {
        Word word = this.getWordFromDictionary(wordFound);
        boolean flag = false;
        if (word != null) {
            if (Server.DEBUG) {
                System.err.println("Deleted values for " + wordFound);
            }

            ArrayList<String> newValues = new ArrayList<String>();
            for (String value : word.getValues()) {
                if (!values.contains(value)) {
                    newValues.add(value);
                    flag = true;
                }
            }
            word.setValues(newValues);

            if (Server.DEBUG) {
                this.showWordsToConsole();
            }
        }
        return flag;
    }

    /**
     * Получение списка значений слова из аргументов.
     *
     * @param args аргументы
     * @return значения для слова
     */
    public final ArrayList<String> getValuesFromArgs(String[] args) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(args));
        list.remove(Server.WORD);
        list.remove(Server.COMMAND);
        return list;
    }

    /**
     * Поиск слова в словаре.
     *
     * @param wordFound - искомое слово
     * @return - найденное слово
     */
    private Word getWordFromDictionary(String wordFound) {
        Word wordReturn = null;
        for (Word word : Server.DICITONARY) {
            if (word.getWord().equals(wordFound)) {
                wordReturn = word;
            }
        }
        return wordReturn; //
    }

    /**
     * Отображение содержимого словаря.
     */
    private void showWordsToConsole() {
        System.out.println("===");
        System.out.println("Count words: "
                + Server.DICITONARY.size());
        for (Word word : Server.DICITONARY) {
            System.out.println("Word: " + word.getWord());
            for (String value : word.getValues()) {
                System.out.println("Value: " + value);
            }
        }
        System.out.println("===");
    }
}
