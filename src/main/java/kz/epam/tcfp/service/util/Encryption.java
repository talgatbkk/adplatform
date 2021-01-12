package kz.epam.tcfp.service.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Encryption {

    public static String encrypt(String data) {
        return DigestUtils.md5Hex(data);
    }
}
