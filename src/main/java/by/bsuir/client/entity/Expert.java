package by.bsuir.client.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expert {
    private List<Integer> opinions = new ArrayList<>();


    public Expert() {
    }

    public void addOpinion(Integer opinion){
            for (int o : opinions) {
                if (o == opinion) {
                    return;
                }
            }
        opinions.add(opinion);
    }

    public int getOpinion(int index) {
       return opinions.get(index);
    }

    public List<Integer> getAllOpinions() {
        return opinions;
    }

    public void removeAll(){
        List<Integer> copy = new ArrayList<>(opinions);
        opinions.removeAll(copy);
    }

    public int amountOpinions() {
        return opinions.size();
    }

    @Override
    public String toString() {
        return "Expert{" +
                "opinions=" + opinions +
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
        Expert expert = (Expert) o;
        return Objects.equals(opinions, expert.opinions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opinions);
    }
}
