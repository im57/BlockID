package kr.or.hanium.lego.controller;

import kr.or.hanium.lego.service.AttendanceService;
import kr.or.hanium.lego.vm.AddAttendanceVM;
import kr.or.hanium.lego.vm.FetchAttendanceResultVM;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("")
    public Long addAttendance(@RequestBody AddAttendanceVM request) {
        Long attendanceId = attendanceService.addAttendance(request);

        return attendanceId;
    }

    @GetMapping("/list")
    public List<FetchAttendanceResultVM> fetchAttendance(@RequestBody AddAttendanceVM request){
        List<FetchAttendanceResultVM> attendanceVMList = attendanceService.fetchAttendanceList(request);
        return attendanceVMList;
    }


}
