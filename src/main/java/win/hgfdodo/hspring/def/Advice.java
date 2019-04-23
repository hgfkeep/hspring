package win.hgfdodo.hspring.def;

/**
 * 增强
 */
public class Advice {

    // 增强pointcut方法的方法
    private String method;

    // 表示一个类的方法
    private String pointcut;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }
}
