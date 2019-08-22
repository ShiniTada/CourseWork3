package by.bsuir.client.repository.impl;

import by.bsuir.client.entity.User;
import by.bsuir.client.repository.Repository;


import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserRepository implements Repository<User> {
    private static UserRepository INSTANCE = new UserRepository();

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    private UserRepository() {
    }

    private SortedSet<User> users = new TreeSet<>();

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }
    @Override
    public User get(String login, String password) {
        User localUser = new User(login, password);

        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User userFromRepository = iter.next();
            if (userFromRepository.equals(localUser)) {
                return userFromRepository;
            }
        }
        return null;
    }

    @Override
    public User get(String login) {
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User userFromRepository = iter.next();
            if (userFromRepository.getLogin().equals(login)) {
                return userFromRepository;
            }
        }
        return null;
    }

    @Override
    public SortedSet<User> getAll() {
        return users;
    }
}
