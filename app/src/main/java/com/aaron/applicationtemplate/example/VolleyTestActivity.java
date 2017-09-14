package com.aaron.applicationtemplate.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.applicationtemplate.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Google Volley 通讯库使用例子
 */

public class VolleyTestActivity extends Activity implements OnClickListener {
    private TextView textView1;
    private ImageView imageView1;
    private NetworkImageView networkImageView;
    private Button StringRequest, JsonRequest, ImageRequest, Imageloader, btn_networkImageView;
    private RequestQueue requestQueue = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_volley);
        textView1 = (TextView) findViewById(R.id.textView1);
        StringRequest = (Button) findViewById(R.id.StringRequest);
        JsonRequest = (Button) findViewById(R.id.JsonRequest);
        ImageRequest = (Button) findViewById(R.id.ImageRequest);
        Imageloader = (Button) findViewById(R.id.Imageloader);
        btn_networkImageView = (Button) findViewById(R.id.btn_networkImageView);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);

        StringRequest.setOnClickListener(this);
        JsonRequest.setOnClickListener(this);
        ImageRequest.setOnClickListener(this);
        Imageloader.setOnClickListener(this);
        btn_networkImageView.setOnClickListener(this);

        // 创建请求队列，请求放进该队列
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    // 第一种： 创建StringRequest请求 -----------------------------------------------
    public void StringRequestTest() {
        Listener<String> listener = new Listener<String>() { // 响应监听器
            @Override
            public void onResponse(String response) {
                textView1.setText(response);
                Log.d("TAG", response);
            }
        };
        ErrorListener errorListener = new ErrorListener() { // 出错监听器
            @Override
            public void onErrorResponse(VolleyError error) {
                textView1.setText("出错：" + error.getMessage());
                Log.e("TAG", error.getMessage(), error);
            }
        };

        StringRequest stringRequest = new StringRequest("http://www.baidu.com", listener, errorListener);
        // 带请求方式参数
        StringRequest stringRequest2 = new StringRequest(Method.POST, "http://www.baidu.com", listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {// 重写父类Request的获取post请求的参数方法
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };
        // 添加进队列
        requestQueue.add(stringRequest);
        // requestQueue.add(stringRequest2);
    }

    // 第二种：创建JsonRequest请求 -----------------------------------------------
    public void JsonObjectRequestTest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://e.weather.com.cn/d/index/101280800.shtml", null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView1.setText(response.toString());
                        Log.d("TAG", response.toString());
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView1.setText("出错：" + error.getMessage());
                Log.e("TAG", error.getMessage(), error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    //第三种：加载图片（1） ImageRequest请求 -----------------------------------------------
    public void ImageRequestTest() {
        ImageRequest imageRequest = new ImageRequest("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"
                , new Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView1.setImageBitmap(response);
            }
        }, 0, 0, Config.RGB_565
                , new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView1.setImageResource(R.mipmap.ic_launcher);
            }
        });
        requestQueue.add(imageRequest);
    }

    //（2）图片 ImageLoader -----------------------------------------------
    public void ImageLoaderTest() {
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        ImageListener imageListener = ImageLoader.getImageListener(imageView1, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png", imageListener);
    }

    // ImageCache 图片缓存
    public class BitmapCache implements ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }

    // （3）图片 NetworkImageView -----------------------------------------------
    public void NetworkImageViewTest() {
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);//默认显示的图片
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);//加载出错显示的图片
        networkImageView.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png", imageLoader);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StringRequest:
                StringRequestTest();
                break;
            case R.id.JsonRequest:
                JsonObjectRequestTest();
                break;
            case R.id.ImageRequest:
                ImageRequestTest();
                break;
            case R.id.Imageloader:
                ImageLoaderTest();
                break;
            case R.id.btn_networkImageView:
                NetworkImageViewTest();
                break;
        }
    }
}
