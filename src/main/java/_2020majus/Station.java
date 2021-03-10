package _2020majus;

public class Station {

    private final String name;
    private final String time;
    private final String wind;
    private final int temp;

    public Station(String name, String time, String wind, int temp) {
        this.name = name;
        this.time = time;
        this.wind = wind;
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getWind() {
        return wind;
    }

    public int getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", wind='" + wind + '\'' +
                ", temp=" + temp +
                '}';
    }
}
