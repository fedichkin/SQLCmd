package ru.fedichkindenis.sqlcmd.util;

/**
 * Утилиты для работы со строками
 */
public class StringUtil {

    public static boolean isEmpty(String text) {

        return text == null || text.trim().length() == 0;
    }
}
