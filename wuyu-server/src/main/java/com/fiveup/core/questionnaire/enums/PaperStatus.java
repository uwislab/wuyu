package com.fiveup.core.questionnaire.enums;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: PaperStatus
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:25
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public enum PaperStatus {

    /**
     * 初始化
     * @return
     * @throws
     */
    INIT("0","设计中"),

    /**
     * 开始发放
     * @return
     * @throws
     */
    START("1","收集中"),

    /**
     * 停止发放
     * @return
     * @throws
     */
    STOP("2","结束");
    private String code;
    private String desc;

    PaperStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
