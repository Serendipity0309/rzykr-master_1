package com.rzyk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;


@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiObVo implements Serializable {

    private String processId;

    private String texamId;

    private String userId;
}
