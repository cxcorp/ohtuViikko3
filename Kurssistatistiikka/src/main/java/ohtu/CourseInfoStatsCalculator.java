package ohtu;

import ohtu.api.CourseInfo;

public class CourseInfoStatsCalculator {
    public int countAllExercises(CourseInfo course) {
        // assumption: no exercise is null
        return course.getExercises()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    public int countExercisesOfWeek(CourseInfo course, int week) {
        // assumption: week < exercises.size()
        return course.getExercises().get(week);
    }
}
