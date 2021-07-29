package com.rifeng.p2p.http;


import com.google.gson.JsonObject;
import com.rifeng.p2p.entity.AccountInfo;
import com.rifeng.p2p.entity.AddressItem;
import com.rifeng.p2p.entity.AgentInfo;
import com.rifeng.p2p.entity.InvitationCode;
import com.rifeng.p2p.entity.LookupCode;
import com.rifeng.p2p.entity.Notify;
import com.rifeng.p2p.entity.NotifyResponse;
import com.rifeng.p2p.entity.PressureRecordChart;
import com.rifeng.p2p.entity.PressureRecordResponse;
import com.rifeng.p2p.entity.RFResponse;
import com.rifeng.p2p.entity.ReportData;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.entity.UserAgreement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public interface RFService {


    @GET("/api/pressure/queryAgreement/{agentId}/{companyId}")
    Flowable<RFResponse<AgentInfo>> getAgentInfo(@Path("agentId") String agentId, @Path("companyId") String companyId);

    @POST("/api/pressure/queryPressureRecord")
    Flowable<RFResponse<PressureRecordResponse>> getPressureRecord(@Body RequestBody body);

    @FormUrlEncoded
    @POST("/api/pressureChar/pageQuery")
    Flowable<RFResponse<PressureRecordChart>> getPressureRecordCharts(@Field("testId") String testId, @Field("pageIndex") int pageIndex, @Field("pageSize") int pageSize);


    @POST("/api/auth/appLogin")
    Flowable<RFResponse<AccountInfo>> getLoginInfo(@Body RequestBody body);


    @POST("/api/pressure/addPressure")
    Flowable<RFResponse<String>> savePressureRecord(@Body RequestBody body);

    @POST("/api/pressureChar/save")
    Flowable<RFResponse<String>> savePressureRecordChart(@Body RequestBody pressureRecord);

    //验证邀请码
    @POST("/api/auth/checkRegister")
    Flowable<RFResponse<String>> getInvitationCode(@Body RequestBody param);

    //注册
    @POST("/api/auth/register")
    Flowable<RFResponse<String>> addPlumber(@Body RequestBody pressureRecord);

    @POST("/api/auth/updateMyPwd")
    Flowable<RFResponse<String>> modifiedPassWord(@Body RequestBody body);


    @POST("/api/auth/sendCaptchaEmail")
    Flowable<RFResponse<String>> forGottenPassWords(@Body RequestBody body);

    @POST("/api/auth/resetMyPwd")
    Flowable<RFResponse<String>> retsetPassWord(@Body RequestBody body);

    @POST("/api/auth/updateMe")
    Flowable<RFResponse<String>> modifiedPlumberInformation(@Body RequestBody body);

    //新接口
    @GET("/api/Pdf/sendEmail/{testId}")
    Flowable<RFResponse<String>> getRecordChartForPDFAndSendMail(@Path("testId") String testId);
//
//    @GET("com.sie.rfhw.pressureRecord.pressureRecord.selectPressureRecordByTestId.biz.ext")
//    Flowable<RFResponse<PressureRecord>> selectPressureRecordByTestId(@Query("testId") String testId);
//
    @POST("/api/pressure/addCodeAndDiagram")
    Flowable<RFResponse<String>> insertDocumentsAndPipingCode(@Body RequestBody body);
//
//    //获取登录验证码图片
//    @POST("com.vplus.utils.verifyCodeImage.flow")
//    Flowable<Object> verifyCodeImage(@Body RequestBody body);


//    @POST("com.vplus.utils.checkVerifyCodeImage.flow")
//    Flowable<Object> userVerifyCodeImage(@Body RequestBody body);
//
//
//    @POST("com.vplus.login.check.checkIsAgreement.biz.ext")
//    Flowable<RFResponse<String>> checkIsAgreement(@Body RequestBody body);

    //新接口
    @GET("/api/dict/getByTypeCode/TEST_METHOD/Y")
    Flowable<RFResponse<List<LookupCode>>> getTestMethod();
    //获取公司名
    @GET("/api/auth/checkCompany/{code}")
    Flowable<RFResponse<JsonObject>> getCompanyName(@Path("code")String code);

    ////获取地区列表
    @GET("/api/tree/getTreeByParentId/{code}/Y/{parentId}")
    Flowable<RFResponse<List<AddressItem>>> getAreaList(@Path("code")String code, @Path("parentId") String parentId);


    //首次登陆同意协议
    @GET("/api/auth/agreeAggrement")
    Flowable<RFResponse<String>> getAgreeAgrement();

    //检测设备可用性
    @GET("/api/equipment/getEquipmentByUniqueCode/{code}/{userId}")
    Flowable<RFResponse<String>> getDeviceCheck(@Path("code")String code, @Path("userId")String userId);

    @GET("/api/agreement/getAgreementByType/{type}")
    Flowable<RFResponse<UserAgreement>> getAgreementContent(@Path("type")String type);

    @GET("/api/auth/checkInvitation/{code}")
    Flowable<RFResponse<InvitationCode>> checkInvitation(@Path("code") String code);
    @Multipart
    @POST("/api/upload/uploadMuti")
    Flowable<RFResponse<List<String>>> uploadImages(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("/api/upload/upload")
    Flowable<RFResponse<String>> uploadImage(@Part MultipartBody.Part photo);
    @GET("/api/auth/getCaptcha")
    Flowable<RFResponse<ResponseBody>> getImageValidCode(@Path("type")String type);

    @POST("/api/notify/myPageQuery")
    Flowable<RFResponse<NotifyResponse>> getNotifies(@Body RequestBody body);

    @GET("/api/notify/get")
    Flowable<RFResponse<NotifyResponse>> getMyNotifies();

    @GET("/api/report/reportData")
    Flowable<RFResponse<ReportData>> getReportData();


    @GET("/api/notify/getNotRead")
    Flowable<RFResponse<HashMap<String , Integer>>> getNotRead();


    @GET("/api/equipment/getCurrentTime")
    Flowable<RFResponse<Map<String, String>>> getServerTime();

    //获取详情，并且设置成已读
    @GET("/api/notify/get/{id}/{isRead}")
    Flowable<RFResponse<Notify>> setReaded(@Path("id") Long id , @Path("isRead") String isRead);

    @GET("/api/notify/setReaded")
    Flowable<RFResponse<String>> setAllReaded();

    @GET("/api/auth/getUserByEmail/{email}")
    Flowable<RFResponse<String>> getUserByEmail(@Path("email") String email);

    @GET("/api/auth/getUserByEmailForReset/{email}")
    Flowable<RFResponse<User>> getUserByEmailForReset(@Path("email") String email);


    @POST("/api/pressure/submit")
    Flowable<RFResponse<String>> submitPressureResult(@Body RequestBody body);


    @Multipart
    @POST("/api/pipingDiagram/addDiagram")
    Flowable<RFResponse<String>> uploadPipeImage(@Part  MultipartBody.Part[] params ,@PartMap Map<String,Long> testId);

}
