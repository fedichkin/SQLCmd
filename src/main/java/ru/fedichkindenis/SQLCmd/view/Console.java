package ru.fedichkindenis.SQLCmd.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Денис on 11.07.2016.
 */
public class Console implements View {

    @Override
    public void write(String message) {

        System.out.println(message);
    }

    @Override
    public String read() {

        String result = "";

        try (InputStreamReader inReader = new InputStreamReader(System.in);
             BufferedReader reader = new BufferedReader(inReader)) {

            result = reader.readLine();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return result;
    }
}
