package me.hyscript7.fvspecs.datastore;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class SQLiteQueryExecutor {

    private final Logger logger;
    private final SQLiteFileManager fileManager;
    private Connection connection;
    private boolean inTransaction;

    public SQLiteQueryExecutor(Logger logger, String filePath) {
        this.logger = logger;
        this.fileManager = new SQLiteFileManager(logger, filePath);
        this.inTransaction = false;
    }

    public @Nullable ResultSet executeQuery(String query) throws SQLException {
        try {
            beginTransaction();
            try (Statement statement = getConnection().createStatement()) {
                statement.execute(query);
                commitTransaction();
                return statement.getResultSet();
            }
        } catch (SQLException e) {
            rollbackTransaction();
            throw e; // Let the exception propagate after rolling back
        }
    }

    public void beginTransaction() throws SQLException {
        if (!inTransaction) {
            getConnection().setAutoCommit(false);
            inTransaction = true;
        }
    }

    public void commitTransaction() throws SQLException {
        if (inTransaction) {
            getConnection().commit();
            getConnection().setAutoCommit(true);
            inTransaction = false;
        }
    }

    public void rollbackTransaction() {
        try {
            if (inTransaction) {
                getConnection().rollback();
                getConnection().setAutoCommit(true);
                inTransaction = false;
            }
        } catch (SQLException e) {
            logger.severe("Error rolling back transaction: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = fileManager.getConnection();
        }
        return connection;
    }

    public void close() {
        fileManager.close();
    }
}
