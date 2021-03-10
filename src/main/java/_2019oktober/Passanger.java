package _2019oktober;

public class Passanger {

    private final String station;
    private final String dateTime;
    private final String ticketsNr;
    private final String ticketsType;
    private final String validation;

    public Passanger(String station, String dateTime, String ticketsNr, String ticketsType, String validation) {
        this.station = station;
        this.dateTime = dateTime;
        this.ticketsNr = ticketsNr;
        this.ticketsType = ticketsType;
        this.validation = validation;
    }

    public String getStation() {
        return station;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTicketsNr() {
        return ticketsNr;
    }

    public String getTicketsType() {
        return ticketsType;
    }

    public String getValidation() {
        return validation;
    }

    @Override
    public String toString() {
        return "Passanger{" +
                "station=" + station +
                ", dateTime='" + dateTime + '\'' +
                ", ticketsNr='" + ticketsNr + '\'' +
                ", ticketsType='" + ticketsType + '\'' +
                ", validation='" + validation + '\'' +
                '}';
    }
}
