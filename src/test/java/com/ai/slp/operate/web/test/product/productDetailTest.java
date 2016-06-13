package com.ai.slp.operate.web.test.product;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.util.ImageByteUtil;
import com.ai.paas.ipaas.image.IImageClient;
import org.junit.Test;

public class productDetailTest {
	@Test
	public void addImage() {
		// 应用场景
		String idpsns = "slp-mall-web-idps";
		// 获取imageClient
		IImageClient im = IDPSClientFactory.getImageClient(idpsns);

		// 待上传的图片路径
		String filepath = "C:\\Users\\jiaxs\\Pictures\\slp-test\\";
		String[] fileNames = new String[]{"G.png","2G.png","3G.png","4G.png"};
		for(int i=0;i<fileNames.length;i++){
			// 将路径转换为byte[]
			byte[] buff = ImageByteUtil.image2byte(filepath+fileNames[i]);
			// 上传图片，获取上传后的ID
			String idpsId = im.upLoadImage(buff, fileNames[i]);
			System.out.println(filepath+fileNames[i] + "  idpsId=" + idpsId);
			// 获取上传图片的URL
			System.out.println(im.getImageUrl(idpsId, ".png"));
			// 获取上传图片指定尺寸的URL
			//System.out.println(im.getImageUrl(idpsId, ".jpg", "100x80"));
		}
	}
	
	@Test
	public void checkImage(){
		
	}
}
