package win.hgfdodo.hspring.processor;

import win.hgfdodo.hspring.factory.AbstractBeanFactory;
import win.hgfdodo.hspring.framework.BeanOperator;
import win.hgfdodo.hspring.def.Bean;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractBeanProcessor extends BeanOperator implements BeanProcessor {

    protected AbstractBeanFactory parentBeanFactory;

    public AbstractBeanProcessor(AbstractBeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
        this.idBeanMapper = parentBeanFactory.getIdBeanMapper();
        this.idClassMapper = parentBeanFactory.getIdClassMapper();
        this.idObjectMapper = parentBeanFactory.getIdObjectMapper();
        this.beans = parentBeanFactory.getBeans();
    }

    @Override
    public void process() throws Exception {
        for (String beanId : this.idObjectMapper.keySet()) {
            processOne(beanId);
        }
    }

    @Override
    public void process(String beanId) throws Exception {
        processOne(beanId);
    }

    private void processOne(String beanId) throws Exception {
        Bean bean = this.idBeanMapper.get(beanId);
        Class clz = this.idClassMapper.get(beanId);
        Object obj = this.idObjectMapper.get(beanId);

        process0(beanId,bean, clz, obj);
    }

    /**
     * 处理单个Bean的实际实现
     *
     * @param bean
     * @param clz
     * @param obj
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */
    protected abstract void process0(String beanId, Bean bean, Class clz, Object obj) throws Exception;

}
