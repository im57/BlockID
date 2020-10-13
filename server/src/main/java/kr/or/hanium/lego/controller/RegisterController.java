package kr.or.hanium.lego.controller;

import kr.or.hanium.lego.service.dto.MailDto;
import kr.or.hanium.lego.service.EmailService;
import kr.or.hanium.lego.service.RegisterService;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import kr.or.hanium.lego.vm.SendEmailResultVM;
import kr.or.hanium.lego.vm.SendEmailVM;
import kr.or.hanium.lego.vm.SignupVM;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class RegisterController {
    private final RegisterService registerService;
    private final EmailService emailService;

    // 이메일 인증
    @PostMapping("/email/send")
    public SendEmailResultVM sendEmail(@RequestBody SendEmailVM request){
        MailDto mailDto = emailService.createEmail(request.getEmail());

        SendEmailResultVM sendEmailResultVM = new SendEmailResultVM();
        sendEmailResultVM.setAuthCode(emailService.sendAuthEmail(mailDto));

        return sendEmailResultVM;
    }

    // Holder 가입과 학생증 생성
    @PostMapping("/signup")
    public Long signUp(@RequestBody SignupVM request){
        Long holderId = registerService.saveHolder(request);
        Long stCardId = registerService.saveStudentIdCard(holderId, request);

        return holderId;
    }


}
