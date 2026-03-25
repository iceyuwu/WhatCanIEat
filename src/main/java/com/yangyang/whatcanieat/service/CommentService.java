package com.yangyang.whatcanieat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.whatcanieat.entity.Comment;
import com.yangyang.whatcanieat.mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {
}