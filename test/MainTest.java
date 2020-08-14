import com.company.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(new PrintStream(originalOut));
    }

    @Test
    public void shouldSuccessfullyEncode() {
        Main.main(new String[]{"-key", "5", "-alg", "shift", "-data", "Welcome to hyperskill!", "-mode", "enc"});
        assertEquals("Bjqhtrj yt mdujwxpnqq!\r\n", outContent.toString());
    }

    @Test
    public void shouldSuccessfullyDecode() {
        Main.main(new String[]{"-key", "5", "-alg", "unicode", "-data", "\\jqhtrj%yt%m~ujwxpnqq&", "-mode", "dec"});
        assertEquals("Welcome to hyperskill!\r\n", outContent.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenWrongModeGiven() {
        Main.main(new String[]{"-key", "5", "-data", "\\jqhtrj%yt%m~ujwxpnqq&", "-mode", "kkk"});
    }

    @Test(expected = NumberFormatException.class)
    public void shouldThrowExceptionWhenWrongKey() {
        Main.main(new String[]{"-key", "aaa", "-data", "\\jqhtrj%yt%m~ujwxpnqq&", "-mode", "dec"});
    }

    @Test
    public void shouldReturnEmptyStringWhenDataIsEmpty() {
        Main.main(new String[]{"-key", "5", "-mode", "dec"});
        assertEquals(System.lineSeparator(), outContent.toString());
    }

    @Test
    public void shouldWarnAboutPotentiallyEmptyDataString() {
        Main.main(new String[]{"-key", "5", "-mode", "dec", "-data"});
        assertEquals("Error" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void shouldReturnAnErrorWhenNoArgumentForIn() {
        Main.main(new String[]{"-key", "5", "-mode", "enc", "-in"});
        assertEquals("Error" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void shouldEncodeDataFromFile() {
        File file = createTempInputFile();
        Main.main(new String[]{"-key", "5", "-mode", "enc", "-in", "file.txt", "-alg", "unicode"});
        assertEquals("\\jqhtrj%yt%m~ujwxpnqq&" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void shouldEncodeDataFromConsoleNotFromPresentFile() {
        File file = createTempInputFile();
        Main.main(new String[]{"-key", "5", "-mode", "enc", "-data", "aaa", "-in", "file.txt"});
        assertEquals("fff" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void shouldEncodeDataFromConsoleToFile() throws IOException {
        Main.main(new String[]{"-key", "5", "-mode", "enc", "-data", "aaa", "-out", "outputFile.txt"});
        String outputFileContent = new String(Files.readAllBytes(Paths.get("outputFile.txt")));
        assertEquals("fff" + System.lineSeparator(), outputFileContent);
    }

    @Test
    public void shouldEncodeDataFromFileToFile() throws IOException {
        File file = createTempInputFile();
        Main.main(new String[]{"-key", "5", "-mode", "enc", "-in", "file.txt", "-out", "outputFile.txt", "-alg", "unicode"});
        String outputFileContent = new String(Files.readAllBytes(Paths.get("outputFile.txt")));
        assertEquals("\\jqhtrj%yt%m~ujwxpnqq&" + System.lineSeparator(), outputFileContent);
    }

    private File createTempInputFile() {
        File file = new File("file.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Welcome to hyperskill!");
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
            return null;
        }
        file.deleteOnExit();
        return file;
    }
}