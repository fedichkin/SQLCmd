package ru.fedichkindenis.SQLCmd.view;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Денис on 27.07.2016.
 *
 * Класс для тестирования работы с консолью
 */
public class ConsoleTest {

    private static String CRLF = "\r\n";

    @Test
    public void testWrite() {

        String expected = "Hello World!!!";

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            System.setOut(new PrintStream(baos));

            View console = new Console();
            console.write(expected);

            String actual = new String(baos.toByteArray());
            assertEquals(expected + CRLF, actual);
        } catch (IOException e) {

            System.err.println("Error ConsoleTest.testWrite: " + e.getMessage());
        }
    }

    @Test
    public void testRead() {

        String expected = "Hello World!!!";

        try (ByteArrayInputStream bais = new ByteArrayInputStream(expected.getBytes())) {

            System.setIn(bais);

            View console = new Console();
            String actual = console.read();

            assertEquals(expected, actual);
        } catch (IOException e) {

            System.err.println("Error ConsoleTest.testRead: " + e.getMessage());
        }
    }

    @Test
    public void testExceptionRead() {

        try {

            InputStream inputStream = new InputStream() {
                @Override
                public int read() throws IOException {

                    throw new IOException("Ошибка чтения");
                }

                @Override
                public int read(byte b[], int off, int len) throws IOException {

                    throw new IOException("Ошибка чтения");
                }
            };

            System.setIn(inputStream);

            View console = new Console();
            console.read();

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ошибка чтения", e.getMessage());
        }
    }

    @Test
    public void testClose() {

        final Boolean[] actual = new Boolean[1];

        try (ByteArrayInputStream bais = new ByteArrayInputStream(new byte['q']){

            @Override
            public void close() throws IOException {
                actual[0] = true;
                super.close();
            }
        }) {

            System.setIn(bais);

            View console = new Console();
            console.close();

            assertEquals(true, actual[0]);
        } catch (IOException e) {

            System.err.println("Error ConsoleTest.testClose: " + e.getMessage());
        }
    }

    @Test
    public void testExceptionClose() {

        try {

            InputStream inputStream = new InputStream() {
                @Override
                public int read() throws IOException {

                    throw new IOException("Ошибка чтения");
                }

                @Override
                public void close() throws IOException {

                    throw new IOException("Ошибка закрытия");
                }
            };

            System.setIn(inputStream);

            View console = new Console();
            console.close();

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ошибка закрытия", e.getMessage());
        }
    }
}
