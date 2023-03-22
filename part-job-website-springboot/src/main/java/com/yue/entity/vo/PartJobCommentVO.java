package com.yue.entity.vo;

import com.yue.entity.PartJobComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PartJobCommentVO extends PartJobComment {

    //创建该评论用户的信息
    private UserVO author;
    //该评论父级评论的用户信息
    private UserVO toUser;

    private List<PartJobCommentVO> childComment;
}
