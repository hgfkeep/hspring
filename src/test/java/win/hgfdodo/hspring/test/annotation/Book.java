package win.hgfdodo.hspring.test.annotation;

import win.hgfdodo.hspring.annotation.Component;
import win.hgfdodo.hspring.annotation.Resource;
import win.hgfdodo.hspring.annotation.Value;

@Component
public class Book implements BookIntf{
    @Resource
    private Author author;
    @Value("book title")
    private String title;

    public Author getAuthor() {
        System.out.println("get   ... ");
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
