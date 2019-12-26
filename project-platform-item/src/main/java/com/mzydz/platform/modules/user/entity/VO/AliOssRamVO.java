package com.mzydz.platform.modules.user.entity.VO;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kongling
 * @package com.mzydz.platform.modules.user.entity.VO
 * @date 2019-09-20  14:14
 * @project ad-publish-cloud
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AliOssRamVO {
    /**
     * 构造文件上传目录
     */
    private String uploadDir;
    private String endpoint;
    private String bucketName_user ;
    private AssumeRoleResponse.Credentials credentials;

}
