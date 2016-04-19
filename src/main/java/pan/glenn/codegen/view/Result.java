/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class Result {

    private String msg;

    private boolean success;

    public Result(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public static  final Result success() {
        return new Result(null, true);
    }

    public static  final Result fail(String msg) {
        return new Result(msg, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
