package win.hgfdodo.hspring.factory;

import win.hgfdodo.hspring.framework.BeanOperator;
import win.hgfdodo.hspring.utils.StringUtils;

/**
 * FactoryBean使用简单工厂方法生产BeanFactory。
 * AbstractBeanFactory 是 FactoryBean 工厂生产的所有Bean的基类抽象。
 */
public abstract class AbstractBeanFactory extends BeanOperator implements BeanFactory {
    public AbstractBeanFactory() {
    }

    public AbstractBeanFactory(boolean init) {
        super(init);
    }

    public abstract void load() throws Exception;

    public abstract Object getBean(String beanId) throws Exception;

}
