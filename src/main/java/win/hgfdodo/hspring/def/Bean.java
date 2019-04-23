package win.hgfdodo.hspring.def;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 类初始化信息描述类
 */
public class Bean {
    private String className;
    private String id;
    private String name;
    private String initMethod;
    private String destroyMethod;
    private List<Property> properties = new ArrayList<Property>();

    public Bean() {
    }

    public Bean(String className, String id, String name, List<Property> properties) {
        this.className = className;
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    public Bean(String className, String id, String name, String initMethod, String destroyMethod, List<Property> properties) {
        this.className = className;
        this.id = id;
        this.name = name;
        this.initMethod = initMethod;
        this.destroyMethod = destroyMethod;
        this.properties = properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void addProperties(Collection<Property> propertyCollection) {
        properties.addAll(propertyCollection);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "className='" + className + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", initMethod='" + initMethod + '\'' +
                ", destroyMethod='" + destroyMethod + '\'' +
                ", properties=" + properties +
                '}';
    }
}
