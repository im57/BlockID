package kr.or.hanium.lego.controller;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "QR코드 출석체크")
    @PostMapping("")
    public Long addAttendance(@RequestBody AddAttendanceVM request) {
        Long attendanceId = attendanceService.addAttendance(request);

        return attendanceId;
    }

    @ApiOperation(value = "출석 내역 조회")
    @GetMapping("/list")
    public List<FetchAttendanceResultVM> fetchAttendance(@RequestBody AddAttendanceVM request){
        List<FetchAttendanceResultVM> attendanceVMList = attendanceService.fetchAttendanceList(request);
        return attendanceVMList;
    }


}
