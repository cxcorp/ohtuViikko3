package ohtu.api;

import java.text.DecimalFormat;

public class CourseStats {
    private int totalSubmissions;
    private int totalExercises;
    private double totalHours;

    public CourseStats(int totalSubmissions, int totalExercises, double totalHours) {
        this.totalSubmissions = totalSubmissions;
        this.totalExercises = totalExercises;
        this.totalHours = totalHours;
    }

    public int getTotalSubmissions() {
        return totalSubmissions;
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public double getTotalHours() {
        return totalHours;
    }

    @Override
    public String toString() {
        return String.format(
            "kurssilla yhteensä %d palautusta, palautettuja tehtäviä %d kpl, aikaa käytetty yhteensä %s tuntia",
            totalSubmissions,
            totalExercises,
            new DecimalFormat("##.#").format(totalHours)
        );
    }
}
