package kr.or.hanium.lego.vm;

import lombok.Data;

@Data
public class FetchClassesResultVM {
    private String name;

    public FetchClassesResultVM(String name) {
        this.name= name;
    }
}
