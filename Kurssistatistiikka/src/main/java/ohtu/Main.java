package ohtu;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        StudiesApi api = new StudiesApi();
        List<Submission> subs = api.getSubmissions(studentNr);
        List<CourseInfo> courses = api.getCourseInfos();

        SubmissionStatsPrinter printer = new SubmissionStatsPrinter(System.out);
        printer.print(studentNr, subs, courses);
    }
}