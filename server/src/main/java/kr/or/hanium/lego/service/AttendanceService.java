package kr.or.hanium.lego.service;

import kr.or.hanium.lego.domain.Attendance;
import kr.or.hanium.lego.domain.Class;
import kr.or.hanium.lego.domain.Holder;
import kr.or.hanium.lego.domain.enumeration.AttendanceStatus;
import kr.or.hanium.lego.repository.AttendanceRepository;
import kr.or.hanium.lego.repository.ClassRepository;
import kr.or.hanium.lego.repository.HolderRepository;
import kr.or.hanium.lego.vm.AddAttendanceVM;
import kr.or.hanium.lego.vm.FetchAttendanceResultVM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ClassRepository classRepository;
    private final HolderRepository holderRepository;

    public Long addAttendance(AddAttendanceVM request) {
        Class class_info = classRepository.getOne(request.getClass_id());

        Attendance newAttendance = new Attendance();
        newAttendance.setHolder_id(request.getHolder_id());
        newAttendance.setClass_id(request.getClass_id());
        newAttendance.setTime(LocalDateTime.now());
        newAttendance.setStatus(getAttendanceStatus(class_info.getStart_time(), class_info.getEnd_time()));

        Attendance addedAttendance = attendanceRepository.save(newAttendance);

        return addedAttendance.getId();
    }

    private AttendanceStatus getAttendanceStatus(LocalTime start, LocalTime end) {
        LocalTime now = LocalTime.now();

        if (now.isBefore(start) || now.equals(start)) {
            System.out.println(now.isBefore(start));
            System.out.println(now.equals(start));
            return AttendanceStatus.PRESENT;
        } else if (now.isBefore(end)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }

    public List<FetchAttendanceResultVM> fetchAttendanceList(AddAttendanceVM request) {
        Holder holder = holderRepository.getOne(request.getHolder_id());

        List<FetchAttendanceResultVM> attendanceVMList = holder.getAttendanceList().stream()
                .filter(x -> x.getClass_id() == request.getClass_id())
                .map(x -> new FetchAttendanceResultVM(x.getStatus().getKrName(), x.getTime()))
                .collect(Collectors.toList());

        return attendanceVMList;
    }
}