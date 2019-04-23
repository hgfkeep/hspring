package win.hgfdodo.hspring.test;

import win.hgfdodo.hspring.factory.FacadeBeanFactory;
import win.hgfdodo.hspring.test.annotation.Author;
import win.hgfdodo.hspring.test.annotation.BookIntf;

public class Test {

    @org.junit.Test
    public void test() throws Exception {
        FacadeBeanFactory facadeBeanFactory = new FacadeBeanFactory();
        Author author = (Author) facadeBeanFactory.getBean("author");
        System.out.println(author);
        BookIntf book = (BookIntf) facadeBeanFactory.getBean("book");
        System.out.println();
//        while (true){
//            sleep(1000);
//            System.out.println(book);
//        }
        Author a = book.getAuthor();
        System.out.println(a);
    }
}
