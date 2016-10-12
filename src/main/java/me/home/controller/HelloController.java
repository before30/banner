package me.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by before30 on 2016. 10. 12..
 */
@Controller
public class HelloController {


//    @Autowired
//    private Environment environment;
//
//    @Autowired
//    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    @ResponseBody
    public String welcome() {

//        VitaminBannerPrinter vitaminBannerPrinter = new VitaminBannerPrinter(resourceLoader);
//        vitaminBannerPrinter.print(environment, System.out);

        return "Hello World";
    }
}
