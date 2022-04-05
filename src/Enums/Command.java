package Enums;

/*
If we wanted to perform logging/auditing,
it might make sense to use OOP
and create a "Command" hierarchy
such that each command knows
its parameters, its format, and how to run itself
e.g. command.execute(); instead of the
switch statement in Services.ReplClientApp

This way, it's also easy to record
each action in sequence, for example to a file
so that we can read it back instead of storing
the transaction copies in memory
 */
public enum Command
{
    READ(1, "<key>"),
    WRITE(2, "<key> <value>"),
    DELETE(1, "<key>"),
    START(),
    COMMIT(),
    ABORT(),
    QUIT(),
    PRINT();

    private final int numParameters;
    private final String format;

    Command()
    {
        this.numParameters = 0;
        this.format = null;
    }

    Command(int numParameters, String format)
    {
        this.numParameters = numParameters;
        this.format = format;
    }

    public String getFormat()
    {
        return this.format;
    }

    public String getFormatError()
    {
        if (this.format != null)
            return this + " must be in format: " + this + " " + this.getFormat();
        return null;
    }

    public int getNumParameters()
    {
        return this.numParameters;
    }
}
