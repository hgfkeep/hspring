package win.hgfdodo.hspring.processor;

public interface BeanProcessor {
    /**
     * 处理单个Bean
     * @param beanId
     * @throws Exception
     */
    void process(String beanId) throws Exception;

    /**
     * 处理所有初始化的java类
     * @throws Exception
     */
    void process() throws Exception;
}