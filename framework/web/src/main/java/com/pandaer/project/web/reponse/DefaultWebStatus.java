package com.pandaer.project.web.reponse;

public enum DefaultWebStatus implements WebStatus {

    SuccessStatus(1200,"Success"),
    ErrorStatus(1500,"Error"),

    ;
    private final Integer code;
    private final String message;

    DefaultWebStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
