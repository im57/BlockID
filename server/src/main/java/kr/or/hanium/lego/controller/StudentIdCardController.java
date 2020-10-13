package kr.or.hanium.lego.controller;

import kr.or.hanium.lego.service.RegisterService;
import kr.or.hanium.lego.service.StudentIdCardService;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/idcard")
public class StudentIdCardController {
    private final RegisterService registerService;
    private final StudentIdCardService studentIdCardService;

    @GetMapping("/{id}")
    public FetchIdcardResultVM fetchStudentIdCard(@PathVariable("id") Long holder_id){
        FetchIdcardResultVM fetchIdcardResultVM = studentIdCardService.fetchStudentIdCardWithHolderId(holder_id);

        return fetchIdcardResultVM;
    }

    @PutMapping("/activate/{id}")
    public FetchIdcardResultVM activateStudentIdCard(@PathVariable("id") Long holder_id) {
        LocalDateTime expireDate = registerService.getExpireDate(LocalDateTime.now());
        FetchIdcardResultVM fetchIdcardResultVM = studentIdCardService.activateStudentIdCardWithHolderId(holder_id, expireDate);

        return fetchIdcardResultVM;
    }

}
