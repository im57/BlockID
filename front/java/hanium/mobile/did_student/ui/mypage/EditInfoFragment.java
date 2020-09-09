//회원정보 수정 fragment (비밀번호 변경, 탈퇴 선택 창)

package hanium.mobile.did_student.ui.mypage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import hanium.mobile.did_student.MainActivity;
import hanium.mobile.did_student.R;

public class EditInfoFragment extends Fragment {

    private EditInfoViewModel editInfoViewModel;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private ListView lvMypage = null;

    private AlertDialog.Builder builder;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editInfoViewModel =
                ViewModelProviders.of(this).get(EditInfoViewModel.class);
        root = inflater.inflate(R.layout.fragment_mypage_edit, container, false);

        lvMypage = root.findViewById(R.id.list_mypage);

        list = new ArrayList<>();
        list.add("비밀번호 변경");
        list.add("서비스 탈퇴");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        lvMypage.setAdapter(adapter);

        lvMypage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if(position == 0) {
                    Navigation.findNavController(view).navigate(R.id.action_mypage_to_password);
                } else if (position == 1) {
                    builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("서비스를 탈퇴하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Navigation.findNavController(view).navigate(R.id.action_mypage_to_password_withdraw);
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                }
            }
        });

        //final TextView textView = root.findViewById(R.id.text_gallery);
        editInfoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}