package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recId;
    private Long uId;
    @TableField("u_name")
    private String uName;
    private String content;
    private Long likeCount;
    private LocalDateTime createTime;
}
