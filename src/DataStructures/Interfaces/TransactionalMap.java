package DataStructures.Interfaces;

public interface TransactionalMap<K, V>
{
    void beginTransaction();

    void commitTransaction();

    void endTransaction();

    V get(K key);

    boolean inTransaction();

    V put(K key, V val);

    V remove(K key);

    String toString();

	void addSavepoint(K keyName);
	bool rollBackSavepoint(K keyname);
	bool releaseSavepoint(K keyname);
}
- SAVEPOINT <SAVEPOINT_NAME>
- ROLLBACK <SAVEPOINT_NAME>
- RELEASE SAVEPOINT <SAVEPOINT_NAME>;
