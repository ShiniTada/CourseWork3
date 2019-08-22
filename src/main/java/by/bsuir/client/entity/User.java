package by.bsuir.client.entity;

import java.util.List;
import java.util.Objects;

public class User implements Comparable<User>{
    private String login, password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(List<String> loginPassword) {
        this.login = loginPassword.get(0);
        this.password = loginPassword.get(1);
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public int compareTo(User u) {
        return login.compareTo(u.getLogin());
    }
}
