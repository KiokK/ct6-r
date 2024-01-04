package by.kihtenkoolga.util;

import by.kihtenkoolga.config.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static by.kihtenkoolga.util.FilesUtil.getFileFromResources;

@Component
public class DbScriptUtil {

    @Value("${datasource.init-tables-with-data}")
    private Boolean isInitDb;

    @Value("${datasource.init-scripts}")
    private String scriptFilesNames;

    public void initAndExecuteScriptsByProperties() {
        if (isInitDb) {
            String[] scriptFiles = scriptFilesNames.split(",");

            try (Connection connection = DataSource.getConnection()) {
                String script;
                for (String scriptFile : scriptFiles) {
                    script = readSQLScript(getFileFromResources(scriptFile));
                    executeScript(connection, script);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String readSQLScript(String filePath) {
        StringBuilder script = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return script.toString();
    }

    public static void executeScript(Connection connection, String script) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String[] sqlCommands = script.split(";");
            for (String sqlCommand : sqlCommands) {
                if (!sqlCommand.trim().isEmpty()) {
                    statement.addBatch(sqlCommand);
                }
            }
            statement.executeBatch();
        }
    }

}
