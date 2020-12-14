package ch.ost.rj.sa.miro2cml.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportedTemplatesViewController {
    @GetMapping(path = "/SupportedTemplates")
    public String getSupportedTemplates(Model model) {
        model.addAttribute("module", "supportedTemplates");
        return "SupportedTemplates";
    }
}
