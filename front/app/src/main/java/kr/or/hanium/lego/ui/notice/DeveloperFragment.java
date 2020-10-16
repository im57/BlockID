//개발자 소개 fragment

package kr.or.hanium.lego.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class DeveloperFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private DeveloperAdapter adapter;

    private View root;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_developer, container, false);

        activity = (MainActivity)getActivity();

        recyclerView = root.findViewById(R.id.recycler_developer);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DeveloperAdapter();
        adapter.addItem(new Developer("이동선", "aaa111@gmail.com", 1));
        adapter.addItem(new Developer("최예원", "aaa222@gmail.com", 2));
        adapter.addItem(new Developer("임윤숙", "aaa333@gmail.com", 3));

        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(null);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.closeFab();
    }
}