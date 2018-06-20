package com.ncu.qianhu.ChatOnlineSystem.Mapper;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:46
 * @Modified by
 */

@Component
@Mapper
public interface TagMapper {
    ArrayList<Tag> findTag(Tag record);
    void insertTag(Tag record);
    ArrayList<Tag> findSender(Tag record);
    void deleteTag(Tag record);
}
