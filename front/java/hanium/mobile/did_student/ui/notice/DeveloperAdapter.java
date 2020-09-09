//recycler 뷰 위한 어댑터

package hanium.mobile.did_student.ui.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import hanium.mobile.did_student.R;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.ItemViewHolder> {

    private ArrayList<Developer> developerList = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_developer, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(developerList.get(position));
    }

    @Override
    public int getItemCount() {
        return developerList.size();
    }

    void addItem(Developer developer) {
        // 외부에서 item을 추가시킬 함수입니다.
        developerList.add(developer);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvEmail;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.text_developer_name);
            tvEmail = itemView.findViewById(R.id.text_developer_email);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(Developer developer) {
            tvName.setText(developer.getName());
            tvEmail.setText(developer.getEmail());
//            imageView.setImageResource(R.drawable.developer3);
            //imageView.setImageResource(developer.getResId());
        }
    }
}
