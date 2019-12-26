package com.mzydz.platform.modules.user.controller;


import com.mzydz.platform.modules.sysconfig.entity.PO.ComConfigAgreement;
import com.mzydz.platform.modules.user.service.IComConfigAgreementService;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统协议设置
 * </p>
 *
 * @author 薛鹏飞
 * @since 2019-04-20
 */
@Controller
@RequestMapping(value = "admin/system",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ComConfigAgreementController {


    @Autowired
    private IComConfigAgreementService comConfigAgreementService;


    @RequestMapping(value = "getAppUserAgreementAndLegalNotices", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(notes = "查询后台 网站用户协议  ", value = "查询后台各种协议", response = ComConfigAgreement.class)
    public Map<String, Object> getAppUserAgreementAndLegalNotices(ComConfigAgreement comConfigAgreement, PageUtil page, HttpServletRequest request, HttpServletResponse response) {

       List<ComConfigAgreement> configAgreement = comConfigAgreementService.getAppUserAgreementAndLegalNotices(comConfigAgreement);
        return ResponseUtil.getSuccessMap(configAgreement);
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "协议修改或添加")
    @ApiOperation(notes = "设置协议 ; ", value = "设置协议")
    public Map<String, Object> update(ComConfigAgreement comConfigAgreement, HttpServletRequest request,
                                      HttpServletResponse response) {
        if(comConfigAgreement.getId() == null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        comConfigAgreementService.updates(comConfigAgreement);
        return ResponseUtil.getSuccessMap();
    }
}
