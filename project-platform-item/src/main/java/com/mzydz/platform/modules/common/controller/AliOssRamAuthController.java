package com.mzydz.platform.modules.common.controller;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.mzydz.platform.modules.user.entity.VO.AliOssRamVO;
import com.util.CommonConfig;
import com.util.aliOSS.OSSFather;
import com.util.aliOSS.OSSInterface;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 阿里云 oss 上传图片认证
 *
 * @author kongling
 * @package com.mzydz.platform.controller.agent.JustAuthController
 * @date 2019-09-20  11:42
 * @project ad-publish-cloud
 */
@RestController
@RequestMapping(value = "aliOssRam/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AliOssRamAuthController {

    @RequestMapping(value = "getStsToken", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = " 获取资源上传token")
    public Map<String, Object> editCompanyInfo() throws IOException {
        AssumeRoleResponse r = OSSFather.getAssumeRole();
        if (r != null) {
            //创建 阿里buk目录
            AliOssRamVO aliOssRamVO = AliOssRamVO.builder().uploadDir(OSSInterface.getUploadDir())
                    .bucketName_user(CommonConfig.bucketName_user)
                    .endpoint(CommonConfig.aliOssRegion).build();
            aliOssRamVO.setCredentials(r.getCredentials());
            return ResponseUtil.getSuccessMap(aliOssRamVO);
        } else {
            return ResponseUtil.getNotNormalMap(ResponseMsg.GET_OSS_RAM_ASS_FAIL);
        }
    }
}
