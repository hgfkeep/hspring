package win.hgfdodo.hspring.processor;

import win.hgfdodo.hspring.factory.AbstractBeanFactory;
import win.hgfdodo.hspring.utils.StringUtils;
import win.hgfdodo.hspring.def.Bean;

import java.lang.reflect.Method;

public class InitializingBeanProcessor extends AbstractBeanProcessor {

    public InitializingBeanProcessor(AbstractBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected void process0(String beanId, Bean bean, Class clz, Object obj) throws Exception {

        //处理init-methodxml标签
        if (bean != null && StringUtils.isNotEmpty(bean.getInitMethod())) {
            Method m = clz.getMethod(bean.getInitMethod(), null);
            m.invoke(obj, m);
            return;  //优先处理init-method，如果同时包含init-method和adterPropertiesSet，仅处理init-method
        }
        // 处理InitializingBean的afterPropertiesSet方法
        Class[] clzs = clz.getInterfaces();
        for (Class c : clzs) {
            if (c.getSimpleName().equals("InitializingBean")) {
                Method m = clz.getMethod("afterPropertiesSet", null);
                m.invoke(obj, m);
                break;
            }
        }
    }
}
