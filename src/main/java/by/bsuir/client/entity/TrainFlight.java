package by.bsuir.client.entity;

import java.util.List;
import java.util.Objects;

public class TrainFlight implements Comparable<TrainFlight> {
    private int number;
    private TrainTypeEnum type;
    private String route;
    private String departure;
    private  String arrival;

    public TrainFlight() {
    }

    public TrainFlight(int number, TrainTypeEnum type, String route, String departure, String arrival) {
        this.number = number;
        this.type = type;
        this.route = route;
        this.departure = departure;
        this.arrival = arrival;
    }

    public TrainFlight(List<String> data) {
        this.number = Integer.parseInt(data.get(0));
        this.type = TrainTypeEnum.findEnum(data.get(1));
        this.route = data.get(2);
        this.departure = data.get(3);
        this.arrival = data.get(4);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return value of Train flight type
     */
    public String getType() {
        return type.getValue();
    }

    /**
     * Get value of Train flight and set {@TrainTypeEnum type}
     * @param typeValue - value of Train flight type
     */
    public void setType(String typeValue) {
        this.type = TrainTypeEnum.findEnum(typeValue);
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return "TrainFlight{" +
                "number=" + number +
                ", type=" + type +
                ", route='" + route + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival +
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
        TrainFlight that = (TrainFlight) o;
        return number == that.number &&
                type == that.type &&
                Objects.equals(route, that.route) &&
                Objects.equals(departure, that.departure) &&
                Objects.equals(arrival, that.arrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, route, departure, arrival);
    }


    @Override
    public int compareTo(TrainFlight t) {
        return String.valueOf(number).compareTo(String.valueOf(t.number));
    }
}

