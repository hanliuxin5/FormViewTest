package com.lychee.formviewtest.lychee;

/**
 * Created by lychee on 2015/12/1.
 */
public class BaseBean {


    /**
     * totalCount : 1
     * msg : 1
     * status : 1
     */

    private int totalCount;
    private int msg;
    private int status;

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }
}
