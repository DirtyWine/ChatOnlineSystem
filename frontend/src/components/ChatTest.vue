<template >
  <el-container style="height: 800px">
    <!--Header line-->
    <el-header style="height: 100px">
      <el-menu class="el-menu-demo"
               default-active="2"
               mode="horizontal"
               active-text-color="#000">

        <!--User avatar-->
        <el-menu-item index="1" >
          <div class="avatar-holder-user">
            <img class="avatar-user" src="../assets/avatar/f1.jpg"/>
          </div>
        </el-menu-item>

        <!--Welcome line-->
        <el-menu-item index="2">
          <span class="welcome-span">Welcome to WayTalk,</span>
          <span class="welcome-span">{{user}}</span>
        </el-menu-item>

        <!--Quit button-->
        <el-menu-item index="6" style="float: right">
          <button class="btn-quit"></button>
        </el-menu-item>

        <!--Friend request-->
        <el-menu-item index="5" style="float: right">
          <el-badge :value="1"
                    :max="10"
                    :hidden="false">
            <button @click="toReq()" class="btn-req"></button>
          </el-badge>
        </el-menu-item>

        <!--new tag-->
        <el-menu-item index="4" style="float: right">
          <el-badge :value="4"
                    :max="10"
                    :hidden="false">
            <button @click="toTag()" class="btn-tag"></button>
          </el-badge>
        </el-menu-item>

        <!--Search friend-->
        <el-menu-item  index="3" style="float: right">
          <el-input v-model="searchID"
                    @keyup.enter.native="search()"
                    placeholder="search friend">
            <i slot="suffix" class="iconfont icon-search-friend"/>
          </el-input>
        </el-menu-item>
      </el-menu>
    </el-header>

    <!--Body-->
    <el-container style="margin: 0px 20px">
      <!--Aside line-->
      <el-aside v-bind:style="{width: asideWidth}">
        <el-menu class="el-menu-vertical-demo nav-aside"
                 text-color="#414247"
                 active-text-color="#EE7E94"
                 :collapse="isCollapse">

          <!--Current Talking List-->
          <el-submenu index="1">
            <template slot="title">
              <i class="iconfont icon-talk"></i>
              <span slot="title">Talking</span>
            </template>
            <!--Talking-->
            <el-menu-item-group v-for="(talk,index) in talks" :key="talk.id">
              <el-menu-item :index="talk.id"  class="overflow" style="height: 80px;" @click="activeTalk(index)">
                <el-badge :hidden="!talk.hasNew"
                          :value="talk.new"
                          :max="99"
                class="item">
                  <img :src="talk.avatar" class="avatar-list">
                  <span>{{talk.remark}}</span>
                </el-badge>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <!--Friend List-->
          <el-submenu index="2">
            <template slot="title">
              <i class="iconfont icon-friends"></i>
              <span slot="title">Friends</span>
            </template>
            <!--Friends-->
            <el-menu-item-group v-for="friend in friends" :key="friend.id">
              <el-menu-item :index="friend.id" class="overflow">
                <el-popover
                  placement="right"
                  width="450"
                  trigger="hover">
                    <el-row>
                      <el-col :span="12">
                        <img :src="friend.avatar" class="avatar-card">
                      </el-col>
                      <el-col :span="12">
                        <h1 style="font-weight: bold;font-size: 30px">{{friend.remark}}</h1>
                        <h3 style="font-size: 18px;color: #666666;font-weight: bold;font-style: italic">{{friend.motto}}</h3>
                        <p>{{friend.gender}}   {{friend.age}}</p>
                        <span>
                          <i slot="suffix" @click="delDialogVisible = true" class="iconfont icon-del icon-card"></i>
                          <i slot="suffix" @click="tagFormVisible = true"  class="iconfont icon-tag icon-card"></i>
                          <i slot="suffix" @click="remarkFormVisible = true" class="iconfont icon-remark icon-card"></i>
                        </span>
                      </el-col>
                    </el-row>
                  <img slot="reference" :src="friend.avatar" class="avatar-list">
                </el-popover>
                <span>{{friend.remark}}</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <!--Chat Window-->
      <el-container v-show="showChat">
        <el-main>
          <div v-for="talk in talks" :key="talk.id">
            <el-collapse-transition>
              <div v-show="talk.active" >
                <!--Title-->
                <el-container style="height: 20px">
                </el-container>
                <div v-for="msg in talk.message" :key="msg.id" id="ChatWindow">
                  {{msg.time}}
                  <el-row style="width: 100%;margin-bottom: 20px">
                    <el-col :span="2" class="avatar-holder-max" v-bind:style="{float: msg.location}">
                      <div class="avatar-holder">
                        <img class="avatar" :src="msg.avatar"/>
                      </div>
                    </el-col>
                    <el-col :span="8" v-bind:style="{textAlign: msg.location,float:msg.location}">
                      <span style="font-size: 20px;font-weight: bold">{{msg.remark}}</span>
                      <div>
                        <p class="talk-holder" >{{msg.content}}</p>
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </div>
            </el-collapse-transition>
          </div>
        </el-main>

        <!--input message-->
        <el-footer style="height: 100px;">
          <el-input v-model="content"
                    placeholder="What's up?"
                    class="input-msg"
                    type="textarea">
          </el-input>
          <el-button class="btn-send">Send</el-button>
        </el-footer>

      </el-container><!--chat window-->

      <!--Search friends-->
      <el-collapse-transition>
        <div style="width: 100%;" v-show="showSearch">
          <el-main>
            <el-row>
              <el-button @click="back()" style="float: left;margin-left: 30px" type="info">Back</el-button>
            </el-row>
            <el-row>
              <el-col :span="8" v-for="(result,index) in results" :key="index">
                <el-card :body-style="{ padding: '0px' }" style="margin: 30px">
                  <img :src="result.avatar" class="image">
                  <div style="padding: 14px;">
                    <div class="bottom clearfix">
                      <span>{{result.nickname}}</span>
                      <p class="id-line">ID: {{ result.id }}</p>
                      <el-button type="info" class="item-right">加好友</el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-main>
        </div>
      </el-collapse-transition>

      <!--Tag Window-->
      <el-collapse-transition>
        <div style="width: 100%;" v-show="showTags">
          <el-main>
            <el-row>
              <el-button @click="back()" style="float: left;margin-left: 30px" type="info">Back</el-button>
            </el-row>
            <el-row>
              <el-col :span="8" v-for="(item,index) in tags" :key="index">
                <el-card :body-style="{ padding: '0px' }" style="margin: 30px; width: 480px">
                  <div slot="header" class="clearfix">
                    <span style="float: left;font-weight: bold;font-size: 20px">{{item.remark}} 对我的印象</span>
                  </div>
                  <div v-for="(tag,index) in item.tag" :key="index" class="bottom">
                    {{tag}}
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-main>
        </div>
      </el-collapse-transition>

      <!--Req Window-->
      <el-collapse-transition>
        <div style="width: 100%;" v-show="showReq">
          <el-main>
            <el-row>
              <el-button @click="back()" style="float: left;margin-left: 30px" type="info">Back</el-button>
            </el-row>
            <el-row>
              <el-col :span="8" v-for="(item,index) in req" :key="index">
                <el-card :body-style="{ padding: '0px' }" style="margin: 30px">
                  <div slot="header" class="clearfix">
                    <span style="float: left;font-weight: bold;font-size: 20px;margin-left: 13px">好友请求</span>
                    <el-button type="success" class="item-right">接受</el-button>
                  </div>
                  <img :src="item.avatar" class="image">
                  <div style="padding: 14px;">
                    <div class="bottom clearfix">
                      <span>{{item.nickname}}</span>
                      <p class="id-line">ID: {{item.id}}</p>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-main>
        </div>
      </el-collapse-transition>


    </el-container><!--aside -->

    <!--Dialogs-->
    <!--Tag Dialog-->
    <el-dialog title="WRITE YOUR FRIEND A TAG" :visible.sync="tagFormVisible">
      <el-form >
        <el-form-item label="What do you think about your friend">
          <el-input  auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="tagFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="tagFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <!--Remark Dialog-->
    <el-dialog title="Set Remark" :visible.sync="remarkFormVisible">
      <el-form >
        <el-form-item>
          <el-input  auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="remarkFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="remarkFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <!--Del Dialog-->
    <el-dialog
      title="Are you sure"
      :visible.sync="delDialogVisible"
      width="30%"
      center>
      <span>Are you sure to delete this friend</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delDialogVisible = false">取 消</el-button>
        <el-button type="danger" @click="delDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </el-container><!--header -->

