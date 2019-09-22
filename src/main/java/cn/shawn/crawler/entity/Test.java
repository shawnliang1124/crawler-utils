package cn.shawn.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-13 13:11
 * @package cn.shawn.crawler.entity
 * @contact https://github.com/shawnliang1124
 */
@Entity
@Table(name = "test", schema = "mall-goods", catalog = "")
public class Test {
    private int id;
    private String title;
    private String content;



    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Test(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Test(){}
}
