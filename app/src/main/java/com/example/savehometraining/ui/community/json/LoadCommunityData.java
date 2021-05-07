package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class LoadCommunityData {
        @SerializedName("User_id")
        private String User_id;

        public LoadCommunityData(String User_id){
            this.User_id=User_id;
        }

}
