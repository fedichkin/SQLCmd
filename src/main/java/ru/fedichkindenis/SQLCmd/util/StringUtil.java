package ru.fedichkindenis.SQLCmd.util;

/**
 * Created by Денис on 13.07.2016.
 *
 * Утилиты для работы со строками
 */
public class StringUtil {

    public static boolean isEmpty(String text) {

        return text == null || text.trim().length() == 0;
    }
}