</template>

<script>
    import ElCollapseTransition from "element-ui/src/transitions/collapse-transition";
    export default {
        name: "ChatTest",
      components: {ElCollapseTransition},
      data() {
        return {
          tagFormVisible: false,
          remarkFormVisible: false,
          delDialogVisible: false,
          isCollapse: false,
          showChat: true,
          showSearch: false,
          showTags: false,
          showReq: false,
          asideWidth: '220px',
          searchID: '',
          user:'user',
          content: '',
          show: true,
          talks:[
            {
              topic: true,
              id: 'WayTalk',
              avatar: require('../assets/avatar/way.jpg'),
              remark: 'WayTalk',
              hasNew: true,
              new: 1,
              active: true,
              message: [
                {
                  id:'1',
                  location: 'left',
                  avatar: require('../assets/avatar/f2.jpg'),
                  remark: 'Qi',
                  content: 'Hi',
                  time: '2018-6-12 18:10:30'
                },
                {
                  id:'2',
                  location: 'right',
                  avatar: require('../assets/avatar/f1.jpg'),
                  remark: 'Me',
                  content: 'Welcome',
                  time: '2018-6-12 18:10:30'
                },
                {
                  id:'3',
                  location: 'left',
                  avatar: require('../assets/avatar/m1.jpg'),
                  remark: 'Loki',
                  content: 'Hahaha',
                  time: '2018-6-12 18:10:30'
                },
                {
                  id:'4',
                  location: 'left',
                  avatar: require('../assets/avatar/m2.jpg'),
                  remark: 'Fen',
                  content: 'What are you laughing at',
                  time: '2018-6-12 18:10:30'
                }
              ]
            },
            {
              topic: false,
              id: 'Loki',
              avatar: require('../assets/avatar/m1.jpg'),
              remark: 'Loki',
              hasNew: false,
              new: 0,
              active: false,
              message: [
                {
                  id:'1',
                  location: 'left',
                  avatar: require('../assets/avatar/m1.jpg'),
                  remark: 'Loki',
                  content: 'Finally,it is going to finish!',
                  time: '2018-6-12 18:10:30'
                },
                {
                  id:'2',
                  location: 'right',
                  avatar: require('../assets/avatar/f1.jpg'),
                  remark: 'Me',
                  content: 'I am going to sleep...',
                  time: '2018-6-12 18:10:30'
                }
              ]
            },
            {
              topic: false,
              id: 'Qi',
              avatar: require('../assets/avatar/f2.jpg'),
              remark: 'Qi',
              hasNew: false,
              new: 0,
              active: false,
              message: [
                {
                  id:'1',
                  location: 'left',
                  avatar: require('../assets/avatar/f2.jpg'),
                  remark: 'Qi',
                  content: 'See you at school!',
                  time: '2018-6-12 18:10:30'
                },
                {
                  id:'2',
                  location: 'right',
                  avatar: require('../assets/avatar/f1.jpg'),
                  remark: 'Me',
                  content: 'Yes, see ya',
                  time: '2018-6-12 18:10:30'
                },
                {
                  id:'3',
                  location: 'right',
                  avatar: require('../assets/avatar/f1.jpg'),
                  remark: 'Me',
                  content: 'take care',
                  time: '2018-6-12 18:10:30'
                }
              ]
            },
          ],
          friends:[
            {
              id: '#1',
              avatar: require('../assets/avatar/m1.jpg'),
              remark: 'Loki',
              motto: 'Son of Odin',
              gender: 'Man',
              age: '1800'
            },
            {
              id: '#2',
              avatar: require('../assets/avatar/f2.jpg'),
              remark: 'Qi',
              motto: 'Sad',
              gender: 'Girl',
              age: '20'
            },
            {
              id: '#3',
              avatar: require('../assets/avatar/m2.jpg'),
              remark: 'Fen',
              motto: 'Sad too',
              gender: 'Man',
              age: '28'
            },
            {
              id: '#4',
              avatar: require('../assets/avatar/f1.jpg'),
              remark: 'Kart',
              motto: 'Eat what you kill',
              gender: 'Girl',
              age: '18'
            }
          ],
          results: [
            {
              id: 'Lu',
              avatar: require('../assets/avatar/m3.jpg'),
              nickname: '风中一匹狼',
            },
            {
              id: '4u',
              avatar: require('../assets/avatar/f1.jpg'),
              nickname: '风中一匹狼',
            },
            {
              id: '7u',
              avatar: require('../assets/avatar/f2.jpg'),
              nickname: '风中一匹狼',
            },
            {
              id: '7u',
              avatar: require('../assets/avatar/m1.jpg'),
              nickname: '风中一匹狼',
            },
            {
              id: '7u',
              avatar: require('../assets/avatar/m2.jpg'),
              nickname: '风中一匹狼',
            }
          ],
          req: [
            {
              id: '456',
              nickname: '一枝梅',
              avatar: require('../assets/avatar/m3.jpg'),
            }
          ],
          tags: [
            {
              remark:'friend1',
              tag: [
                'tag1',
                'tag2'
              ]
            },
            {
              remark:'friend2',
              tag: [
                'tag1',
                'tag2'
              ]
            }
          ],
        };
      },

      methods: {
        setFalse: function() {
          this.showChat = false
          this.showSearch = false
          this.showTags = false
          this.showReq = false
        },
        search: function() {
          this.setFalse()
          this.showSearch = true
        },

        toTag: function() {
          this.setFalse()
          this.showTags = true
        },
        toReq:function () {
          this.setFalse()
          this.showReq = true
        },

        back: function() {
          this.setFalse()
          this.showChat = true
        },

        collapse:function() {
          this.isCollapse = !this.isCollapse
          if(this.isCollapse)
            this.asideWidth = '70px'
          else
            this.asideWidth = '210px'
        },

        activeTalk: function(index) {
          for(var i = 0;i<this.talks.length;i++) {
            this.$set(this.talks[i],'active',false)
          }
          this.$set(this.talks[index],'active',true)
        },
      }
    }
