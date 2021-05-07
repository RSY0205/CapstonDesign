package com.example.savehometraining.ui.Routine;

public class routineinfo {
    String name;
    void setName(String name){this.name=name;}
    String getName(){return this.name;}
    int set;
    void setSet(int set){this.set=set;}
    int getSet(){return this.set;}
    int count;
    void setCount(int count){this.count=count;}
    int getCount(){return this.count;}
    void setRoutineinfo(String name, int set,int count){
        this.name=name;
        this.set=set;
        this.count=count;
    }
}
