//출석 리스트 위한 어댑터

package kr.or.hanium.lego.ui.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.or.hanium.R;

public class AttendanceAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<Attendance> list = new ArrayList<Attendance>();

    public AttendanceAdapter(Context context, int layout, ArrayList<Attendance> list) {
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
            viewHolder.tvAttendanceDate = view.findViewById(R.id.text_attendance_date);
            viewHolder.tvAttendanceState = view.findViewById(R.id.text_attendance_state);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        Attendance attendance = list.get(i);

        viewHolder.tvAttendanceDate.setText(attendance.getAttendance_time());
        viewHolder.tvAttendanceState.setText(attendance.getAttendance_state());

        return view;
    }

    public void setList(ArrayList<Attendance> list) {
        this.list = list;
    }

    static class ViewHolder {
        public TextView tvAttendanceDate = null;
        public TextView tvAttendanceState = null;
    }
}
