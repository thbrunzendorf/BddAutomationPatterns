package bddautomationpatterns.geekpizza.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileDataPersist implements DataPersist {
    private Path databaseFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "GeekPizzaDbJava.json");

    @Override
    public void save(String data) throws Exception {
        Files.write(databaseFilePath, Arrays.asList(data), StandardCharsets.UTF_8);
    }

    @Override
    public String load() throws Exception {

        List<String> lines = Files.readAllLines(databaseFilePath, StandardCharsets.UTF_8);
        return String.join(System.lineSeparator(), lines);
    }
}
