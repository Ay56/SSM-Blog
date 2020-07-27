package com.lfy.blog.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 */
@Data
public class Comment  implements Serializable {
    /**
     * 主键由数据库自动生成（主要是自动增长型）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long conId;

    private Long comId;

    private Long byId;

    private Date commTime;

    private String children;

    private Integer upvote;

    private String comContent;

}
