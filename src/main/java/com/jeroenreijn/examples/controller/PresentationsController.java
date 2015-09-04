package com.jeroenreijn.examples.controller;

import com.jeroenreijn.examples.services.PresentationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/")
public class PresentationsController {

    @Autowired
    PresentationsService presentationsService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(final ModelMap modelMap) {
        return showList("jsp", null, modelMap);
    }

    @RequestMapping(value = "{template}", method = RequestMethod.GET)
    public String showList(@PathVariable(value = "template") final String template,
                           HttpServletResponse httpServletResponse,
                           final ModelMap model) {
        if (template.equals("jsocle")) {
            try {
                final PrintWriter writer = httpServletResponse.getWriter();
                ControllerPackage.view(presentationsService.findAll()).render(writer);
                writer.close();
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("presentations", presentationsService.findAll());
        return "index-" + template;
    }

}