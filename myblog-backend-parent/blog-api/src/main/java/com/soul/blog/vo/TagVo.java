package com.soul.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagVo {

    private String id;  // why not Long?

    private String tagName;

    private String avatar;
}
