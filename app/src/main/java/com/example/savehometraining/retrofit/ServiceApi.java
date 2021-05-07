package com.example.savehometraining.retrofit;

import com.example.savehometraining.Login.json.CheckIdData;
import com.example.savehometraining.Login.json.CheckIdResponse;
import com.example.savehometraining.Login.json.CheckNicknameData;
import com.example.savehometraining.Login.json.CheckNicknameResponse;
import com.example.savehometraining.Login.json.ExData;
import com.example.savehometraining.Login.json.ExDatae;
import com.example.savehometraining.Login.json.ExResponse;
import com.example.savehometraining.Login.json.ExResponsea;
import com.example.savehometraining.Login.json.JoinData;
import com.example.savehometraining.Login.json.JoinResponse;
import com.example.savehometraining.Login.json.LoginData;
import com.example.savehometraining.Login.json.LoginResponse;
import com.example.savehometraining.Login.json.PartData;
import com.example.savehometraining.Login.json.PartResponse;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.Routine.json.FitnessData;
import com.example.savehometraining.ui.Routine.json.FitnessResponse;
import com.example.savehometraining.ui.Routine.json.RoutineTestData;
import com.example.savehometraining.ui.Routine.json.RoutineTestResponse;
import com.example.savehometraining.ui.calendar.json.DeleteFitnessData;
import com.example.savehometraining.ui.calendar.json.DeleteFitnessResponse;
import com.example.savehometraining.ui.calendar.json.LoadCalendarDayData;
import com.example.savehometraining.ui.calendar.json.LoadCalendarDayResponse;
import com.example.savehometraining.ui.calendar.json.LoadDayInfoResponse;
import com.example.savehometraining.ui.calendar.json.LoadDayInfodata;
import com.example.savehometraining.ui.calendar.json.ModifyFitnessData;
import com.example.savehometraining.ui.calendar.json.ModifyFitnessResponse;
import com.example.savehometraining.ui.calendar.json.WriteFitnessData;
import com.example.savehometraining.ui.calendar.json.WriteFitnessResponse;
import com.example.savehometraining.ui.community.json.BoardImgResponse;
import com.example.savehometraining.ui.community.json.DelBoardData;
import com.example.savehometraining.ui.community.json.DelBoardResponse;
import com.example.savehometraining.ui.community.json.DelCommentData;
import com.example.savehometraining.ui.community.json.DelCommentResponse;
import com.example.savehometraining.ui.community.json.LoadCommentData;
import com.example.savehometraining.ui.community.json.LoadCommentResponse;
import com.example.savehometraining.ui.community.json.LoadCommunityData;
import com.example.savehometraining.ui.community.json.LoadCommunityResponse;
import com.example.savehometraining.ui.community.json.ModifyBoardData;
import com.example.savehometraining.ui.community.json.ModifyBoardRespose;
import com.example.savehometraining.ui.community.json.ModifyCommentData;
import com.example.savehometraining.ui.community.json.ModifyCommentResponse;
import com.example.savehometraining.ui.community.json.WriteCommentData;
import com.example.savehometraining.ui.community.json.WriteCommentResponse;
import com.example.savehometraining.ui.community.json.WriteCommunityData;
import com.example.savehometraining.ui.community.json.WriteCommunityResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceApi {
    //유저
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/join/checkId")
    Call<CheckIdResponse> checkId(@Body CheckIdData data);

    @POST("/user/join/checkNickname")
    Call<CheckNicknameResponse> checkNickName(@Body CheckNicknameData data);

    //로그인
    @POST("/user/part_preference")
    Call<PartResponse> Part(@Body PartData data);

    @POST("/user/exercise_form_preference")
    Call<ExResponse> Exercise(@Body ExDatae data);

    @POST("/user/exercise_preference")
    Call<ExResponsea> Exercised(@Body ExData data);

    //커뮤니티
    @POST("/community/load")
    Call<LoadCommunityResponse> LoadCommunity(@Body LoadCommunityData data);

    @POST("/community/load/comment")
    Call<LoadCommentResponse> LoadComment(@Body LoadCommentData data);

    @POST("/community/write")
    Call<WriteCommunityResponse> WriteCommunity(@Body WriteCommunityData data);

    @POST("/community/write/comment")
    Call<WriteCommentResponse> WriteComment(@Body WriteCommentData data);

    @POST("/community/board/del")
    Call<DelBoardResponse> DelBoard(@Body DelBoardData data);

    @POST("/community/board/Modify")
    Call<ModifyBoardRespose> ModifyBoard(@Body ModifyBoardData data);

    @POST("/community/comment/del")
    Call<DelCommentResponse> DelCommnet(@Body DelCommentData data);

    @POST("/community/comment/modify")
    Call<ModifyCommentResponse> ModifyComment(@Body ModifyCommentData data);

    @Multipart
    @POST("/community/board/image")
    Call<BoardImgResponse> boardimg(@Part List<MultipartBody.Part> photos);


    //캘린더
    @POST("/user/calendar/routine")
    Call<FitnessResponse> Fitness(@Body FitnessData data);

    @POST("/user/calendar")
    Call<WriteFitnessResponse> WriteFitness(@Body WriteFitnessData data);

    @POST("/user/calendar/date")
    Call<LoadCalendarDayResponse> LoadCalendarDay(@Body LoadCalendarDayData data);

    @POST("/user/calendar/data")
    Call<LoadDayInfoResponse> LoadDay(@Body LoadDayInfodata data);

    @POST("/user/calendar/deletefit")
    Call<DeleteFitnessResponse> Delfit(@Body DeleteFitnessData data);

    @POST("/user/calendar/modifyfit")
    Call<ModifyFitnessResponse> Mofit(@Body ModifyFitnessData data);

    //루틴
    @POST("/routine/test")
    Call<RoutineTestResponse> RoutineTest(@Body RoutineTestData data);

    @POST("/user/routine/loadexcerciseinfo")
    Call<LoadExerciseInfoResponse> LoadExceriseInfo(@Body LoadExceriseInfoData data);

}
