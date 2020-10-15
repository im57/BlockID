package kr.or.hanium.lego.vm;


import kr.or.hanium.lego.domain.enumeration.AttendanceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FetchAttendanceResultVM {
    private String status;
    private LocalDateTime time;

    public FetchAttendanceResultVM(String status, LocalDateTime time) {
        this.status = status;
        this.time = time;
    }
}
