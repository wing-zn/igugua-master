package com.util.aliOSS;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.util.CommonConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * This sample demonstrates how to upload/download an object to/from
 * Aliyun OSS using the OSS SDK for Java.
 */
@Slf4j
public class OSSFather {
    //访问节点
    private static String endpoint = CommonConfig.endpoint;
    //地域节点
    public static String aliOssRegion = CommonConfig.aliOssRegion;
    private static String accessKeyId = CommonConfig.accessKeyId;
    private static String accessKeySecret = CommonConfig.accessKeySecret;
    public static String bucketName_user = CommonConfig.bucketName_user;
    private static String aliOssAcsRamRole = CommonConfig.aliOssAcsRamRole;

    /**
     * OSS的文件夹名
     */
    static String folder = CommonConfig.IMG_PATH;
    //这里是你存放图片的文件夹名
    static String key = "";
    static String last = "?x-oss-process=image/resize,w_300,h_300/quality,q_100";
    static ClientConfiguration conf;

    static {
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(10000);
    }


    public static OSSClient getOSSClient() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
        if (ossClient.doesBucketExist(bucketName_user)) {
            log.debug("您已经创建Bucket：" + bucketName_user + "。");
        } else {
            log.debug("您的Bucket不存在，创建Bucket：" + bucketName_user + "。");
            ossClient.createBucket(bucketName_user);
        }
        BucketInfo info = ossClient.getBucketInfo(bucketName_user);
        log.debug("Bucket " + bucketName_user + "的信息如下：");
        log.debug("\t数据中心：" + info.getBucket().getLocation());
        log.debug("\t创建时间：" + info.getBucket().getCreationDate());
        log.debug("\t用户标志：" + info.getBucket().getOwner());
        return ossClient;
    }

    public static AssumeRoleResponse getAssumeRole() {
        String endpoint = "sts.aliyuncs.com";
        String roleArn = aliOssAcsRamRole;
        String roleSessionName = "session-name";
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // 构造default profile（参数留空，无需添加region ID）
        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
        // 用profile构造client
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysEndpoint(endpoint);
        request.setSysMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        // Optional
        request.setPolicy(policy);
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("Failed：");
            log.error("Error code: " + e.getErrCode());
            log.error("Error message: " + e.getErrMsg());
            log.error("RequestId: " + e.getRequestId());
        }
        return null;
    }


}

