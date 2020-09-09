package hanium.mobile.did_student.ui.notice;

public class Developer {

    private String name;
    private String email;
    private int resId;

    public Developer(String name, String email, int resId){
        this.name = name;
        this.email = email;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
