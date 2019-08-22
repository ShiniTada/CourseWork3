package by.bsuir.client.entity;

import java.io.Serializable;
import java.util.Map;

public class ServerDecision implements Serializable {
    private Map<String, Integer> firstBetterSecond;

    public ServerDecision(Map<String, Integer> firstBetterSecond) {
        this.firstBetterSecond = firstBetterSecond;
    }

    public Map<String, Integer> getFirstBetterSecond() {
        return firstBetterSecond;
    }

    @Override
    public String toString() {
        return "ServerDecision{" +
                "firstBetterSecond=" + firstBetterSecond +
                '}';
    }
}
