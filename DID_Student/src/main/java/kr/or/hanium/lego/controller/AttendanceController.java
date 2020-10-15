package kr.or.hanium.lego.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.hanium.lego.vm.FetchAttendanceResultVM;

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
