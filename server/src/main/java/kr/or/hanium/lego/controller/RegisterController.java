package kr.or.hanium.lego.controller;


import kr.or.hanium.lego.vm.CheckEmailVM;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import kr.or.hanium.lego.vm.SendEmailVM;
import kr.or.hanium.lego.vm.SignupVM;
import org.springframework.web.bind.annotation.*;

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
