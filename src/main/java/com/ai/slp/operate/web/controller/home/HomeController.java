package com.ai.slp.operate.web.controller.home;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.user.api.keyinfo.interfaces.IUcKeyInfoSV;
import com.ai.slp.user.api.keyinfo.param.QueryGroupInfoRequest;
import com.ai.slp.user.api.keyinfo.param.QueryGroupInfoResponse;
import com.ai.slp.user.api.keyinfo.param.UcGroupKeyInfoVo;
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
			logger.info("\rfileUid:"+fileUid+"\rfileUrl:"+imageUrl);
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

	@RequestMapping("/queryuser")
	@ResponseBody
	public ResponseData<PageInfo<UcGroupKeyInfoVo>> queryUserList(Integer pageSize, Integer pageNo, String userType, String userName){
		ResponseData<PageInfo<UcGroupKeyInfoVo>> responseData;
		QueryGroupInfoRequest infoRequest = new QueryGroupInfoRequest();
		infoRequest.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
		infoRequest.setPageNo(pageNo);
		infoRequest.setPageSize(pageSize);
		infoRequest.setCustName(userName);
		infoRequest.setUserType(userType);
		IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
		QueryGroupInfoResponse infoResponse = ucKeyInfoSV.queryGroupInfo(infoRequest);
		ResponseHeader header = infoResponse.getResponseHeader();
		if (header!=null && header.isSuccess()){
			responseData = new ResponseData<PageInfo<UcGroupKeyInfoVo>>(ResponseData.AJAX_STATUS_SUCCESS,
					"查询成功",infoResponse.getPageInfo());
		}else {
			responseData = new ResponseData<PageInfo<UcGroupKeyInfoVo>>(ResponseData.AJAX_STATUS_FAILURE,
					"查询失败:"+header.getResultMessage());
		}
		return responseData;
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
