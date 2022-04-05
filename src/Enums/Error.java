package Enums;

public enum Error
{
    COMMAND_NOT_FOUND("Command not found: "),
    NOT_IN_TRANSACTION("No corresponding START found for command: "),
    KEY_NOT_FOUND("Key not found: ");

    private final String message;

    Error(String message)
    {
        this.message = message;
    }

    public String toString()
    {
        return message;
    }
}
