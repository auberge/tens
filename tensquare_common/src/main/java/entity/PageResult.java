package entity;

import java.util.List;

/**
 * 分页结果实体类
 * @param <T>
 */
public class PageResult<T> {
    private long total;//总记录数
    private List<T> rows;//行数

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
