package hanium.mobile.did_student.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("동덕여자대학교");
    }

    public LiveData<String> getText() {
        return mText;
    }
/*
    private MutableLiveData<Map> mText;

    public MainViewModel() {
        mText = new MutableLiveData<>();

        Map<String, String> map = null;
        map.put("school", "동덕여자대학교k");
        map.put("name", "이동선");
        map.put("major", "컴퓨터학과");
        map.put("num", "20161764");
        map.put("date", "2020.08.03");

        mText.setValue(map);
    }

    public LiveData<Map> getText() {
        return mText;
    }*/
}