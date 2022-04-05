import DataStructures.TransactionalHashMap;
import Services.ReplClient;
import Application.ReplClientApplication;

import java.io.InputStream;

public class ReplClientApplicationDriver
{
    public static void main(String[] args)
    {
        ReplClient client = new ReplClient(new TransactionalHashMap<>());
        InputStream inputSource = System.in;
        ReplClientApplication app = new ReplClientApplication(client, inputSource);

        app.run();
    }
}
