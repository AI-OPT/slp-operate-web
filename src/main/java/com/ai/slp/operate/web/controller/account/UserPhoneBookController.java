package com.ai.slp.operate.web.controller.account;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.user.api.ucuserphonebooks.interfaces.IUserPhoneBooksSV;
import com.ai.slp.user.api.ucuserphonebooks.param.*;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserPhoneBookController {

	@RequestMapping("/account/phonebook/phonebookmgr")
	public ModelAndView phonebookmgr(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookmgr");
		return view;
	}

	@RequestMapping("/account/phonebook/submitNewTelGroup")
	@ResponseBody
	public ResponseData<String> submitNewTelGroup(UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).addUcTelGroup(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/queryTelGroups")
	@ResponseBody
	public ResponseData<List<UcTelGroup>> queryTelGroups(UcTelGroupQueryReq req) {
		ResponseData<List<UcTelGroup>> responseData = null;
		try {
			UcTelGroupQueryResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).getUcTelGroups(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功",
						resp.getGroups());
			} else {
				responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/deleteUcTelGroup")
	@ResponseBody
	public ResponseData<String> deleteUcTelGroup(UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).deleteUcTelGroup(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/modifyUcTelGroup")
	@ResponseBody
	public ResponseData<String> modifyUcTelGroup(UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).modifyUcTelGroup(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/phonebookdetail")
	public ModelAndView phonebookdetail(HttpServletRequest request) {
		String telGroupId = request.getParameter("telGroupId");
		if (StringUtil.isBlank(telGroupId)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请传入分组ID");
		}
		request.setAttribute("telGroupId", telGroupId);
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookdetail");
		return view;
	}

	@RequestMapping("/account/phonebook/queryUserPhonebooks")
	@ResponseBody
	public ResponseData<PageInfo<UserPhonebook>> queryUserPhonebooks(UcUserPhonebooksQueryReq req) {
		ResponseData<PageInfo<UserPhonebook>> responseData = null;
		try {
			PageInfo<UserPhonebook> pagInfo = DubboConsumerFactory.getService(IUserPhoneBooksSV.class)
					.queryUserPhonebooks(req);
			responseData = new ResponseData<PageInfo<UserPhonebook>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", pagInfo);
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<UserPhonebook>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/batchDeleteUserPhonebooks")
	@ResponseBody
	public ResponseData<String> batchDeleteUserPhonebooks(String recordIds) {
		ResponseData<String> responseData = null;
		try {
			UcUserPhonebooksBatchDeleteReq req = new UcUserPhonebooksBatchDeleteReq();
			List<String> list = new ArrayList<String>();
			String[] arr = recordIds.split(",");
			if (arr != null && arr.length > 0) {
				for (String id : arr) {
					list.add(id);
				}
			}

			req.setRecordIds(list);
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).batchDeleteUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/batchAddUserPhonebooks")
	@ResponseBody
	public ResponseData<String> batchAddUserPhonebooks(String datas) {
		ResponseData<String> responseData = null;
		try {
			List<UcUserPhonebooksBatchData> list =JSON.parseArray(datas, UcUserPhonebooksBatchData.class);
			UcUserPhonebooksBatchAddReq req = new UcUserPhonebooksBatchAddReq();
			req.setDatas(list);
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).batchAddUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

}
