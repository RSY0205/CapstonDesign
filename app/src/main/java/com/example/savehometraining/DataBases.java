package com.example.savehometraining;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns {

        public static final String EXERCISE = "exercise";
        public static final String COUNT_MIN = "count_min";
        public static final String SET_SEC = "set_sec";
        public static final String ROUTINE_NAME = "routine_name";
        public static final String ID = "id";

        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +ID+" integer primary key autoincrement, "
                +EXERCISE+" text not null , "
                +COUNT_MIN+" text not null , "
                +SET_SEC+" text not null , "
                +ROUTINE_NAME+" text not null );";
    }
}
