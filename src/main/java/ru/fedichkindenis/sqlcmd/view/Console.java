package ru.fedichkindenis.sqlcmd.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для отображения и чтения информации в консоли
 */
public class Console implements View {

    private BufferedReader reader;

    public Console() {

        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void write(String message) {

        System.out.println(message);
    }

    @Override
    public String read() {

        String result;

        try {

            result = reader.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    @Override
    public void close() {

        if(reader != null) {

            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
