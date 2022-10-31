package whz.pti.eva.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The type Grade.
 */
@Entity
public class Grade {

    @Id
    private String lecture;
    private String grade;

    /**
     * Instantiates a new Grade.
     *
     * @param lecture the lecture
     * @param grade   the grade
     */
    public Grade(String lecture, String grade) {
        this.lecture=lecture;
        this.grade=grade;
    }

    /**
     * Instantiates a new Grade.
     */
    public Grade() {

    }

    /**
     * Gets grade.
     *
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Gets lecture.
     *
     * @return the lecture
     */
    public String getLecture() {
        return lecture;
    }

}
