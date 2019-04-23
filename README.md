# hspring

实现spring，深入理解spring aop的原理

## 实现过程

1. digeter实现xml -> java object
2. 解析注解, annotation -> java object
3. BeanFactory生产Bean，添加init和destroy方法
4. FactoryBean，构造BeanFactory
5. AOP处理

## 核心逻辑

FactoryBean 解析xml配置和annotation，存在内存中，对应本实现的`BeanOperator`中。

FactoryBean添加Bean destroy回调；

在实际使用时，`getBean(id)`，分别从xml配置和annotation处构造Bean对象，构造好Bean对象后，需要改造Bean；

改造Bean在本实现中就是经过一系列的process处理：

* InitializingBeanProcessor：调用Bean的init回调
* AopBeanProcessor：使用代理增强Bean
* FactoryBeanProcessor：处理FactoryBean

> BeanFactory 和 FactoryBean的区别：
BeanFactory是一个Factory，即工厂实现，主要负责生产Bean，管理Bean，但是没有具体的实现，仅仅是接口；具体的实现包括了XmlBeanFactory和AnnotationBeanFactory；
FactoryBean，用户可以通过实现该接口定制实例化Bean的逻辑。

