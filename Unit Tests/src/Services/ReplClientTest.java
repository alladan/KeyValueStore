package Services;

import DataStructures.Interfaces.TransactionalMap;

import static org.junit.jupiter.api.Assertions.*;

import Enums.Command;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ReplClientTest
{
    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();
    @Mock
    private TransactionalMap<String, String> mockTm;
    private ReplClient client;

    @Test
    public void abort_inTransaction_returnsTrue()
    {
        when(mockTm.inTransaction()).thenReturn(true);
        assertTrue(client.abort());
    }

    @Test
    public void abort_notInTransaction_returnsFalse()
    {
        when(mockTm.inTransaction()).thenReturn(false);
        assertFalse(client.abort());
    }

    @Test
    public void commit_inTransaction_returnsTrue()
    {
        when(mockTm.inTransaction()).thenReturn(true);
        assertTrue(client.commit());
    }

    @Test
    public void commit_notInTransaction_returnsFalse()
    {
        when(mockTm.inTransaction()).thenReturn(false);
        assertFalse(client.commit());
    }

    @Test
    public void delete_whenCalled_removesKey()
    {
        client.delete("");
        verify(mockTm, times(1)).remove(any());
    }

    @Test
    public void getCommand_givenNoMatch_returnsNull()
    {
        String test = "testCommandPleaseIgnore";
        assertNull(client.getCommand(test));
    }

    @Test
    public void getCommand_givenValidLowerCaseCommands_matchesAll()
    {
        for (Command c : Command.values())
        {
            assertEquals(client.getCommand(c.toString().toLowerCase()), c);
        }
    }

    @Test
    public void getCommand_givenValidUpperCaseCommands_matchesAll()
    {
        for (Command c : Command.values())
        {
            assertEquals(client.getCommand(c.toString().toUpperCase()), c);
        }
    }

    @Test
    public void read_whenCalled_getsKey()
    {
        client.read("");
        verify(mockTm, times(1)).get(any());
    }

    @Before
    public void setUp()
    {
        client = new ReplClient(mockTm);
    }

    @Test
    public void start_whenCalled_beginsTransaction()
    {
        client.start();
        verify(mockTm, times(1)).beginTransaction();
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void toString_whenCalled_returnsMapToString()
    {
        String test = "test";
        when(mockTm.toString()).thenReturn(test);
        assertEquals(client.toString(), test);
    }

    @Test
    public void write_whenCalled_putsKeyValue()
    {
        client.write("", "");
        verify(mockTm, times(1)).put(any(), any());
    }
}
