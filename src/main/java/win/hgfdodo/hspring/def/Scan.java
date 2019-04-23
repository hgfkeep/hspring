package win.hgfdodo.hspring.def;

public class Scan {
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "Scan{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
