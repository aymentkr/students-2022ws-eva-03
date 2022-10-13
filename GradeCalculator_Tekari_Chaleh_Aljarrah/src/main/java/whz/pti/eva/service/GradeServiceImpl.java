package whz.pti.eva.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.Grade;
import whz.pti.eva.domain.GradeRepository;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    private static final Logger log = LoggerFactory.getLogger(GradeServiceImpl.class);

    private GradeRepository gradeRepository;

    @Autowired
    public void GardeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Grade> listAllGrades() {
        return gradeRepository.findAll();
    }


    @Override
    public void addGrade(String lecture, String grade) {
        gradeRepository.save(new Grade(lecture,grade));
    }

    @Override
    public double calculateAverage() {
        return listAllGrades().stream()
                .mapToDouble(g -> Double.parseDouble(g.getGrade()))
                .average()
                .orElse(0.0);
    }
}
