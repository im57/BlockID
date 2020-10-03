package kr.or.hanium.lego.controller;

import kr.or.hanium.lego.vm.FetchAttendanceResultVM;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @PostMapping("/qr/{class_id}")
    public String addAttendance(@PathVariable Long class_id) {
        return "자했다";
    }

    @GetMapping("/{class_id}/list")
    public FetchAttendanceResultVM fetchAttendance(@PathVariable Long id){
        return new FetchAttendanceResultVM();
    }


}
