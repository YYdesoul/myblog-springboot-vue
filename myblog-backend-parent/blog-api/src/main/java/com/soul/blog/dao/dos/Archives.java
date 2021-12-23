package com.soul.blog.dao.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Archives {
  private Integer year;

  private Integer month;

  private Long count;

}
