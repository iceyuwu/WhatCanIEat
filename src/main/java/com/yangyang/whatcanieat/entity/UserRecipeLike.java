package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_recipe_like")
public class UserRecipeLike {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long uId;
    private Long recId;
    private LocalDateTime createTime;

}
