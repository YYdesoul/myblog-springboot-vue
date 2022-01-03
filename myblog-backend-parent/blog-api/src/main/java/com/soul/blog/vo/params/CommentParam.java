package com.soul.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
