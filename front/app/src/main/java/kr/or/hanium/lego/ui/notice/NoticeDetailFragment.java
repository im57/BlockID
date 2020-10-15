//공지사항 fragment

package kr.or.hanium.lego.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class NoticeDetailFragment extends Fragment {

    private TextView tvTitle;
    private TextView tvDetail;

    private View root;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notice_detail, container, false);

        activity = (MainActivity)getActivity();

        tvTitle = root.findViewById(R.id.text_notice_title);
        tvDetail = root.findViewById(R.id.text_notice_detail);

        tvTitle.setText("버전1.0정보");
        tvDetail.setText("출석체크, 출석조회 가능");

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(null);
    }
}