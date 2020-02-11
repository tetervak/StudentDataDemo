package ca.javateacher.studentdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value={"/", "/Index"})
    public String index(){
        return "Index";
    }
}
