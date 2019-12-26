package com;

import com.constant.CommonConstant;
import com.util.StringUtils;

import java.math.BigDecimal;

/**
 * 用户账号信息
 *
 * @author kongling
 * @package com
 * @date 2019-05-16  17:55
 * @project suda_cloud
 */
public class Account {
    /**
     * 获取用户唯一帐号
     */
    public static String getUserUid(Long id) {
        return CommonConstant.ACCOUNT_PREFIX_STR + (CommonConstant.ACCOUNT_PREFIX_NUM + id);
    }

    /**
     * 获取邀请码
     *
     * @param id
     * @return
     */
    public static String getInvitationCode(Long id) {
        return CommonConstant.project + (new BigDecimal(id).add(new BigDecimal(1000))).toString();
    }

    /**
     * 获取订单号
     */
    public static String getSwiftNo(Long id) {
        return CommonConstant.project + id + System.currentTimeMillis() + StringUtils.getRandom(9);
    }




}
