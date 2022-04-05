package Services;

import Enums.Command;
import DataStructures.Interfaces.TransactionalMap;

import java.util.HashMap;

public class ReplClient
{
    private TransactionalMap<String, String> keyVal;
    private HashMap<String, Command> commands;

    public ReplClient(TransactionalMap<String, String> map)
    {
        keyVal = map;
        commands = new HashMap<>();

        for (Command c : Command.values())
        {
            commands.put(c.name(), c);
        }
    }

    public boolean abort()
    {
        if (keyVal.inTransaction())
        {
            keyVal.endTransaction();
            return true;
        }

        return false;
    }

    public boolean commit()
    {
        if (keyVal.inTransaction())
        {
            keyVal.commitTransaction();
            return true;
        }

        return false;
    }

    public void delete(String key)
    {
        keyVal.remove(key);
    }

    public Command getCommand(String input)
    {
        String command = input.toUpperCase();
        return commands.get(command);
    }

    public String read(String key)
    {
        return keyVal.get(key);
    }

    public void start()
    {
        keyVal.beginTransaction();
    }

    public String toString()
    {
        return keyVal.toString();
    }

    public void write(String key, String value)
    {
        keyVal.put(key, value);
    }
}
