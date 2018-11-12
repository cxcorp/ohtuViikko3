package ohtu;

import ohtu.api.Submission;

import java.util.Collection;

public class SubmissionStatsCalculator {
    public int countDoneExercises(Collection<Submission> subs) {
        return subs
            .stream()
            .map(submission -> submission.getExercises().size())
            .mapToInt(Integer::intValue)
            .sum();
    }

    public double countUsedHours(Collection<Submission> subs) {
        return subs
            .stream()
            .map(Submission::getHours)
            .mapToDouble(Double::doubleValue)
            .sum();
    }
}
