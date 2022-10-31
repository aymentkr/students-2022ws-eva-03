package whz.pti.eva.service;

import whz.pti.eva.domain.Grade;
import java.util.List;

/**
 * The interface Grade service.
 */
public interface GradeService {
    /**
     * List all grades list.
     *
     * @return the list
     */
    List<Grade> listAllGrades();

    /**
     * Add grade.
     *
     * @param lecture the lecture
     * @param grade   the grade
     */
    void addGrade(String lecture,String grade);

    /**
     * Calculate average double.
     *
     * @return the double
     */
    double calculateAverage();
}
