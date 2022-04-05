package DataStructures;

import DataStructures.Interfaces.TransactionalMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionalHashMapTest
{
    private TransactionalMap<String, String> testMap;
    private String test, test2, test3;

    TransactionalHashMapTest()
    {
        test = "";
        test2 = "test2";
        test3 = "test3";
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        testMap = new TransactionalHashMap<>();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown()
    {
        testMap = new TransactionalHashMap<>();
    }

    @Test
    void beginTransaction_runOnce_inTransaction()
    {
        testMap.beginTransaction();
        assertTrue(testMap.inTransaction());
    }

    @Test
    void beginTransaction_runMultiple_inTransaction()
    {
        testMap.beginTransaction();
        testMap.beginTransaction();
        assertTrue(testMap.inTransaction());
    }

    private void beginTransactionsAndPutTestStrings()
    {
        testMap.put(test, test);
        testMap.beginTransaction();
        testMap.put(test2, test2);
        testMap.beginTransaction();
        testMap.put(test3, test3);
    }

    @Test
    void beginTransactions_commitThenEnd_keepsOriginal()
    {
        beginTransactionsAndPutTestStrings();

        testMap.commitTransaction();
        testMap.remove(test);
        assertEquals(testMap.get(test3), test3);
        assertEquals(testMap.get(test2), test2);
        assertNull(testMap.get(test));

        testMap.endTransaction();
        assertNull(testMap.get(test3));
        assertNull(testMap.get(test2));
        assertEquals(testMap.get(test), test);
    }

    @Test
    void beginTransactions_endThenCommit_keepsPrevious()
    {
        beginTransactionsAndPutTestStrings();

        testMap.endTransaction();
        testMap.remove(test);
        assertNull(testMap.get(test3));
        assertEquals(testMap.get(test2), test2);
        assertNull(testMap.get(test));

        testMap.commitTransaction();
        assertNull(testMap.get(test3));
        assertEquals(testMap.get(test2), test2);
        assertNull(testMap.get(test));
    }

    @Test
    void commitTransaction_noTransaction_throws()
    {
        assertThrows(IllegalStateException.class, () ->
                testMap.commitTransaction());
    }

    @Test
    void commitTransaction_moreCommitsThanBegins_throws()
    {
        testMap.beginTransaction();
        testMap.commitTransaction();
        assertThrows(IllegalStateException.class, () ->
                testMap.commitTransaction());
    }

    @Test
    void commitTransaction_moreBeginsThanCommits_inTransaction()
    {
        testMap.beginTransaction();
        testMap.beginTransaction();
        testMap.commitTransaction();
        assertTrue(testMap.inTransaction());
    }

    @Test
    void commitTransaction_ranOnce_savesTransaction()
    {
        String test = "";
        testMap.put(test, test);
        testMap.beginTransaction();
        testMap.remove(test);
        assertNull(testMap.get(test));
        testMap.commitTransaction();
        assertNull(testMap.get(test));
    }

    @Test
    void commitTransaction_ranMultiple_savesTransactions()
    {
        beginTransactionsAndPutTestStrings();

        testMap.commitTransaction();
        assertEquals(testMap.get(test3), test3);
        assertEquals(testMap.get(test2), test2);
        assertEquals(testMap.get(test), test);

        testMap.commitTransaction();
        assertEquals(testMap.get(test3), test3);
        assertEquals(testMap.get(test2), test2);
        assertEquals(testMap.get(test), test);
    }

    @Test
    void commitTransaction_sameCommitsBegins_noTransaction()
    {
        testMap.beginTransaction();
        testMap.commitTransaction();
        assertFalse(testMap.inTransaction());
    }

    @Test
    void endTransaction_noTransaction_throws()
    {
        assertThrows(IllegalStateException.class, () ->
                testMap.commitTransaction());
    }

    @Test
    void endTransaction_moreEndsThanBegins_throws()
    {
        testMap.beginTransaction();
        testMap.endTransaction();
        assertThrows(IllegalStateException.class, () ->
                testMap.endTransaction());
    }

    @Test
    void endTransaction_moreBeginsThanEnds_inTransaction()
    {
        testMap.beginTransaction();
        testMap.beginTransaction();
        testMap.endTransaction();
        assertTrue(testMap.inTransaction());
    }

    @Test
    void endTransaction_ranOnce_forgetsTransaction()
    {
        testMap.put(test, test);
        testMap.beginTransaction();
        testMap.remove(test);
        assertNull(testMap.get(test));
        testMap.endTransaction();
        assertEquals(testMap.get(test), test);
    }

    @Test
    void endTransaction_ranMultiple_forgetsTransactions()
    {
        beginTransactionsAndPutTestStrings();

        testMap.endTransaction();
        assertNull(testMap.get(test3));
        assertEquals(testMap.get(test2), test2);
        assertEquals(testMap.get(test), test);

        testMap.endTransaction();
        assertNull(testMap.get(test3));
        assertNull(testMap.get(test2));
        assertEquals(testMap.get(test), test);
    }

    @Test
    void endTransaction_sameEndsBegins_noTransaction()
    {
        testMap.beginTransaction();
        testMap.endTransaction();
        assertFalse(testMap.inTransaction());
    }
}
