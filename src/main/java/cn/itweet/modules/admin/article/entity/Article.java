package cn.itweet.modules.admin.article.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by whoami on 15/04/2017.
 */
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    /**
     * 是否已经发布文章，0为未发布，1为已发布
     */
    private Integer state;

    /**
     * 文章类型，目前只有两种类型，博客类型：0，推文类型：1
     */
    @Column(name = "type_article")
    private Integer typeArticle;

    /**
     * 博客文章描述，一般截取文章正文的前15个字段
     */
    private String description;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 文章的正文内容
     */
    @Column(name = "content",columnDefinition="TEXT")
    private String content;
    @Column(name = "htmlContent",columnDefinition="TEXT")
    private String htmlContent;

    /**
     * 文章的作者
     */
    private String author;

    @Column(name = "cover_picture")
    private String coverPicture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Integer getTypeArticle() {
        return typeArticle;
    }

    public void setTypeArticle(Integer typeArticle) {
        this.typeArticle = typeArticle;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", author='" + author + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                '}';
    }
}
