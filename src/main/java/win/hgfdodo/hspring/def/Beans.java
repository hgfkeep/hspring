package win.hgfdodo.hspring.def;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Beans {

    List<Bean> beans = new ArrayList<Bean>();
    private Scan scan;
    private List<Aspect> aspects = new ArrayList<>();

    public void addBean(Bean bean) {
        beans.add(bean);
    }

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public List<Aspect> getAspects() {
        return this.aspects;
    }

    public void addAspect(Aspect aspect) {
        this.aspects.add(aspect);
    }
}
