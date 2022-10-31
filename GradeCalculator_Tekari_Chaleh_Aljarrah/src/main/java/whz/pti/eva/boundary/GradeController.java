package whz.pti.eva.boundary;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import whz.pti.eva.domain.Grade;
import whz.pti.eva.service.GradeService;

import java.util.List;

/**
 * The type Chat controller.
 */
@Controller
public class GradeController {
    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * List all grades string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/grades")
    List<Grade> listAllGrades(Model model) {
        List<Grade> grades = gradeService.listAllGrades();
        model.addAttribute("grades", grades);
        model.addAttribute("average", gradeService.calculateAverage());
        return gradeService.listAllGrades();
    }
    @PostMapping("/addGrade")
    public void addGrade(@RequestParam String lecture, @RequestParam String grade) {
        gradeService.addGrade(lecture, grade);
    }
}
