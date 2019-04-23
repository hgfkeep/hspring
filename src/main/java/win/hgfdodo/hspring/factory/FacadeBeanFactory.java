package win.hgfdodo.hspring.factory;

import win.hgfdodo.hspring.framework.BeanOperator;
import win.hgfdodo.hspring.processor.*;

import java.util.Arrays;
import java.util.List;

public class FacadeBeanFactory extends AbstractBeanFactory {
    private Thread shutdownHook;
    private XmlBeanFactory xmlBeanFactory;
    private AnnotationBeanFactory annotationBeanFactory;
    private List<AbstractBeanProcessor> beanProcessors;
    private BeanOperator frameworkContext;


    public FacadeBeanFactory() {
        super(true);
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() throws Exception {
        xmlBeanFactory = new XmlBeanFactory(this);
        xmlBeanFactory.load();
        annotationBeanFactory = new AnnotationBeanFactory(this);
        annotationBeanFactory.load();
        beanProcessors = Arrays.asList(new InitializingBeanProcessor(this),
                new FactoryBeanProcessor(this), new AopBeanProcessor(this));
        registerShutdownHook();
    }

    @Override
    public Object getBean(String beanId) throws Exception {
        xmlBeanFactory.getBean(beanId);
        annotationBeanFactory.getBean(beanId);
        for (AbstractBeanProcessor processor : beanProcessors) {
            processor.process(beanId);
        }
        return this.getIdObjectMapper().get(beanId);
    }

    private void registerShutdownHook() {
        if (this.shutdownHook == null) {
            this.shutdownHook = new Thread() {
                @Override
                public void run() {
                    destroyBeans();
                }
            };

            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }
    }

    private void destroyBeans() {
        try {
            new DestroyBeanProcessor(this).process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
