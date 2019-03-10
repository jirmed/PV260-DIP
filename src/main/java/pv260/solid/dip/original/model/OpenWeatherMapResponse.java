package pv260.solid.dip.original.model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

@XmlRootElement(name = "weatherdata")
@XmlAccessorType(NONE)
public class OpenWeatherMapResponse {

    @XmlElement
    private Location location;

    @XmlElement(name = "time")
    @XmlElementWrapper(name = "forecast")
    private List<ForecastTime> times;

    public Location getLocation() {
        return location;
    }

    public List<ForecastTime> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return "OpenWeatherMapResponse{" + "location=" + location + ", times=" + times + '}';
    }

    @XmlType
    public static class Location {
        @XmlElement
        private String name;
        @XmlElement
        private String country;

        @Override
        public String toString() {
            return "Location{" + "name=" + name + ", country=" + country + '}';
        }
    }

    @XmlType
    public static class ForecastTime {

        @XmlAttribute
        @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
        private LocalDateTime from;

        @XmlAttribute
        @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
        private LocalDateTime to;

        @XmlElement
        private Temperature temperature;

        public LocalDateTime getFrom() {
            return from;
        }

        public LocalDateTime getTo() {
            return to;
        }


        public LocalDateTime getLocalDateTime() {
            return from;
        }

        public Temperature getTemperature() {
            return temperature;
        }

        @Override
        public String toString() {
            return "ForecastTime{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", temperature=" + temperature +
                    '}';
        }
    }

    @XmlType
    public static class Temperature {
        @XmlAttribute
        private String unit;

        @XmlAttribute
        private double value;

        @XmlAttribute
        private double min;

        @XmlAttribute
        private double max;

        public String getUnit() {
            return unit;
        }

        public double getValue() {
            return value;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        @Override
        public String toString() {
            return "Temperature{" +
                    "unit='" + unit + '\'' +
                    ", value=" + value +
                    ", min=" + min +
                    ", max=" + max +
                    '}';
        }
    }
}
