package com.fiveup.core.common.result;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 
 * @Date 
 * @Version
 * 用于封装消息体，返回页面的消息体
 **/
public class ResultBo<T> implements Serializable {
    private static final long serialVersionUID = -5196002076749493116L;
    private Integer code;
    private String message;
    private Long count;
    private T data;

    public boolean isSuccess() {
        return BaseResultStatusEnum.SUCCESS.getCode().equals(code);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ResultBo<T> ok(T data, Long count) {
        return new ResultBo(BaseResultStatusEnum.SUCCESS, data, count);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ResultBo<T> ok(T data) {
        return new ResultBo(BaseResultStatusEnum.SUCCESS, data);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ResultBo<T> ok(String key, T data, Long count) {
        Map<String, T> m = new HashMap<String, T>();
        m.put(key, data);
        return new ResultBo(BaseResultStatusEnum.SUCCESS, m, count);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ResultBo<T> ok() {
        return new ResultBo(BaseResultStatusEnum.SUCCESS, null, 0l);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ResultBo<T> error(BaseResultStatusEnum baseResultStatusEnum) {
        return new ResultBo(baseResultStatusEnum);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ResultBo<T> error(BaseResultStatusEnum baseResultStatusEnum, String errMsg) {
        return new ResultBo(baseResultStatusEnum.getCode(), errMsg);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> ResultBo<T> error(Integer code, String errMsg) {
        return new ResultBo(code, errMsg);
    }

    public ResultBo() {
        this(BaseResultStatusEnum.SUCCESS);
    }

    public ResultBo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBo(BaseResultStatusEnum baseResultStatusEnum) {
        this.code = baseResultStatusEnum.getCode();
        this.message = baseResultStatusEnum.getMessage();
    }

    public ResultBo(BaseResultStatusEnum baseResultStatusEnum, T data, Long count) {
        this(baseResultStatusEnum);
        this.data = data;
        this.count = count;
    }

    public ResultBo(BaseResultStatusEnum baseResultStatusEnum, T data) {
        this(baseResultStatusEnum);
        this.data = data;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
