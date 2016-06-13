package com.ai.slp.operate.web.test.dps;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.util.ImageByteUtil;
import com.ai.paas.ipaas.image.IImageClient;

/**
 * Created by jackieliu on 16/6/12.
 */
public class DpsTest {

    public void test(){
            try {
                //应用场景
                String idpsns="slp-mall-web-idps";
                //获取imageClient
                IImageClient im = IDPSClientFactory.getImageClient(idpsns);
                //待上传的图片路径
                String filepath="E:\\haha.jpg";
                //将路径转换为byte[]
                byte[] buff= ImageByteUtil.image2byte(filepath);
                //上传图片，获取上传后的ID
                String idpsId=im.upLoadImage(buff, "haha.jpg");
                System.out.println("idpsId="+idpsId);
                //获取上传图片的URL
                System.out.println(im.getImageUrl(idpsId, ".jpg"));
                //获取上传图片指定尺寸的URL
                System.out.println(im.getImageUrl(idpsId, ".jpg","100x80"));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
