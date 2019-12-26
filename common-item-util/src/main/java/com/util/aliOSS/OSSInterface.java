package com.util.aliOSS;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.util.DateUtil;
import com.util.DealDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Slf4j
public class OSSInterface extends OSSFather {

    /**
     * 上传文件
     *
     * @param bucketName 要上传到哪个存储区域中   hrex
     * @param object     要上传的文件 或是 字符串
     * @param fileName   文件上传路径
     * @return 返回存储的区域 和存储的名字
     */
    public static String upload(Object object, String bucketName, String fileName) {
        // 把字符串存入OSS，Object的名称为firstKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
        //判断是否传进bucketName 如果没有传 那就引用父类的参数
        if (StringUtils.isBlank(bucketName)) {
            bucketName = bucketName_user;
        }
        fileName = folder + fileName;
        //判断object是什么类型 调用什么多态方法
        if (object instanceof String) {
            InputStream is = new ByteArrayInputStream(((String) object).getBytes());
            getOSSClient().putObject(bucketName, fileName, is);
        }
        if (object instanceof InputStream) {
            getOSSClient().putObject(bucketName, fileName, (InputStream) object);
        }
        if (object instanceof File) {
            File file = (File) object;
            getOSSClient().putObject(bucketName, fileName, file);
        }
        if (object instanceof Byte[]) {
            byte[] content = (byte[]) object;
            getOSSClient().putObject(bucketName, fileName, new ByteArrayInputStream(content));
        }
        getOSSClient().shutdown();
        return "/" + fileName;
    }

    //上传图片 返回图片url
    public static String uploadImage(String fileName, InputStream inputStream, String bucketName) {
        OSSClient o = getOSSClient();
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String[] names = fileName.split("[.]");
            String name = uuid + "." + names[names.length - 1];
            PutObjectResult result = o.putObject(new PutObjectRequest(bucketName, folder.substring(1) + name, inputStream));
            System.out.println(JSONObject.toJSONString(result));
            return key + folder + name;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            o.shutdown();
        }
        return null;

    }

    /**
     * 查看某个存储区域的所有文件
     *
     * @param bucketName 要查看的存储区域
     * @return
     */
    public static List<OSSObjectSummary> findObject(String bucketName) {
        if (bucketName == null || "".equals(bucketName)) {
            bucketName = bucketName_user;
        }
        OSSClient o = getOSSClient();
        ObjectListing objectListing = o.listObjects(bucketName);
        List<OSSObjectSummary> list = objectListing.getObjectSummaries();
        o.shutdown();
        return list;
    }

    /**
     * 删除某个文件
     *
     * @param bucketName 存储区域
     * @param key        文件key
     */
    public static void delFile(String bucketName, String key) {
        OSSClient o = getOSSClient();
        o.deleteObject(bucketName, key);
        o.shutdown();
    }

    /**
     * 获取图片
     *
     * @param response   服务端
     * @param bucketName 存储区域
     * @param firstKey   文件key
     * @throws IOException
     */
    public void getPhoto(HttpServletResponse response, String bucketName, String firstKey) throws IOException {

        OSSClient o = getOSSClient();
        if (bucketName == null || bucketName.equals("")) {
            bucketName = bucketName_user;
        }
        try {
            InputStream fis = o.getObject(bucketName, firstKey).getObjectContent();
            // 设置输出的格式
            response.reset();
            response.setContentType("image/png");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            while ((len = fis.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            fis.close();
            o.shutdown();
        } catch (IOException c) {
            c.printStackTrace();
        }

    }

    /**
     * 获取上传文件路径
     */
    public static String getUploadDir() {
        String uploadDir = DateUtil.dateToString(DealDateUtil.getNowDate(), DateUtil.DateStyle.YYYY_MM_DD_EN.value);
        return uploadDir;
    }


    /**
     * 获取上传文件大小
     *
     * @param bucketName 要查看的存储区域
     * @param url        文件访问路径
     * @return
     */
    public static OSSObject findOssObjectOne(String bucketName, String url) {
        OSSClient o = getOSSClient();
        OSSObject ossObject = new OSSObject();
        try {
            //截取文件 key
            String key = url.split("://")[1].split("[?]")[0];
            key = key.substring(key.indexOf("/") + 1);
            ossObject = o.getObject(bucketName, key);
        } catch (Exception e) {
            log.error("获取上传文件信息错误：" + ExceptionUtils.getFullStackTrace(e));
        } finally {
            o.shutdown();
        }
        return ossObject;
    }

    public static void main(String[] args) {
        String url = "https://adpulish.oss-cn-beijing.aliyuncs.com/2019/09/21/1531834169104-rc-upload-1569034608964-2.jpeg?-----?";
        String key = url.split("://")[1].split("[?]")[0];
        key = key.substring(key.indexOf("/") + 1);
        System.out.println(key);
    }

}
