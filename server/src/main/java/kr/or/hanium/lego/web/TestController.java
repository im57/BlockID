package kr.or.hanium.lego.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // test Controller
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
