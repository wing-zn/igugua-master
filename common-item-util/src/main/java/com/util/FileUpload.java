package com.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author zhengliangzhang
 */
public class FileUpload {
    // 上传地址路径
    public static String FILE_PATH = CommonConfig.FILE_PATH;
    // 图片路径
    public static String IMGPATH = CommonConfig.IMG_PATH;

    static {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    // 文件上传
    public static File getFile(String fileName) {
        return new File(FILE_PATH, fileName);
    }

    //获取 重组文件名字
    public static String getFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String[] split = fileName.split("\\.");
        fileName = ConstantConfig.PERIOD + split[split.length - 1];
        if (fileName.equals(ConstantConfig.PERIOD)) {
            return "";
        }
        UUID uuid = UUID.randomUUID();
        fileName = System.currentTimeMillis() + uuid.toString() + String.valueOf(fileName);
        return fileName;
    }

    /**
     * 上传 返回文件名称
     */
    public static String uploadFile(MultipartFile file, HttpServletRequest request)
            throws IOException {
        String imgPath = IMGPATH;
        String fileName = getFileName(file);
        if (fileName.equals("")) {
            return "";
        }
        String uploadDir = getUploadDir();
        File tempFile =
                getTempFile(fileName);
        file.transferTo(tempFile);
        return imgPath + uploadDir + "/" + tempFile.getName();
    }

    private static File getTempFile(String fileName) throws IOException {
        String uploadDir = getUploadDir();
        File tempFile =
                new File(FILE_PATH + uploadDir, fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        return tempFile;
    }


    public static String uploadFileBase64(String imgBase64) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        String uploadDir = getUploadDir();
        String imgPath = IMGPATH;
        String fileName = UUID.randomUUID().toString() + ".jpg";

        // Base64解码
        byte[] b = decoder.decodeBuffer(imgBase64);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        File tempFile =
                getTempFile(fileName);
        OutputStream out = new FileOutputStream(tempFile.getPath());
        out.write(b);
        out.flush();
        out.close();
        return imgPath + uploadDir + "/" + fileName;
    }

    /**
     * 判断上传文件是否为图片
     *
     * @throws IOException
     */
    public static boolean isImage(MultipartFile file) throws IOException {
        BufferedImage bi = ImageIO.read(file.getInputStream());
        if (bi == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取上传文件路径
     */
    public static String getUploadDir() {
        String uploadDir = DateUtil.dateToString(DealDateUtil.getNowDate(), DateUtil.DateStyle.YYYY_MM_DD_EN.value);
        return uploadDir;
    }


    //===============================百度富文本使用============================================

    /**
     * 传入上传路径
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file, String savePathPrefix)
            throws IOException {
        String imgPath = IMGPATH;
        File tempFile =
                getTempFilePrefix(savePathPrefix);
        file.transferTo(tempFile);
        return imgPath + savePathPrefix;
    }

    /**
     * 上传传入根路径下前缀
     *
     * @param savePathPrefix
     * @return
     * @throws IOException
     */
    private static File getTempFilePrefix(String savePathPrefix) throws IOException {
        File tempFile =
                new File(FILE_PATH + savePathPrefix);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        return tempFile;
    }


}
