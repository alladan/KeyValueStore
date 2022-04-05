import Application.ReplClientApplication;
import DataStructures.TransactionalHashMap;
import Services.ReplClient;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ReplClientApplicationTest
{
    @Test
    public void testInput1()
    {
        String testInput =
                "WRITE a hello\n" +
                        "READ a\n" +
                        "START\n" +
                        "WRITE a hello-again\n" +
                        "READ a\n" +
                        "START\n" +
                        "DELETE a\n" +
                        "READ a\n" +
                        "COMMIT\n" +
                        "READ a\n" +
                        "WRITE a once-more\n" +
                        "READ a\n" +
                        "ABORT\n" +
                        "READ a\n" +
                        "QUIT";

        String testOut =
                "> > hello\r\n" +
                        "> > > hello-again\r\n" +
                        "> > > > > > > once-more\r\n" +
                        "> > hello\r\n" +
                        "> ";

        String testErr =
                "Key not found: a\r\n" +
                        "Key not found: a\r\n" +
                        "Exiting...\r\n";

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        java.io.ByteArrayOutputStream err = new java.io.ByteArrayOutputStream();
        System.setErr(new java.io.PrintStream(err));

        ReplClient client = new ReplClient(new TransactionalHashMap<>());
        InputStream inputSource = new ByteArrayInputStream(testInput.getBytes());

        ReplClientApplication app = new ReplClientApplication(client, inputSource);
        app.run();

        assertEquals(testOut, out.toString());
        assertEquals(testErr, err.toString());
    }
}
