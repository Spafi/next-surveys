package com.spaf.surveys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class IndexResource {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
