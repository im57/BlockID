//개발자 소개 fragment

package hanium.mobile.did_student.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hanium.mobile.did_student.R;

public class DeveloperFragment extends Fragment {

    private DeveloperViewModel developerViewModel;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private DeveloperAdapter adapter;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        developerViewModel =
                ViewModelProviders.of(this).get(DeveloperViewModel.class);
        root = inflater.inflate(R.layout.fragment_developer, container, false);

        recyclerView = root.findViewById(R.id.recycler_developer);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DeveloperAdapter();
        adapter.addItem(new Developer("이동선", "", 1));
        adapter.addItem(new Developer("최예원", "", 2));
        adapter.addItem(new Developer("임윤숙", "eovhehd1986@gmail.com", 3));

        recyclerView.setAdapter(adapter);

        // final TextView textView = root.findViewById(R.id.text_tools);
        developerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}