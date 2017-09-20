package com.aaron.applicationtemplate.data.remote;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 网络请求接口
 */
public interface ZhiHuDailyAPI {

    /**
     * 获取最新的日报数据
     *
     * @return
     */
    @GET("stories/latest")
    Observable<ResponseBody> getlatestNews();

    /**
     * 根据id查询主题日报内容
     *
     * @param id
     * @return
     */
    @GET("theme/{id}")
    Observable<ResponseBody> getThemesDetailsById(@Path("id") int id);

    /**
     * 根据id查询日报的额外信息
     *
     * @param id
     * @return
     */
    @GET("story-extra/{id}")
    Observable<ResponseBody> getDailyExtraMessageById(@Path("id") int id);
}
