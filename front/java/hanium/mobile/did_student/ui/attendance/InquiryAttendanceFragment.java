//출석조회 fragment

package hanium.mobile.did_student.ui.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import hanium.mobile.did_student.R;

public class InquiryAttendanceFragment extends Fragment {

    private Spinner spinner;

    private ListView lvAttendance = null;
    private ArrayList<String> attendanceList;
    private ArrayList<String> subjectList;

    private AttendanceAdapter attendanceAdapter;
    private ArrayAdapter<String> subjectAdapter;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_attendance_inquiry, container, false);

        subjectList = new ArrayList<>();
        subjectList.add("수업을 선택해주세요");
        subjectList.add("프로그래밍 논리의 이해");
        subjectList.add("임베디드 시스템");
        subjectList.add("데이터베이스 프로그래밍");

        attendanceList = new ArrayList();

        spinner = (Spinner)root.findViewById(R.id.spinner_subject);
        subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, subjectList);
        spinner.setAdapter(subjectAdapter);

        lvAttendance = root.findViewById(R.id.list_attendance);
        attendanceAdapter = new AttendanceAdapter(getActivity(), R.layout.listview_attendance, attendanceList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           //과목 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attendanceList.clear();

                if(position != 0) {
                    attendanceList.add("2020-07-29 09:08");
                    attendanceList.add("2020-07-30 09:02");
                    attendanceList.add("2020-07-31 08:58");
                    attendanceList.add(parent.getItemAtPosition(position).toString());
                }

                attendanceAdapter.setList(attendanceList);
                lvAttendance.setAdapter(attendanceAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }
}