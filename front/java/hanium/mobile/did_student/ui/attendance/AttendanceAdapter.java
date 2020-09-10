//출석 리스트 위한 어댑터

package hanium.mobile.did_student.ui.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hanium.mobile.did_student.R;

public class AttendanceAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<String> list = new ArrayList<String>();

    public AttendanceAdapter(Context context, int layout, ArrayList<String> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAttendance = view.findViewById(R.id.text_attendance_date);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.tvAttendance.setText(list.get(i));

        return view;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    static class ViewHolder {
        public TextView tvAttendance = null;
    }
}
