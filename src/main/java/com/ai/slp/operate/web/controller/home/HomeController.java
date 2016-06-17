package com.ai.slp.operate.web.controller.home;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/home")
public class HomeController {
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping({"","/"})
	public String index(Model uiModel) {
		return "forward:/prodquery/add";
	}

	@RequestMapping("/upimg")
	@ResponseBody
	public String uploadImg(@RequestParam("upload") MultipartFile file, HttpServletRequest request,
							@RequestParam("CKEditorFuncNum") String ckeditFuncNum, String ckCsrfToken){
		printParams(request);
		StringBuffer strBuffer = new StringBuffer();
		IImageClient imageClient = IDPSClientFactory.getImageClient(SysCommonConstants.ProductImage.IDPSNS);
		try {
			String fileName = file.getOriginalFilename();
			String fileUid = imageClient.upLoadImage(file.getBytes(),fileName);
			String imageUrl = imageClient.getImageUrl(fileUid, getFileExtName(fileName));
			System.out.println("\rfileUid:"+fileUid+"\rfileUrl:"+imageUrl);
			strBuffer.append("<script type=\"text/javascript\">");
			strBuffer.append("window.parent.CKEDITOR.tools.callFunction("+ckeditFuncNum+",'"+imageUrl+"','')");
			strBuffer.append("</script>");
		} catch (IOException e) {
			logger.error("Add file faile.",e);
			strBuffer.reverse();
			strBuffer.append("<font color=\"red\" size=\"2\">*上传文件错误</font>");
		}
		return strBuffer.toString();
	}

	private String getFileExtName(String fileName){
		String extName = "";
		if (StringUtils.isNotBlank(fileName)){
			int ind =fileName.lastIndexOf(".");
			extName = fileName.substring(ind);
		}
		return extName;
	}

	private void printParams(HttpServletRequest request){
		Map<String,String[]> paramMap = request.getParameterMap();
		for (Map.Entry<String,String[]> entry:paramMap.entrySet()){
			System.out.println("the param:"+entry.getKey());
			String[] vals = entry.getValue();
			for (String val:vals){
				System.out.print(val+",");
			}
			System.out.println("\r");
		}
	}
}
