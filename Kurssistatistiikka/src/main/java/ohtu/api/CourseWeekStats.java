package ohtu.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class CourseWeekStats {
    private int students;
    @SerializedName("hour_total")
    private double totalHours;
    @SerializedName("exercise_total")
    private int totalExercises;
    private List<Double> hours;
    private List<Integer> exercises;

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public void setTotalExercises(int totalExercises) {
        this.totalExercises = totalExercises;
    }

    public List<Double> getHours() {
        return hours;
    }

    public void setHours(List<Double> hours) {
        this.hours = hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }
}
