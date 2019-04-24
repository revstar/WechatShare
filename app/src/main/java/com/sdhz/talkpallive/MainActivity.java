package com.sdhz.talkpallive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.sdhz.talkpallive.adapter.PictureAdapter;
import com.sdhz.talkpallive.decoration.SpaceItemDecoration;
import com.sdhz.talkpallive.utils.BitmapUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPhoto;
    private int scrollPosition = 0;

    private IWXAPI api;

    Bitmap mBitmap;
    List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rvPhoto = findViewById(R.id.rvPhoto);

       list = new ArrayList<>();
        list.add(R.drawable.picture_one);
        list.add(R.drawable.picture_two);
        list.add(R.drawable.picture_three);
        list.add(R.drawable.picture_four);
        list.add(R.drawable.picture_five);

        PictureAdapter pictureAdapter = new PictureAdapter(this, list);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPhoto.setLayoutManager(linearLayoutManager);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvPhoto);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(20);
        rvPhoto.addItemDecoration(spaceItemDecoration);
        rvPhoto.setAdapter(pictureAdapter);

        rvPhoto.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition()>=0){
                    scrollPosition=linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    Log.d("scrollPosition>>>",scrollPosition+"");
                }
            }
        });



    }

    public void wechatCircleOfFriendsShare(View view) {


        getBitmap();
        if (mBitmap!=null){
            wxShare(false,mBitmap);
        }



    }




    public void wechatFriendsShare(View view) {
        getBitmap();
        if (mBitmap!=null){
            wxShare(true,mBitmap);
        }
    }



    /**
     * @param isShareFriend true 分享朋友 false 分享朋友圈
     */
    private void wxShare(boolean isShareFriend, Bitmap bitmap) {
        WXImageObject wxImageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxImageObject;
        //设置缩略图
        Bitmap mBp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(mBp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");//  transaction字段用
        req.message = msg;
        req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api = WXAPIFactory.createWXAPI(this, Constants.WXAPPID, true);
        api.registerApp(Constants.WXAPPID);
        api.sendReq(req);
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private Bitmap getBitmap() {
        if (scrollPosition>=0&&list!=null&&list.size()>scrollPosition){
            mBitmap=BitmapFactory.decodeResource(getResources(),list.get(scrollPosition));
        }
        return mBitmap;
    }
}
