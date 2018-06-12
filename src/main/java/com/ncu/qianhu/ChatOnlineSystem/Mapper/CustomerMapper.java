package com.ncu.qianhu.ChatOnlineSystem.Mapper;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/5/28
 * @Modified by
 */

@Component
@Mapper
public interface CustomerMapper {
    ArrayList<Customer> find(Customer record);
}
