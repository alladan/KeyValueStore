package Application;

import Enums.Command;
import Enums.Error;
import Services.ReplClient;

import java.io.InputStream;
import java.util.Scanner;

public class ReplClientApplication
{
    private ReplClient client;
    private Scanner in;

    public ReplClientApplication(ReplClient client, InputStream inputSource)
    {
        this.client = client;
        in = new Scanner(inputSource);
    }

    public void run()
    {
        Command command = null;

        do
        {
            System.out.print("> ");

            //inputValues[0] = command; [1] = key; [2] = value
            String[] inputValues = getInputValues(in);

            //if input was all spaces, a newline, or otherwise
            if (inputValues.length == 0)
            {
                continue;
            }

            command = client.getCommand(inputValues[0]);

            if (!validateCommand(command, inputValues))
            {
                continue;
            }

            processCommand(command, inputValues);
        }
        while (command != Command.QUIT);

        System.err.println("Exiting...");
    }

    private String[] getInputValues(Scanner in)
    {
        String input = in.nextLine().trim();

        if (input.length() == 0)
        {
            return new String[0];
        }

        return input.split("\\s+");
    }

    private boolean validateCommand(Command command, String[] inputValues)
    {
        //if command not found
        if (command == null)
        {
            System.err.println(Error.COMMAND_NOT_FOUND + inputValues[0]);
            return false;
        }

        //(inputValues - command) should == numParameters
        if ((inputValues.length - 1) != command.getNumParameters())
        {
            System.err.println(command.getFormatError());
            return false;
        }

        return true;
    }

    private void processCommand(Command command, String[] inputValues)
    {
        switch (command)
        {
            case WRITE:
                client.write(inputValues[1], inputValues[2]);
                break;
            case READ:
                String key = inputValues[1];
                String value = client.read(key);
                if (value == null)
                {
                    System.err.println(Error.KEY_NOT_FOUND + key);
                    break;
                }
                System.out.println(value);
                break;
            case DELETE:
                client.delete(inputValues[1]);
                break;
            case START:
                client.start();
                break;
            case ABORT:
                if (!client.abort())
                {
                    System.err.println(Error.NOT_IN_TRANSACTION + command.toString());
                }
                break;
            case COMMIT:
                if (!client.commit())
                {
                    System.err.println(Error.NOT_IN_TRANSACTION + command.toString());
                }
                break;
            case PRINT:
                System.err.println(client.toString());
                break;
            default:
                break;
        }
    }
}