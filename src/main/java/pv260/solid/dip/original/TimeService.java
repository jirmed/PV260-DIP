package pv260.solid.dip.original;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeService {

    LocalDate getTomorrow();

    boolean isTomorrowLunchTime(LocalDateTime from, LocalDateTime to);

    boolean isTomorrow(LocalDateTime dateTime);

}
