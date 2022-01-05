package com.soul.blog.admin.model.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
