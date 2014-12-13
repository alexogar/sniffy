JDBC Sniffer
============

JDBC Sniffer counts the number of executed SQL queries and provides an API for validating it and reseting to 0
It is very useful in unit tests and allows you to test if particular method doesn't make more than N SQL queries

Download
============
[jdbc-sniffer-1.0.jar](https://github.com/bedrin/jdbc-sniffer/raw/1.0/jdbc-sniffer-1.0.jar)

Setup
============
Simply add jdbc-sniffer.jar to your classpath and add "sniffer:" prefix to the JDBC connection url
For example "jdbc:h2:~/test" should be changed to "sniffer:jdbc:h2:~/test"

Validating the number of queries
============
The number of executed queries is available via static methods of two classes:
com.github.bedrin.jdbc.sniffer.Sniffer and com.github.bedrin.jdbc.sniffer.ThreadLocalSniffer

First one holds the number of SQL queries executed by all threads, while the later holds the number of SQL queries generated by current thread only

```java
@Test
public void testExecuteStatement() throws ClassNotFoundException, SQLException {
    // Just add sniffer: in front of your JDBC connection URL in order to enable sniffer
    Connection connection = DriverManager.getConnection("sniffer:jdbc:h2:~/test", "sa", "sa");
    connection.createStatement().execute("SELECT 1 FROM DUAL");
    // Sniffer.executedStatements() returns count of execute queries
    assertEquals(1, Sniffer.executedStatements());
    // Sniffer.verifyNotMoreThanOne() throws an IllegalStateException if more than one query was executed; it also resets the counter to 0
    Sniffer.verifyNotMoreThanOne();
    // Sniffer.verifyNotMore() throws an IllegalStateException if any query was executed
    Sniffer.verifyNotMore();
}
```

GPG
============
> gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys E8A7A294

-----BEGIN PGP SIGNATURE-----
Version: GnuPG v1

iQEbBAABAgAGBQJUjKoDAAoJEHs8iZrop6KUnGwH+LylA8nvKI91O2GZ9LCK8lbZ
DoGf6z6eSpBjJjWL0SCsE7oosG6EcuBUZIICvA6w1gO2IokUe8is1dCvf4WgAi0Y
xIiQ9ogAhHfvuE8y6izgIDJ6TcBVAgJMqzdC2XHDkPB0fkfUE4R3Vmd5IFVCObUD
tp5CQLtIDAfuacsHP4FEpglj9q9wSDkGHfdiizSYty7bCFYxuQ2N+EhJF0WPgQ0O
F0+lGY+QuB32f01wcU4KPspiiuWJcESulyXPRzWfMLHzOQZRSHI054JU0sm0oWNA
aYzR4LpXlmKkZfPn32qk2uZAWfr09/oMkCxQhx5U6rByMC0oE5giIKcmvBLeZg==
=Bm0g
-----END PGP SIGNATURE-----