</script>

<style scoped>

  /*header*/
  .welcome-span {
    font-size: 50px;
    font-weight: bold
  }

  .avatar-holder-user {
    height: 100px;
    width: 100px;
    background: url("../assets/avatar.png");
    background-size: 100% 100%;
  }

  .avatar-user {
    height: 86px;
    width: 86px;
    border-radius: 43px;
    margin-top: 6px;
    margin-left: 2px;
  }

  .btn-quit {
    border: none;
    height: 20px;
    width: 20px;
    background: url("../assets/quit.png");
    background-size: 100% 100%;
  }

  .btn-req {
    border: none;
    height: 35px;
    width: 35px;
    background: url("../assets/add.png");
    background-size: 100% 100%;
  }

  .btn-tag {
    border: none;
    height: 35px;
    width: 35px;
    background: url("../assets/tag.png");
    background-size: 100% 100%;
  }

  /*Aside*/
  .nav-aside {
    float: left;
    text-align: left;
    font-weight: bold;
    font-size: 30px;
  }

  .avatar-list {
    width: 40px;
    height: 40px;
    border-radius: 20px
  }

  .item {
    margin-top: 15px;
  }

  .el-submenu span {
    font-size: 20px;
  }
  .el-menu-item span {
    font-size: 17px;

  }
  .overflow {
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .avatar-card {
    width: 200px;
    height: 200px
  }

  .icon-card {
    float: right;
    font-size: 25px;
    margin: 10px
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }

  /*Chat Window*/
  .btn-send {
    background: url("../assets/btn.png");
    border: none;
    background-size: 100% 100%;
    color: white;
    font-size: 20px;
    float: right
  }

  .input-msg {
    font-size: 20px;
    font-weight: bold;
  }

  .avatar-holder {
    height: 80px;
    width: 80px;
    background: url("../assets/avatar.png");
    background-size: 100% 100%;
  }

  .avatar-holder-max {
    max-width: 90px
  }

  .avatar {
    height: 66px;
    width: 66px;
    border-radius: 33px;
    margin-top: 6px;
    margin-left: 2px;
  }

  .talk-holder {
    display:inline-block;
    background: url('../assets/talk_bg.png');
    background-size: 100% 100%;
    color: white;
    padding:0 20px;
    font-size: 25px;
    word-wrap: break-word;
    word-break: normal;
  }

  /*card*/
  .image {
    width: 100%;
    display: block;
  }

  .bottom {
    margin: 13px;
    line-height: 20px;
    font-weight: bold;
    font-size: 20px;
    text-align: left;
  }

  .id-line {
    font-size: 16px;
    text-align: left;
    color: #999;
  }

  .item-right {
    float: right;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

</style>
