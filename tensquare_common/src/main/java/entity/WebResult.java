package entity;

/**
 * 返回值实体类
 */
public class WebResult {
    private Integer code;//状态码
    private boolean flag;//是否成功
    private String message;//信息
    private Object date;//数据

    public WebResult() {
    }

    public WebResult(Integer code, boolean flag, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public WebResult(Integer code, boolean flag, String message, Object date) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.date = date;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
