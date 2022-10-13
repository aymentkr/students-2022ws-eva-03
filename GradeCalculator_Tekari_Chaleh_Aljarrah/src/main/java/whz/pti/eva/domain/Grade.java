package whz.pti.eva.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grade {

    @Id
    private String lecture;
    private String grade;

    public Grade(String lecture, String grade) {
        this.lecture=lecture;
        this.grade=grade;
    }
    public Grade() {

    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }
}
