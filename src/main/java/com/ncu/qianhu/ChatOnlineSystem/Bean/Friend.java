package com.ncu.qianhu.ChatOnlineSystem.Bean;

import lombok.*;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:20
 * @Modified by
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Friend {
    private String friendAId;
    private String friendBId;
    private String friendRemark;
    private int friendStat;
}
