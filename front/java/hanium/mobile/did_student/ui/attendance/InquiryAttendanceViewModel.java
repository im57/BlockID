package hanium.mobile.did_student.ui.attendance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InquiryAttendanceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InquiryAttendanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}