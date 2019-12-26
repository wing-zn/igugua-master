package com.mzydz.platform.modules.common.controller;

import com.mzydz.platform.modules.user.entity.VO.TelCodeTypeVO;
import com.util.CaptchaUtil;
import config.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片验证码服务
 *
 * @author kongling
 * @package com.suda.platform.app
 * @date 2019-05-15  11:21
 * @project suda_cloud
 */
@RestController
@RequestMapping(value = "common/imgCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class ImgVerificationCodeController {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成验图片证码
     *
     * @param response
     * @param request
     * @throws IOException
     */
    @GetMapping("/captcha.jpg")
    public void captcha(TelCodeTypeVO telCodeTypeVO, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成图片验证码
        BufferedImage image = CaptchaUtil.createImage();
        // 生成文字验证码
        String randomText = CaptchaUtil.drawRandomText(image);
        // 保存到验证码到 redis 有效期两分钟
        redisUtils.setImgRedisHashValue(telCodeTypeVO.getImgToken(), randomText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
    }

}
