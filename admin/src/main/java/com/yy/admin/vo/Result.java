package com.yy.admin.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 统一结果返回
 */
public class Result {

    public Result() {
    }

    public Result(String msg, Integer code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Result(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public Result(Object data) {
        if(data instanceof IPage){
            IPage page = (IPage) data;
            setPage(page);
            this.data = (page == null ? new ArrayList<>(0) : page.getRecords());
        }else{
            this.data = data;
        }

    }


    /**
     * 消息
     */
    private String msg = "成功";
    /**
     * 状态码
     */
    private Integer code = 200;
    /**
     * 请求ID
     */
    private String requestId = UUID.randomUUID().toString();
    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis() / 1000;

    /**
     * 数据
     */
    private Object data;

    /**
     * 异常信息
     */
    private String ex;

    /**
     * 分页信息
     */
    private Map<String, Object> page;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setPage(IPage page) {
        this.page = new HashMap<>();
        if (page == null) {
            page = new Page(1, 10);
        }
        this.page.put("current", page.getCurrent());
        this.page.put("size", page.getSize());
        this.page.put("pageCount", page.getPages());
        this.page.put("total", page.getTotal());
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getEx() {
        return ex != null && ex.isEmpty() ? null : ex;

    }

    public Map<String, Object> getPage() {
        return page;
    }

    public void setPage(Map<String, Object> page) {
        this.page = page;
    }

    public String getRequestId() {
        return requestId != null && requestId.isEmpty() ? null : requestId;

    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
