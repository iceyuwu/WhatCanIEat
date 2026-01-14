package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.logging.log4j.message.StringFormattedMessage;

@Data
@TableName("ingredients")
public class Ingredient {
    private long id;
    private String name;
    private int type;
    private  int calorie;
}
