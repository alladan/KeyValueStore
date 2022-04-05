package DataStructures;

import DataStructures.Interfaces.TransactionalMap;

import java.util.HashMap;
import java.util.Stack;

public class TransactionalHashMap<K, V> implements TransactionalMap<K, V>
{
    private Stack<HashMap<K, V>> transactions;
    private HashMap<K, V> currentMap;
private HashMap<K, TransactionalHashMap<K,V>> nameToSavePoint;

    public TransactionalHashMap()
    {
        transactions = new Stack<>();
	nameToSavePoint = new HashMap<>();
        currentMap = new HashMap<>();
        //last "transaction" will always be the original
        transactions.push(currentMap);
    }

    @Override
    public void beginTransaction()
    {
        //clone the current map
        HashMap<K, V> transactionKeyVal = (HashMap<K, V>) currentMap.clone();
        //add clone to transactions
        transactions.push(transactionKeyVal);
        //set current map to clone
        currentMap = transactionKeyVal;
    }

    @Override
    public void commitTransaction()
    {
        if (!inTransaction())
        {
            throw new IllegalStateException();
        }
        //set currentMap to "existing" transaction
        currentMap = transactions.pop();
        //pop out previous transaction and set to current to overwrite
        transactions.pop();
        transactions.push(currentMap);
    }

    @Override
    public void endTransaction()
    {
        if (!inTransaction())
        {
            throw new IllegalStateException();
        }

        //throw away existing transaction
        transactions.pop();
        //set currentMap to "previous" transaction
        currentMap = transactions.peek();
    }

    @Override
    public V get(K key)
    {
        return currentMap.get(key);
    }

    @Override
    public boolean inTransaction()
    {
        //the last "transaction" is always the original map
        return transactions.size() > 1;
    }

    @Override
    public V put(K key, V val)
    {
        return currentMap.put(key, val);
    }

    @Override
    public V remove(K key)
    {
        return currentMap.remove(key);
    }

@Override
public void addSavePoint(K key)
{
	
}

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (HashMap<K, V> map : transactions)
        {
            sb.append("(");
            for (K key : map.keySet())
            {
                sb.append("{").append(key).append(",").append(map.get(key)).append("}, ");
            }
            sb.append(")\n");
        }

        return sb.toString();
    }
}
