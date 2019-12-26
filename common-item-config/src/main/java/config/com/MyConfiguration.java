package config.com;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 卫星
 * @package config.com
 * @date 2019-04-19  23:05
 * @project niuwan_cloud
 */
@ConfigurationProperties(prefix = "my-configuration")
@Data
@Component
public class MyConfiguration {

    //上传路径
    private String uploadPath;
    //访问路径
    private String imagePath;
    /********************* 静态资源目录 *******************************************/
    /**
     * 配置静态资源目录
     */
    private String staticLocations;

    //session 存储时间

    private Integer sessionTimeout;
    /*************** 阿里云信息 **************************/
    /**
     * LTAI2KhhGGvbXzUZ
     * 阿里key
     */
    private String accessKeyId = "";
    /**
     * n7islklEHqVefD4DR6oNp3Kz5h6CWj
     * 阿里密钥
     */
    private String accessKeySecret = "";

    /*********************阿里短信模版信息*********************************/
    private String aliSmsSignName = "";
    private String aliSmsTemplateCode = "";


    //  ######阿里云文件上传oss##########
    /**
     * 访问节点
     */
    private String aliOssEndpoint = "oss-cn-beijing.aliyuncs.com";
    /**
     * 地域节点
     */
    private String aliOssRegion = "oss-cn-beijing";

    /**
     * 文件名字
     */
    private String aliOssBucketNameUser = "adpulish";

    /**
     * aliOss RAM 角色控制  role  acs:ram::1088928632484109:role/oss
     */
    private String aliOssAcsRamRole = "acs:ram::1088928632484109:role/oss";

    private Integer codeLength = 6;

    //=========================虚拟币========================================================
    /**
     * 以太坊请求节点地址
     */
    private String ethNodesReqAddr = "https://mainnet.infura.io/3f727c7ce8434106809d5cc2463ff779";

    /**
     * 以太坊钱包交易记录
     */
    private String ethQueryOrderRecordReqAddr = "http://api.etherscan.io/api?module=account&action=txlist&page=&offset=&sort=desc&apikey=7X8Y5FA3K5FSG1D3S57B6F5EBPT9QC1AYR";

    /**
     * 推广网页 和二维码地址
     */
    private String popularizePageUrl;
    /**
     * 推广网页 和二维码地址
     */
    private String popularizeQrCode;
    /**
     * 百度富文本 配置文件
     */
    private String baiduUeditorLocations;


    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
