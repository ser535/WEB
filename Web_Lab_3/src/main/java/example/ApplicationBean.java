package example;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ApplicationBean {
    public String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        example.db.DatabaseConnection.initSchema();
        return LocalDateTime.now().format(formatter);
    }
}