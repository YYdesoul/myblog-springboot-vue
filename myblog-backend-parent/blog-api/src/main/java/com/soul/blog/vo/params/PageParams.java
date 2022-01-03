package com.soul.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * constant value of paging
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageParams {

  private int page = 1;

  private int pageSize = 10;

  private Long categoryId;

  private Long tagId;

  private String year;

  private String month;

  public String getMonth(){
    if (this.month != null && this.month.length() == 1){
      return "0"+this.month;
    }
    return this.month;
  }
}
