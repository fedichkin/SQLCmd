package sqlcmd.view;

import org.junit.Test;
import ru.fedichkindenis.sqlcmd.view.Console;
import ru.fedichkindenis.sqlcmd.view.View;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Класс для тестирования работы с консолью
 */
public class ConsoleTest {

    private static String CRLF = "\r\n";

    @Test
    public void testWrite() {

        //given
        String expected = "Hello World!!!";

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            System.setOut(new PrintStream(baos));

            //when
            View console = new Console();
            console.write(expected);

            String actual = new String(baos.toByteArray());
            //then
            assertEquals(expected + CRLF, actual);
        } catch (IOException e) {

            System.err.println("Error ConsoleTest.testWrite: " + e.getMessage());
        }
    }

    @Test
    public void testRead() {

        //given
        String expected = "Hello World!!!";

        try (ByteArrayInputStream bais = new ByteArrayInputStream(expected.getBytes())) {

            System.setIn(bais);
            //when
            View console = new Console();
            String actual = console.read();
            //then
            assertEquals(expected, actual);
        } catch (IOException e) {

            System.err.println("Error ConsoleTest.testRead: " + e.getMessage());
        }
    }

    @Test
    public void testExceptionRead() {

        try {
            //given
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
            //when
            View console = new Console();
            console.read();
            //then
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ошибка чтения", e.getMessage());
        }
    }

    @Test
    public void testClose() {

        //given
        final Boolean[] actual = new Boolean[1];

        try (ByteArrayInputStream bais = new ByteArrayInputStream(new byte['q']){

            @Override
            public void close() throws IOException {
                actual[0] = true;
                super.close();
            }
        }) {

            System.setIn(bais);
            //when
            View console = new Console();
            console.close();
            //then
            assertEquals(true, actual[0]);
        } catch (IOException e) {

            System.err.println("Error ConsoleTest.testClose: " + e.getMessage());
        }
    }

    @Test
    public void testExceptionClose() {

        try {
            //given
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
            //when
            View console = new Console();
            console.close();
            //then
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ошибка закрытия", e.getMessage());
        }
    }
}
