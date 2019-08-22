package by.bsuir.client.entity;

import java.io.Serializable;
import java.util.List;

public class ExpertsDecision implements Serializable {
    private List<Integer> opinions;

    public ExpertsDecision(List<Integer> opinions) {
        this.opinions = opinions;
    }

    public List<Integer> getOpinions() {
        return opinions;
    }

    @Override
    public String toString() {
        return "ExpertsDecision{" +
                "opinions=" + opinions +
                '}';
    }
}
