package com.pandaer.project.server.common.file.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SubjectEnum {
    PRODUCT_IMAGE(0,"product"),
    BRAND_LOGO(1,"brand"),
    USER_AVATAR(2,"avatar")
    ;

    private final Integer code;
    private final String dir;

    SubjectEnum(Integer code, String dir) {
        this.code = code;
        this.dir = dir;
    }

    public static SubjectEnum getSubjectByCode(Integer code) {
        return Arrays.stream(values()).filter(item -> ObjUtil.equal(item.getCode(),code)).findFirst().orElse(null);
    }
}
