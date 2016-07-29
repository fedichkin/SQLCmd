package ru.fedichkindenis.SQLCmd.view;

import ru.fedichkindenis.SQLCmd.model.DataMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Денис on 18.07.2016.
 *
 * Декаратор для отображения информации из базы данных в виде таблиц консоли
 * Для корректного отображение в консоле должен быть установлен шритфт Courier New
 */
public class TableConsoleView extends ViewDecorator {

    private final char LEFT_DOWN_ANGLE = '╚';
    private final char RIGHT_DOWN_ANGLE = '╝';
    private final char LEFT_UP_ANGLE = '╔';
    private final char RIGHT_UP_ANGLE = '╗';
    private final char CROSS_BORDER = '╬';
    private final char HORIZONTAL_BORDER = '═';
    private final char VERTICAL_BORDER = '║';
    private final char CROSS_UP = '╦';
    private final char CROSS_DOWN = '╩';
    private final char CROSS_LEFT = '╠';
    private final char CROSS_RIGHT = '╣';

    public TableConsoleView(View view) {
        super(view);
    }

    @Override
    public void write(List<String> list, AlignWrite alignWrite) {

        if(list == null || list.size() == 0) {

            throw new IllegalArgumentException("Не указан список значений");
        }

        if(alignWrite.equals(AlignWrite.HORIZONTAL)) {
            int maxLength = getMaxLength(list);
            String upHead = getHorizontalHead(maxLength, list.size(), true);
            String downHead = getHorizontalHead(maxLength, list.size(), false);
            String row = getHorizontalRow(list, maxLength);

            write(upHead);
            write(row);
            write(downHead);
        }
        else if(alignWrite.equals(AlignWrite.VERTICAL)) {
            int maxLength = getMaxLength(list);
            String upHead = getHorizontalHead(maxLength, 1, true);
            String downHead = getHorizontalHead(maxLength, 1, false);

            write(upHead);

            for(String row : getVerticalRows(list, maxLength)) {
                write(row);
            }

            write(downHead);
        }
    }

    @Override
    public void write(DataMap dataMap) {

    }

    @Override
    public void write(List<DataMap> listDataMap) {

    }

    private int getMaxLength(Collection<String> strings) {

        int max = 0;

        for(String string : strings) {

            if(max < string.length()) {
                max = string.length();
            }
        }

        return max;
    }

    private String getHorizontalHead(int maxLength, int countColumn, boolean isUp) {

        int countBorder = countColumn + 1;

        char [] head = new char[countBorder + (countColumn * maxLength)];

        if(isUp) {
            head[0] = LEFT_UP_ANGLE;
            head[head.length - 1] = RIGHT_UP_ANGLE;
        }
        else {
            head[0] = LEFT_DOWN_ANGLE;
            head[head.length - 1] = RIGHT_DOWN_ANGLE;
        }

        int indexBorder = 1;
        for(int i = 0; i < countColumn; i++) {
            for(int j = 0; j < maxLength; j++) {

                head[i * maxLength + j + indexBorder] = HORIZONTAL_BORDER;
            }

            if(i < (countColumn - 1)) {
                if(isUp) {
                    head[i * maxLength + maxLength + indexBorder] = CROSS_UP;
                }
                else {
                    head[i * maxLength + maxLength + indexBorder] = CROSS_DOWN;
                }

                indexBorder++;
            }
        }

        return new String(head);
    }

    private String getHorizontalRow(Collection<String> strings, int maxLength) {

        String row = "";

        for(String string : strings) {

            row = row + VERTICAL_BORDER;
            row = row + string;
            if(string.length() < maxLength) {
                for(int i = 0; i < maxLength - string.length(); i++) {
                    row = row + " ";
                }
            }
        }
        row = row + VERTICAL_BORDER;

        return row;
    }

    private List<String> getVerticalRows(Collection<String> strings, int maxLength) {

        List<String>  rows = new LinkedList<>();

        for (String string : strings) {

            String row = "" + VERTICAL_BORDER;
            row = row + string;
            if(string.length() < maxLength) {
                for(int i = 0; i < maxLength - string.length(); i++) {
                    row = row + " ";
                }
            }
            row = row + VERTICAL_BORDER;
            rows.add(row);
            rows.add(gerHorizontalSeparator(maxLength));
        }

        rows.remove(rows.size() - 1);

        return rows;
    }

    private String gerHorizontalSeparator(int length) {

        String separator = "" + CROSS_LEFT;

        for(int i = 0; i < length; i++) {
            separator = separator + HORIZONTAL_BORDER;
        }

        separator = separator + CROSS_RIGHT;

        return separator;
    }
}

