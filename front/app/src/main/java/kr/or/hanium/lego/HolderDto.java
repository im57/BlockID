package kr.or.hanium.lego;

import java.io.Serializable;

public class HolderDto implements Serializable {
    private long id;
    private String holder_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHolder_id() {
        return holder_id;
    }

    public void setHolder_id(String holder_id) {
        this.holder_id = holder_id;
    }
}
