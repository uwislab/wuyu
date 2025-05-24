package com.fiveup.core.teacherworkspace.common.constant;

import lombok.AllArgsConstructor;

public class CommonMessage {

    @AllArgsConstructor
    public enum Cache {
        /**
         * 用户excel导入成功数据
         */
        LESSON_EXCEL_SUCCESS("wuyu:lesson:excel:success:"),
        /**
         * 用户excel导入失败数据
         */
        LESSON_EXCEL_FAIL("wuyu:lesson:excel:fail:");
        private final String key;

        public String getKey(String keyValue) {
            return this.key + keyValue;
        }
    }
}
