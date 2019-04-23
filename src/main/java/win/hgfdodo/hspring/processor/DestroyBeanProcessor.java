package win.hgfdodo.hspring.processor;

import win.hgfdodo.hspring.factory.AbstractBeanFactory;
import win.hgfdodo.hspring.framework.DestroyBean;
import win.hgfdodo.hspring.utils.StringUtils;
import win.hgfdodo.hspring.def.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DestroyBeanProcessor extends AbstractBeanProcessor {
    public DestroyBeanProcessor(AbstractBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected void process0(String beanId, Bean bean, Class clz, Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, Exception {
        // 处理destroy-method
        if (null != bean && StringUtils.isNotEmpty(bean.getDestroyMethod())) {
            Method m = clz.getMethod(bean.getDestroyMethod(), null);
            m.invoke(obj, null);
        }

        // 处理继承了DestroyBean接口的类
        Class[] classes = clz.getInterfaces();
        if (classes != null) {
            for (Class c : classes) {
                if (DestroyBean.class.getSimpleName().equals(c.getSimpleName())) {
                    Method m = clz.getMethod("destroy", null);
                    m.invoke(obj, null);
                    break;
                }
            }
        }

    }
}
