package win.hgfdodo.hspring.factory;

/**
 * BeanFactory是Bean生产工厂
 */
public interface BeanFactory {

    /**
     * 加载Bean
     *
     * @throws Exception
     */
    public void load() throws Exception;

    /**
     * 获取Object
     *
     * @param beanId
     * @return
     * @throws Exception
     */
    public Object getBean(String beanId) throws Exception;

}
