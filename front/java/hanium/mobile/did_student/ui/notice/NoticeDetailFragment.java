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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import hanium.mobile.did_student.R;

public class NoticeDetailFragment extends Fragment {

    private NoticeViewModel noticeViewModel;

    private TextView tvTitle;
    private TextView tvDetail;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noticeViewModel =
                ViewModelProviders.of(this).get(NoticeViewModel.class);
        root = inflater.inflate(R.layout.fragment_notice_detail, container, false);

        tvTitle = root.findViewById(R.id.text_notice_title);
        tvDetail = root.findViewById(R.id.text_notice_detail);

        tvTitle.setText("버전1.0정보");
        tvDetail.setText("출석체크, 출석조회 가능");

    //    final TextView textView = root.findViewById(R.id.text_tools);
        noticeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
     //           textView.setText(s);
            }
        });
        return root;
    }
}