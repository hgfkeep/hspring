package win.hgfdodo.hspring.processor;

import win.hgfdodo.hspring.def.Advice;
import win.hgfdodo.hspring.def.Aspect;
import win.hgfdodo.hspring.def.Bean;
import win.hgfdodo.hspring.def.Before;
import win.hgfdodo.hspring.factory.AbstractBeanFactory;
import win.hgfdodo.hspring.utils.CollectionUtils;
import win.hgfdodo.hspring.utils.MapUtils;
import win.hgfdodo.hspring.utils.ReflectUtils;
import win.hgfdodo.hspring.utils.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AopBeanProcessor extends AbstractBeanProcessor {
    //要切入的方法与切入类和方法的映射:
    private Map<String, Map<String, List<Advice>>> pointcut2Aspect = new HashMap<>();

    public AopBeanProcessor(AbstractBeanFactory parentBeanFactory) {
        super(parentBeanFactory);
        parseAspect();
    }

    /**
     * 将Aspect转为map数据结构
     */
    private void parseAspect() {
        List<Aspect> aspects = this.beans.getAspects();
        for (Aspect aspect : aspects) {
            List<Advice> advices = aspect.getAdvices();
            for (Advice advice : advices) {
                String pointcut = advice.getPointcut();
                String ref = aspect.getRef();
                Map<String, List<Advice>> aspect2Advice = pointcut2Aspect.get(pointcut);
                if (MapUtils.isEmpty(aspect2Advice)) {
                    aspect2Advice = new HashMap<String, List<Advice>>();
                    pointcut2Aspect.put(pointcut, aspect2Advice);
                }
                List<Advice> advcs = aspect2Advice.get(ref);
                if (CollectionUtils.isEmpty(advcs)) {
                    advcs = new ArrayList<Advice>();
                    aspect2Advice.put(ref, advcs);
                }
                advcs.add(advice);
            }
        }
    }

    @Override
    protected void process0(String beanId, Bean bean, Class clz, Object obj) throws Exception {
        for (String pointcut : pointcut2Aspect.keySet()) {
            if (pointcut.startsWith(clz.getName())) {
                Map<String, List<Advice>> aspect2Advices = pointcut2Aspect.get(pointcut);
                ProxyHandler proxyHandler = new ProxyHandler(obj, aspect2Advices);
                Object proxObject = Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), proxyHandler);
                idObjectMapper.put(beanId, proxObject);
                break;
            }
        }
    }

    public class ProxyHandler implements InvocationHandler {
        private Object object;
        private Map<String, List<Advice>> aspect2Advices;

        public ProxyHandler(Object object, Map<String, List<Advice>> aspect2Advices) {
            this.object = object;
            this.aspect2Advices = aspect2Advices;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            //处理Before
            for (String ref : aspect2Advices.keySet()) {
                List<Advice> advices = aspect2Advices.get(ref);
                Object adviceObj = parentBeanFactory.getBean(ref);

                for (Advice advice : advices) {
                    if (advice instanceof Before) {
                        ReflectUtils.invokeMethod(adviceObj, advice.getMethod(), null, null);
                    }
                }
            }
            // 转调具体目标对象的方法
            return method.invoke(object, args);

            // 在转调具体目标对象之后，可以执行一些功能处理
        }
    }
}


