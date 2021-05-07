package com.example.savehometraining.Login;

public class Preperence_item {
    boolean checked;
    String ItemString;
    Preperence_item(boolean b, String t){
        checked=b;
        ItemString=t;
    }

    public boolean isChecked(){
        return checked;
    }

}
