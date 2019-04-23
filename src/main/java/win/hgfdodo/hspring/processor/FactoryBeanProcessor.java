package win.hgfdodo.hspring.processor;

import win.hgfdodo.hspring.factory.AbstractBeanFactory;
import win.hgfdodo.hspring.framework.FactoryBean;
import win.hgfdodo.hspring.utils.StringUtils;
import win.hgfdodo.hspring.def.Bean;

import java.lang.reflect.Method;

/**
 * 用户代码实现了FactoryBean处理
 */
public class FactoryBeanProcessor extends AbstractBeanProcessor {
    public FactoryBeanProcessor(AbstractBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected void process0(String beanId, Bean bean, Class clz, Object obj) throws Exception {
        Class[] classes = clz.getInterfaces();
        if (classes != null) {
            for (Class c : classes) {
                if (FactoryBean.class.getSimpleName().equals(c.getSimpleName())) {
                    // 替换beanId对应的对象
                    Method m = clz.getMethod("getObject", null);
                    Object targetObj = m.invoke(obj, null);
                    this.idObjectMapper.put(beanId, targetObj);
                    // 替换beanId中的clazz
                    Method m2 = clz.getMethod("getObjectType", null);
                    Object targetClazz = m2.invoke(obj, null);
                    this.idClassMapper.put(beanId, (Class) targetClazz);
                    // 暂时先不处理Bean对象
                    this.idBeanMapper.remove(beanId);

                    // 对于工厂bean，它的InitializingBean方法执行的时候，beanId还不是factoryBeanId
                    // 这样不影响工厂bean本身的DisposableBean接口的处理（InitializingBean接口已处理过）
                    String factoryBeanId = StringUtils.lowerFirstChar(clz.getSimpleName());
                    this.idClassMapper.put(factoryBeanId, clz);
                    this.idObjectMapper.put(factoryBeanId, obj);
                    this.idBeanMapper.put(factoryBeanId, bean);
                    break;
                }
            }
        }
    }
}
