package com.util;

import config.com.MyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 卫星
 * @package com.util
 * @date 2019-03-08  10:33
 * @project CurrenCy-Cloud
 */
@Component
public class CommonConfig {
    @Autowired
    MyConfiguration myConfiguration;

    @PostConstruct
    public void init() {
        FILE_PATH = myConfiguration.getUploadPath();
        IMG_PATH = myConfiguration.getImagePath();
        baiduUeditorLocations = myConfiguration.getBaiduUeditorLocations();
        endpoint = myConfiguration.getAliOssEndpoint();
        aliOssRegion = myConfiguration.getAliOssRegion();
        accessKeyId = myConfiguration.getAccessKeyId();
        accessKeySecret = myConfiguration.getAccessKeySecret();
        bucketName_user = myConfiguration.getAliOssBucketNameUser();
        aliOssAcsRamRole = myConfiguration.getAliOssAcsRamRole();
    }

    /**
     * 文件路径
     */
    public static String FILE_PATH;
    public static String IMG_PATH;
    /**
     * 阿里信息
     */
    public static String endpoint;
    public static String aliOssRegion;
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String bucketName_user;
    public static String aliOssAcsRamRole;

    /**
     * 百度富文本 配置文件
     */
    public static String baiduUeditorLocations;


}
