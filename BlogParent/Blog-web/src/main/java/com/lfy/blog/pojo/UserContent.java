package com.lfy.blog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章表
 */
@Data
public class UserContent  implements Serializable {
    /**
     * 主键由数据库自动生成（主要是自动增长型）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长策略
    private Long id;

    private Long uId;

    private String title;

    private String category;

    private String personal;

   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rptTime;

   //用户头像
    private String imgUrl;

    private String nickName;

    private Integer upvote;

    private Integer downvote;

    private Integer commentNum;

    private String content;

    private String titleName;

    //标题图片
    private String titlePic;


}

