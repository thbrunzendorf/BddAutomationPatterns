package bddautomationpatterns.geekpizza.repository;

import java.io.IOException;

public interface DataPersist {
    void save(String data) throws Exception;
    String load() throws Exception;
}
