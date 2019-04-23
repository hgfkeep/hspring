package win.hgfdodo.hspring.factory;

import org.apache.commons.digester.Digester;
import win.hgfdodo.hspring.def.*;
import win.hgfdodo.hspring.utils.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Method;

public class XmlBeanFactory extends AbstractBeanFactory {

    private AbstractBeanFactory parentBeanFactory;

    public XmlBeanFactory(AbstractBeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
        this.idBeanMapper = parentBeanFactory.getIdBeanMapper();
        this.idClassMapper = parentBeanFactory.getIdClassMapper();
        this.idObjectMapper = parentBeanFactory.getIdObjectMapper();
    }

    public void load() throws Exception {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("beans", Beans.class);

        digester.addObjectCreate("beans/scan", Scan.class);
        digester.addSetProperties("beans/scan", "package", "packageName");
        digester.addSetNext("beans/scan", "setScan");

//        如果配置文件中有多个Bean，添加一次即可。
        digester.addObjectCreate("beans/bean", Bean.class);

//        设置Bean的属性
        digester.addSetProperties("beans/bean", "class", "className"); //设置xml与类中定义不一致的属性
        digester.addSetProperties("beans/bean");  //设置默认属性

        digester.addObjectCreate("beans/bean/property", Property.class);
        digester.addSetProperties("beans/bean/property");

//        设置属性的关系，即Bean中怎么添加property， Beans中怎么添加bean
        digester.addSetNext("beans/bean/property", "addProperty");
        digester.addSetNext("beans/bean", "addBean");

        digester.addObjectCreate("beans/aspect", Aspect.class);
        digester.addSetProperties("beans/aspect");

        digester.addObjectCreate("beans/aspect/before", Before.class);
        digester.addSetProperties("beans/aspect/before");
        digester.addSetNext("beans/aspect/before", "addAdvice");

        digester.addSetNext("beans/aspect", "addAspect");

        InputStream inputStream = this.getClass().getClassLoader().getSystemResourceAsStream("beans.xml");
        System.out.println(inputStream.available());
        this.beans = (Beans) digester.parse(inputStream);
        parentBeanFactory.setBeans(this.beans);
    }

    @Override
    public Object getBean(String beanId) throws Exception {
        Bean bean = this.idBeanMapper.get(beanId);
        if (bean == null) {
            return null;
        }
        Class clz = Class.forName(bean.getClassName());
        Object obj = this.idObjectMapper.get(bean.getId());
        if (obj == null) {
            obj = clz.getDeclaredConstructor().newInstance();
        }
        if (bean.getProperties() != null) {
            for (Property property : bean.getProperties()) {
                String methodName = "set" + StringUtils.upperFirstChar(property.getName());
                Class argType = String.class;
                Object value = property.getValue();
                if (property.getRef() != null) {
                    value = this.idObjectMapper.get(property.getRef());
                    argType = value.getClass();
                }
                Method method = clz.getMethod(methodName, argType);

                method.invoke(obj, value);
            }
        }
        if (obj != null) {
            this.idObjectMapper.put(beanId, obj);
        }
        return obj;
    }


}
