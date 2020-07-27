package com.lfy.blog.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 */
@Data
public class loginLog  implements Serializable {

    /**
     * 登录主键
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长策略
    private Long  id;

    /**
     * 对应的用户id
     */
    private Long uId;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登录时间
     */
    private Date createTime;
    /**
     * 登录次数
     */
    private Long  loginNum;

    /**
     * 登录状态
     */
    private Long status;
}
