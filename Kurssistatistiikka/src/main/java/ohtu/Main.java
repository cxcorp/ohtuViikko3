package ohtu;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String studentNr = "014709290";
        if (args.length > 0) {
            studentNr = args[0];
        }

        List<Submission> subs = new StudiesApi().getSubmissions(studentNr);

        System.out.printf("opiskelijanumero %s\n\n", studentNr);

        for (Submission submission : subs) {
            System.out.printf(" %s\n", submission);
        }

        System.out.println();
        System.out.printf(
                "yhteensä: %d tehtävää, %.1f tuntia\n",
                countExercises(subs),
                countHours(subs)
        );
    }

    private static int countExercises(List<Submission> submissions) {
        return submissions
                .stream()
                .map(submission -> submission.getExercises().size())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static double countHours(List<Submission> submissions) {
        return submissions
                .stream()
                .map(Submission::getHours)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}