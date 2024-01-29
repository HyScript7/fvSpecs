package me.hyscript7.fvspecs.datastore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SQLiteFileManager {

    private final Logger logger;
    private final String filePath;

    public SQLiteFileManager(Logger logger, String filePath) {
        this.logger = logger;
        this.filePath = filePath;

        File directory = new File(filePath).getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                logger.severe("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
        try {
            // Makes sure the file is created
            Connection c = getConnection();
            c.close();
        } catch (SQLException e) {
            logger.severe("Unable to open a test connection, it is likely the database file couldn't be created. Propagating RuntimeException.");
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + filePath);
    }

    public void close() {
        logger.info("Closing SQLite file: " + filePath);
    }
}

