package pv260.solid.dip.original;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeServiceImpl implements TimeService {

    @Override
    public LocalDate getTomorrow() {
        return LocalDate.now().plusDays(1);
    }

    @Override
    public boolean isTomorrowLunchTime(LocalDateTime from, LocalDateTime to) {
        LocalDateTime tomorrowLunchTime = getTomorrow().atTime(12, 0);
        return isBetweenInclusive(from, to, tomorrowLunchTime);

    }

    @Override
    public boolean isTomorrow(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(getTomorrow());
    }

    private boolean isBetweenInclusive(LocalDateTime localFrom, LocalDateTime localTo, LocalDateTime tomorrowLunchTime) {
        return (localFrom.isBefore(tomorrowLunchTime) ||
                localFrom.isEqual(tomorrowLunchTime)) &&
                (localTo.isAfter(tomorrowLunchTime) ||
                        localTo.isEqual(tomorrowLunchTime));
    }
}