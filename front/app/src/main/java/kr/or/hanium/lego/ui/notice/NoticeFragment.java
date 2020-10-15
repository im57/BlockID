//공지사항 fragment

package kr.or.hanium.lego.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class NoticeFragment extends Fragment {

    Spinner spinner;

    private ListView lvNotice = null;
    private ArrayList<String> noticeDetailList;
    private ArrayList<String> noticeList;

    private ArrayAdapter<String>  noticeDetailAdapter;
    private ArrayAdapter<String> noticeAdapter;

    private View root;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notice, container, false);

        activity = (MainActivity)getActivity();

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

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(null);
    }
}