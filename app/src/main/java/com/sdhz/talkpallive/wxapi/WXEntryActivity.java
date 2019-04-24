/*
 * Copyright (C) 2015 Bilibili <jungly.ik@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sdhz.talkpallive.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sdhz.talkpallive.Constants;
import com.sdhz.talkpallive.R;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import androidx.appcompat.app.AppCompatActivity;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WXAPPID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("resp.errCode:" + resp.errCode , ",resp.errStr:"
                + resp.errStr);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    Toast.makeText(this,"分享成功",Toast.LENGTH_SHORT).show();

                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                 Toast.makeText(this,"分享取消",Toast.LENGTH_SHORT).show();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    //分享拒绝
                    Toast.makeText(this,"分享出错",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    Toast.makeText(this,"分享失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
//        Intent it=new Intent(this, MainActivity.class);
//        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        startActivity(it);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (api != null) {
                api.unregisterApp();
                api.detach();
                api = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}