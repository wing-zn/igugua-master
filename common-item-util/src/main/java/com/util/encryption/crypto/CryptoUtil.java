package com.util.encryption.crypto;

import com.util.encryption.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.experimental.UtilityClass;

/**
 * 使用 spring security 加密方法
 *
 * @author kongling
 * @package com.util.encryption.crypto
 * @date 2019-12-13  14:55
 * @project bank-publish-cloud
 */
@UtilityClass
public class CryptoUtil {

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param rawPass
     * @return
     */
    public String encode(String rawPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPass);
    }

    public static void main(String[] args) {
        String l = "$2a$10$2kw9gNdBVJnSL0zGs5i/Euawwo6fwfCLFqOoMPGFgAnSmiTpbuNR6";
        System.out.println(encode("456"));
        System.out.println(validatePass("123456", l));
    }


    /**
     * 校验密码
     *
     * @param newPass
     * @param passwordEncoderOldPass
     * @return
     */
    public boolean validatePass(String newPass, String passwordEncoderOldPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(newPass, passwordEncoderOldPass);
    }

}
