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

    /**
     * Instantiates a new Grade controller.
     *
     * @param gradeService the grade service
     */
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * List all grades string.
     *
     * @param model the model
     * @return the string
     */
    //@RequestMapping(value = "/grades", method = RequestMethod.GET) OR
    //@RequestMapping("/grades") OR
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
    //@RequestMapping(value = "/addGrade", method = RequestMethod.POST) OR
    @PostMapping("/addGrade")
    public String addGrade(@RequestParam String lecture, @RequestParam String grade) {
        gradeService.addGrade(lecture, grade);
        return "redirect:grades";
    }
}
