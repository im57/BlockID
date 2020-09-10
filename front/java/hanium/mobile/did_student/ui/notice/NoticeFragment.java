//공지사항 fragment

package hanium.mobile.did_student.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import hanium.mobile.did_student.R;

public class NoticeFragment extends Fragment {

    Spinner spinner;

    ListView lvNotice = null;
    ArrayList<String> noticeDetailList;
    ArrayList<String> noticeList;

    ArrayAdapter<String>  noticeDetailAdapter;
    ArrayAdapter<String> noticeAdapter;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notice, container, false);

        noticeList = new ArrayList<>();
        noticeList.add("버전정보");
        noticeList.add("이용방법");
        noticeList.add("기타");

        noticeDetailList = new ArrayList();

        spinner = (Spinner)root.findViewById(R.id.spinner_notice);
        noticeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, noticeList);
        spinner.setAdapter(noticeAdapter);

        lvNotice = root.findViewById(R.id.list_notice);
        noticeDetailAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, noticeDetailList);
        lvNotice.setAdapter(noticeDetailAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //과목 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noticeDetailList.clear();

                noticeDetailList.add("버전 1.0 정보");
                noticeDetailList.add("버전 1.1 정보");
                noticeDetailList.add("버전 2.0 정보");
                noticeDetailList.add(parent.getItemAtPosition(position).toString());

                noticeDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Navigation.findNavController(root).navigate(R.id.action_notice_to_detail);
            }
        });

        return root;
    }
}