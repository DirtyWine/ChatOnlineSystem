package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Customer;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/5/28
 * @Modified by
 */

@RestController
@RequestMapping("/customer/")
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @CrossOrigin
    @GetMapping("/test")
    public ResponseEntity<String> test(Customer requestCustomer) {

        System.out.println(requestCustomer.toString());
        try {
            Customer queryCustomer = customerMapper.find(requestCustomer);
            if (null == queryCustomer) {
                return new ResponseEntity<>("您输入的用户名不存在", HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>("登录成功\t"+queryCustomer.getGender(), HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error",HttpStatus.NOT_FOUND);
        }
    }
}
