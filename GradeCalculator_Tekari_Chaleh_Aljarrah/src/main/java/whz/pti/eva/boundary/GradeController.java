package whz.pti.eva.boundary;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired private GradeService gradeService;

    /**
     * List all grades string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/grades")
    public String listAllGrades(Model model) {
        List<Grade> grades = gradeService.listAllGrades();
        model.addAttribute("grades", grades);
        model.addAttribute("average", gradeService.calculateAverage());
        return "grades";
    }

    /**
     * Add grade.
     *
     * @param lecture the lecture
     * @param grade   the grade
     * @return the string
     */
    @PostMapping("/addGrade")
    public String addGrade(@RequestParam String lecture, @RequestParam String grade) {
        gradeService.addGrade(lecture, grade);
        return "redirect:grades";
    }
}
