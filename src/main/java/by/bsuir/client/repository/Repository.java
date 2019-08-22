package by.bsuir.client.repository;

import java.util.SortedSet;

public interface Repository<T> {

    void add(T t);

    void remove(T t);

    T get(String login, String password);

    T get(String identification);

    SortedSet<T> getAll();

}
