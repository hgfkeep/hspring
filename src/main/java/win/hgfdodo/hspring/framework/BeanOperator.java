package win.hgfdodo.hspring.framework;

import win.hgfdodo.hspring.def.Bean;
import win.hgfdodo.hspring.def.Beans;

import java.util.HashMap;
import java.util.Map;

public class BeanOperator {
    protected Beans beans = null;
    protected Map<String, Class> idClassMapper;
    protected Map<String, Object> idObjectMapper;
    protected Map<String, Bean> idBeanMapper;

    public BeanOperator() {
    }

    public BeanOperator(boolean init) {
        if (init) {
            idClassMapper = new HashMap<String, Class>();
            idObjectMapper = new HashMap<String, Object>();
            idBeanMapper = new HashMap<String, Bean>();
        }
    }

    public Beans getBeans() {
        return beans;
    }

    public void setBeans(Beans beans) {
        this.beans = beans;
    }

    public Map<String, Class> getIdClassMapper() {
        return idClassMapper;
    }

    public void setIdClassMapper(Map<String, Class> idClassMapper) {
        this.idClassMapper = idClassMapper;
    }

    public Map<String, Object> getIdObjectMapper() {
        return idObjectMapper;
    }

    public void setIdObjectMapper(Map<String, Object> idObjectMapper) {
        this.idObjectMapper = idObjectMapper;
    }

    public Map<String, Bean> getIdBeanMapper() {
        return idBeanMapper;
    }

    public void setIdBeanMapper(Map<String, Bean> idBeanMapper) {
        this.idBeanMapper = idBeanMapper;
    }
}
