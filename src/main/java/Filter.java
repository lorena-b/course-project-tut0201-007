/**
 * An interface that will be responsible for checking schedules
 * based on given specifications.
 */
public interface Filter {
    public Schedule checkSchedule(Schedule s);
}