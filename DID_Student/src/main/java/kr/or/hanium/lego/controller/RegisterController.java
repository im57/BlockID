package kr.or.hanium.lego.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.hanium.lego.vm.CheckEmailVM;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import kr.or.hanium.lego.vm.SendEmailVM;
import kr.or.hanium.lego.vm.SignupVM;

@RestController
@RequestMapping("/users")
public class RegisterController {
    @PostMapping("/email/send")
    public String sendEmail(@RequestBody SendEmailVM request){
        return "잘했다.";
    }

    @PostMapping("/email/check")
    public String checkEmail(@RequestBody CheckEmailVM request){
        return "잘했다.";
    }

    @GetMapping("/idcard/{id}")
    public FetchIdcardResultVM fetchIdcard(@PathVariable Long id){
        return new FetchIdcardResultVM();
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupVM request){
        return "잘했다.";
}


}
