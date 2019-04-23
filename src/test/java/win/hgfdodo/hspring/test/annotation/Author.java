package win.hgfdodo.hspring.test.annotation;

import win.hgfdodo.hspring.annotation.Component;
import win.hgfdodo.hspring.annotation.Value;

@Component
public class Author {
    @Value("author name")
    private String name;

    public void before(){
        System.out.println("Before author print");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
