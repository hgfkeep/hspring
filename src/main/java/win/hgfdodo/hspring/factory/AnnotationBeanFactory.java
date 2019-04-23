package win.hgfdodo.hspring.factory;

import win.hgfdodo.hspring.annotation.Component;
import win.hgfdodo.hspring.annotation.Resource;
import win.hgfdodo.hspring.annotation.Value;
import win.hgfdodo.hspring.exception.BothValueAndRefDefinedException;
import win.hgfdodo.hspring.utils.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AnnotationBeanFactory extends AbstractBeanFactory {
    //    java文件后缀
    private final static String JAVA_FILE_SUFFIX = ".class";

    private AbstractBeanFactory parentBeanFactory;

    public AnnotationBeanFactory(AbstractBeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
        this.idBeanMapper = parentBeanFactory.getIdBeanMapper();
        this.idClassMapper = parentBeanFactory.getIdClassMapper();
        this.idObjectMapper = parentBeanFactory.getIdObjectMapper();
        this.beans = parentBeanFactory.getBeans();
    }

    public void load() throws Exception {
        if (this.beans != null && this.beans.getScan() != null) {
            scanPackage(this.beans.getScan().getPackageName());
        }
    }

    @Override
    public Object getBean(String beanId) throws Exception {

        Class clz = this.idClassMapper.get(beanId);
        if (clz == null) {
            return null;
        }
        Object obj = this.idObjectMapper.get(beanId);
        if (obj == null) {
            obj = clz.getDeclaredConstructor().newInstance();
        }
        // 解析属性@Value注解
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            Value v = field.getAnnotation(Value.class);
            if (v != null) {
                String propertyValue = v.value();
                Method m = clz.getMethod("set" + StringUtils.upperFirstChar(field.getName()), String.class);
                m.invoke(obj, propertyValue);
            }

            Resource r = field.getAnnotation(Resource.class);
            if (r != null && v != null) {
                throw new BothValueAndRefDefinedException();
            }
            if (r != null && v == null) {
                String refId = r.name();
                if (StringUtils.isEmpty(refId)) {
                    refId = StringUtils.lowerFirstChar(field.getType().getSimpleName());
                }
                Object value = this.idObjectMapper.get(refId);
                Method m = clz.getMethod("set" + StringUtils.upperFirstChar(field.getName()), value.getClass());
                m.invoke(obj, value);
            }
        }

        if (obj != null) {
            this.idObjectMapper.put(beanId, obj);
        }

        return obj;
    }

    public void scanPackage(String packageName) throws ClassNotFoundException {
        if (StringUtils.isEmpty(packageName)) {
            return;
        }
        String rootPath = this.getClass().getClassLoader().getResource("//").getPath();
        String[] dirs = packageName.split("\\.");
        Path packagePath = Paths.get(rootPath, dirs);
        File pack = packagePath.toFile();
        if (pack != null && pack.isDirectory()) {
            for (File file : pack.listFiles()) {
                String className = file.getName().substring(0, file.getName().length() - JAVA_FILE_SUFFIX.length());
                String fullClassName = packageName + "." + className;
                Class clz = Class.forName(fullClassName);

//                被annotation注解
                Component component = (Component) clz.getAnnotation(Component.class);
                if (component != null) {
                    String beanId = component.value();
                    if (StringUtils.isEmpty(beanId)) {
                        beanId = StringUtils.lowerFirstChar(className);
                    }

                    if (beanId != null) {
                        this.idClassMapper.put(beanId, clz);
                    }
                }
            }
        }
    }

}
