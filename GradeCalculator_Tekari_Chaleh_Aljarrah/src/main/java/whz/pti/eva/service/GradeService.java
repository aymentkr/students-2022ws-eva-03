package whz.pti.eva.service;

import whz.pti.eva.domain.Grade;
import java.util.List;

public interface GradeService {
	
    List<Grade> listAllGrades();
    void addGrade(String lecture,String grade);
    double calculateAverage();
}
