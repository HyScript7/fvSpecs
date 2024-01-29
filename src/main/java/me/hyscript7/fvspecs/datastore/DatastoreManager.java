package me.hyscript7.fvspecs.datastore;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatastoreManager {
    private final Logger logger;
    private final String databaseFilePath;
    private final SQLiteFileManager fileManager;
    private final SQLiteQueryExecutor queryExecutor;

    public DatastoreManager(JavaPlugin plugin) {
        this.logger = plugin.getLogger();
        this.databaseFilePath = plugin.getDataFolder().getAbsolutePath() + File.separator + "data.db";

        this.fileManager = new SQLiteFileManager(logger, databaseFilePath);
        this.queryExecutor = new SQLiteQueryExecutor(logger, databaseFilePath);

        createDatabaseSchema();
    }

    public SQLiteFileManager getFileManager() {
        return fileManager;
    }

    public SQLiteQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    private void createDatabaseSchema() {
        /*
        try {
            queryExecutor.executeQuery("CREATE TABLE IF NOT EXISTS player_data (" +
                    "uuid TEXT PRIMARY KEY," +
                    "level INT," +
                    "prestige INT," +
                    "lives INT," +
                    "exp INT," +
                    "joined INTEGER" +
                    ");");
            logger.info("player_data table has been created or it has already existed!");
        } catch (SQLException e) {
            logger.warning("player_data table couldn't be created!");
        }
        */
    }
}

