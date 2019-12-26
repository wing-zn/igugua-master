package com.mzydz.platform.modules.user.entity.VO.homeCount;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author kongling
 * @package com.suda.platform.VO.homeCount
 * @date 2019-06-20  16:02
 * @project suda_cloud
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class HomeCountVO {
    /**
     * 总管理员数量
     */
    public Long userAll;

    /**
     * 系统管理员数量
     */
    public Long userSys;

    /**
     * 网点管理员数量
     */
    public Long userBranch;

}
