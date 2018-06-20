package com.ncu.qianhu.ChatOnlineSystem.Controller;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Friend;
import com.ncu.qianhu.ChatOnlineSystem.Bean.Tag;
import com.ncu.qianhu.ChatOnlineSystem.Bean.TagResult;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.FriendMapper;
import com.ncu.qianhu.ChatOnlineSystem.Mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author: CXX
 * @Description:
 * @Date: 2018/6/11 13:45
 * @Modified by
 */
@RestController
@RequestMapping("/tag/")
public class TagController {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private FriendMapper friendMapper;

    @CrossOrigin
    @PostMapping("/insertTag")
    public ResponseEntity<String> addTag(@RequestBody Tag requestTag) {
        System.out.println(requestTag.toString());
        try{
            tagMapper.insertTag(requestTag);
            return new ResponseEntity<>("Insert Successfully", HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error",HttpStatus.OK);
        }
    }

    @CrossOrigin
    @PostMapping("/get")
    public ArrayList<TagResult> getTag(@RequestBody Tag requestTag) {
        System.out.println(requestTag.toString());
        try{
            ArrayList<Tag> senders = tagMapper.findSender(requestTag);
            ArrayList<TagResult> tags = new ArrayList<>();
            if (! senders.isEmpty()) {
                for (int i = 0; i < senders.size(); i++ ) {
                    TagResult tagResult = new TagResult();
                    tagResult.setTag(new ArrayList<String>());
                    Friend friend = new Friend();
                    friend.setFriendAId(requestTag.getTagReceiverId());
                    friend.setFriendBId(senders.get(i).getTagSenderId());
                    friend = friendMapper.findRemark(friend);
                    tagResult.setRemark(friend.getFriendRemark());
                    requestTag.setTagSenderId(senders.get(i).getTagSenderId());
                    System.out.println(requestTag.toString());
                    ArrayList<Tag> content = tagMapper.findTag(requestTag);
                    for(int j = 0; j < content.size(); j++) {
                        tagResult.getTag().add(content.get(j).getTagContent());
                    }
                    tags.add(tagResult);
                }
            }
            return tags;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<TagResult>();
        }
    }

}
