package ohtu;

import com.google.common.base.Joiner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Submission {
    private int week;
    private double hours;
    private List<Integer> exercises;
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {

        return String.format(
            "%s, viikko %d tehtyjä tehtäviä yhteensä %d, aikaa kului %.1f tuntia, tehdyt tehtävät: %s",
            course,
            week,
            exercises.size(),
            hours,
            formatExercises()
        );
    }

    private String formatExercises() {
        return Joiner.on(", ").join(exercises);
    }
}