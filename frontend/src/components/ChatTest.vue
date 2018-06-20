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
            <img class="avatar-user" :src="myAvatar"/>
          </div>
        </el-menu-item>

        <!--Welcome line-->
        <el-menu-item index="2" style="" v-show="!isCollapse">
          <span class="welcome-span">Welcome to WayTalk,</span>
          <span class="welcome-span">{{user}}</span>
        </el-menu-item>

        <!--Quit button-->
        <el-menu-item index="6" style="float: right">
          <button @click="quit" class="btn-quit"></button>
        </el-menu-item>

        <!--Friend request-->
        <el-menu-item index="5" style="float: right">
          <el-badge :value="req.content.length"
                    :max="10"
                    :hidden="!req.hasReq">
            <button @click="toReq()" class="btn-req"></button>
          </el-badge>
        </el-menu-item>

        <!--new tag-->
        <el-menu-item index="4" style="float: right">
            <button @click="toTag()" class="btn-tag"></button>
        </el-menu-item>

        <!--Search friend-->
        <el-menu-item  index="3" style="float: right">
          <el-input v-model="searchID"
                    @keyup.enter.native="search()"
                    placeholder="search friend">
            <i @click="search()" slot="suffix" class="iconfont icon-search-friend"/>
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
                 default-active="WayTalk"
                 :collapse="isCollapse">

          <!--Current Talking List-->
          <el-submenu index="-2">
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
          <el-submenu index="-1">
            <template slot="title">
              <i class="iconfont icon-friends"></i>
              <span slot="title">Friends</span>
            </template>
            <!--Friends-->
            <el-menu-item-group v-for="(friend,index) in friends" :key="friend.id">
              <el-menu-item :index="friend.id" class="overflow" @click="startChat(index)">
                <el-popover
                  placement="right"
                  width="450"
                  trigger="hover">
                    <el-row>
                      <el-col :span="12">
                        <img :src="friend.avatar" class="avatar-card">
                      </el-col>
                      <el-col :span="12">
                        <h1 style="font-weight: bold;font-size: 25px">{{friend.remark}}</h1>
                        <h3 style="font-size: 16px;color: #666666;font-weight: bold;font-style: italic">{{friend.sign}}</h3>
                        <p>{{friend.gender}}   {{friend.home}}</p>
                        <span>
                          <i slot="suffix" @click="delFriend(index)" class="iconfont icon-del icon-card"></i>
                          <i slot="suffix" @click="addTag(index)"  class="iconfont icon-tag icon-card"></i>
                          <i slot="suffix" @click="changeRemark(index)" class="iconfont icon-remark icon-card"></i>
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
        <div class="head-line">
          <h1>Current: {{talks[current].remark}}</h1>
          <el-dropdown v-show="current" style="color: white;float: right;margin-top: 5px">
            <span class="el-dropdown-link">
              MORE<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="downloadHistory()" >下载聊天记录</el-dropdown-item>
              <el-dropdown-item @click.native="getHistory()" >同步历史聊天记录</el-dropdown-item>
              <el-dropdown-item @click.native="closeTalk()" >注销当前会话</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-main>
          <div v-for="talk in talks" :key="talk.id">
            <el-collapse-transition>
              <div v-show="talk.active" >
                <!--Title-->
                <div v-for="msg in talk.message" :key="msg.id" id="ChatWindow">
                  {{msg.time}}
                  <el-row style="width: 100%;margin-bottom: 20px">
                    <el-col :sm="2"  class="avatar-holder-max" v-bind:style="{float: msg.location}">
                      <div class="avatar-holder">
                        <img class="avatar" :src="msg.avatar"/>
                      </div>
                    </el-col>
                    <el-col :sm="8"  v-bind:style="{textAlign: msg.location,float:msg.location}">
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
                    @keyup.enter.native="send()"
                    type="textarea">
          </el-input>
          <el-button @click="send()" class="btn-send">Send</el-button>
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
              <el-col :md="8" v-for="(result,index) in results" :key="index">
                <el-card :body-style="{ padding: '0px' }" style="margin: 30px">
                  <img :src="result.avatar" class="image">
                  <div style="padding: 14px;">
                    <div class="bottom clearfix">
                      <span>{{result.nickname}}</span>
                      <p class="id-line">ID: {{ result.id }}</p>
                      <el-button @click="addFriend(result,0)" type="info" class="item-right">加好友</el-button>
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
              <el-col :md="8" v-for="(item,index) in tags" :key="index">
                <el-card :body-style="{ padding: '0px' }" style="margin: 30px; width: 480px">
                  <div slot="header" class="clearfix">
                    <span style="float: left;font-weight: bold;font-size: 20px">{{item.remark}} 对我的印象</span>
                  </div>
                  <div v-for="(tag,index) in item.tag" :key="index" class="bottom">
                    <span style="font-size: 25px;font-weight:bold;font-style: italic;color:#EE7E94">
                      <i slot="prefix" class="iconfont icon-tag"></i>{{tag}}
                    </span>
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
              <el-col :md="8" v-for="(item,index) in req.content" :key="index">
                <el-card :body-style="{ padding: '0px' }" style="margin: 30px">
                  <div slot="header" class="clearfix">
                    <span style="float: left;font-weight: bold;font-size: 20px;margin-left: 13px">好友请求</span>
                  </div>
                  <img :src="item.avatar" class="image">
                  <div style="padding: 14px;">
                    <div class="bottom clearfix">
                      <span>{{item.nickname}}</span>
                      <p class="id-line">ID: {{item.id}}</p>
                    </div>
                    <el-button @click="addFriend(item,1)" type="success" class="item-right">接受</el-button>
                    <el-button @click="addFriend(item,-1)" type="danger" class="item-right" style="margin-right: 10px">滚</el-button>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-main>
        </div>
      </el-collapse-transition>


    </el-container><!--aside -->
  </el-container><!--header -->
</template>

<script>
    import ElCollapseTransition from "element-ui/src/transitions/collapse-transition";
    var thisStomp = null

    export default {
        name: "ChatTest",
      components: {ElCollapseTransition},
      data() {
        return {
          user: this.$store.state.nickname,
          myAvatar:this.$store.state.avatar,
          current: 0,
          isCollapse: false,
          showChat: true,
          showSearch: false,
          showTags: false,
          showReq: false,
          timer: true,
          screenWidth: document.body.clientWidth,
          asideWidth: '220px',
          searchID: '',

          content: null,
          show: true,
          talks:[
            {
              id: 'WayTalk',
              avatar: require('../assets/avatar/way.jpg'),
              remark: 'WayTalk',
              hasNew: false,
              new: 0,
              active: true,
              message: []
            },
          ],
          friends:[],
          results: [],
          req: {
            hasReq: false,
            content: []
          },
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
          test:[]
        };
      },
      mounted () {
        const that = this
        window.onresize = () => {
          return (() => {
            window.screenWidth = document.body.clientWidth
            that.screenWidth = window.screenWidth
            if (that.screenWidth < 1200) {
              this.asideWidth = '80px'
              this.isCollapse = true
            } else {
              this.asideWidth = '220px'
              this.isCollapse = false
            }
          })()
        }
      },
      watch: {
        screenWidth (val) {
          if (!this.timer) {
            this.screenWidth = val
            this.timer = true
            let that = this
            setTimeout(function () {
              // that.screenWidth = that.$store.state.canvasWidth
              console.log(that.screenWidth)
              that.init()
              that.timer = false
            }, 400)
          }
        }
      },
      created() {
        var that =this

        //Socket
        var socket = new SockJS('http://127.0.0.1:8080/server')
        thisStomp = Stomp.over(socket)
        thisStomp.connect({
          'name': this.$store.state.name
        },function (frame) {
          console.log('Connected:'+frame)

          //群聊
          thisStomp.subscribe('/topic/chat',function (response) {
            var msg = JSON.parse(response.body)
            console.log(msg)
            if (msg.id != that.$store.state.name) {
              that.talks[0].message.push(msg)
              that.talks[0].new ++
              that.talks[0].hasNew = true
            }
          });

          //私聊
          thisStomp.subscribe('/user/queue/messageController',function (response) {
            var msg = JSON.parse(response.body)
            console.log(msg)
            var f = 1;
            for (var j = 0;j<that.talks.length;j++) {
              var talk = that.talks[j]
              if (msg.id == talk.id) {
                console.log(j)
                that.talks[j].message.push(msg)
                that.talks[j].new ++;
                that.talks[j].hasNew = true
                f = 0
                break
              }
            }
            if (f == 1) {
              that.talks.push({
                id: msg.id,
                avatar: msg.avatar,
                remark: msg.remark,
                hasNew: true,
                new: 1,
                active: false,
                message: [msg]
              })
            }

          });

          //好友管理
          thisStomp.subscribe('/user/queue/friendController',function (response) {
            var msg = JSON.parse(response.body)
            if(0 == msg.type) {
              console.log(msg)
              that.req.content.push(msg)
              that.req.hasReq=true
            } else if (1 == msg.type) {
              that.getFriendList()
              that.$notify({
                title: msg.id,
                message: 'Hey,we are friends now!',
              })
            } else if (2 == msg.type) {
              that.getFriendList()
              that.$notify({
                title: msg.id,
                message: 'Hey,we are friends now!',
              })
              for(var i = 0; i<that.req.content.length; i++) {
                if(msg.id == that.req.content[i].id) {
                  that.req.content.splice(i,1)
                  if (that.req.content.length == 0)
                    that.req.hasReq = false
                  break;
                }
              }
            } else {
              for(var i = 0; i<that.req.content.length; i++) {
                if(msg.id == that.req.content[i].id) {
                  that.req.content.splice(i,1)
                  if (that.req.content.length == 0)
                    that.req.hasReq = false
                  break;
                }
              }
            }
          })

          //好友删除处理
          thisStomp.subscribe('/user/queue/deleteFriend',function (response) {
            var msg = JSON.parse(response.body)
            console.log(msg[0])
            if(msg[0] == 'Whoops') {
              that.$message({
                type: 'warning',
                message: 'Whoops,someone just deleted you,Hahahahah'
              })
            } else {
              that.$message({
                type: 'warning',
                message: 'Yes,you drop that guy eventually'
              })
            }
            for (var i = 0; i<that.talks.length;i++){
              if (that.talks[i].id == msg[1]) {
                that.talks.splice(i,1)
                that.current = 0
                that.activeTalk(that.current)
                break
              }
            }
            that.getFriendList()
          })
        })




        this.getFriendList()
        this.getMsgList()
        this.getReqList()

      },


      methods: {

        getFriendList: function() {
          var that = this
          //GET friends list
          this.axios.post('/friend/list',{
            friendAId: this.$store.state.name
          })
            .then(function (response) {
              var data = response.data;
              that.friends = data;
            })
            .catch(function (error) {
              console.log(error)
            })
        },

        getMsgList: function() {
          var that = this
          //离线消息接收
          this.axios.post('/message/new',{
            messageReceiverId: this.$store.state.name
          })
            .then(function (response) {
              var data = response.data;
              for (var i = 0;i<data.length;i++) {
                var item = data[i]
                var f = 1;
                for (var j = 0;j<that.talks.length;j++) {
                  var talk = that.talks[j]
                  if (item.id == talk.id) {
                    that.talks[j].message.push(item)
                    that.talks[j].new ++;
                    f = 0
                    break
                  }
                }
                if (f == 1) {
                  that.talks.push({
                    id: item.id,
                    avatar: item.avatar,
                    remark: item.remark,
                    hasNew: true,
                    new: 1,
                    active: false,
                    message: [item]
                  })
                }
              }
            })
            .catch(function (error) {
              console.log(error)
            })
        },

        getReqList: function() {
          var that = this
          //GET friends request
          this.axios.post('/friend/req',{
            friendBId: this.$store.state.name
          })
            .then(function (response) {
              console.log('req'+ response.data)
              var data = response.data;
              if (0 != data.length) {
                that.req.content = data
                that.req.hasReq = true
              }

            })
            .catch(function (error) {
              console.log(error)
            })

        },

          //send message
        send:function () {
          if ( '' === this.content || '\n' === this.content || this.content == ' ') {
            this.$message.error('Do Not Send Empty Message')
            this.content = ''

          } else {
            var time = this.Format('yyyy-MM-dd hh:mm:ss')
            console.log(time)
            var msg = {
              'id': this.$store.state.name,
              'content': this.content,
              'time': time,
              'location':'right',
              'avatar': this.$store.state.avatar,
              'remark': this.$store.state.nickname,
            }
            var sendMsg = {
              'messageSenderId': this.$store.state.name,
              'messageReceiverId': this.talks[this.current].id,
              'messageContent': this.content,
              'messageTime': time,
              'messageStat':1,
              'senderAvatar':this.$store.state.avatar
            }
            this.talks[this.current].message.push(msg)
            if (0 != this.current) {
              thisStomp.send('/app/message',{}, JSON.stringify(sendMsg))
            } else {
              thisStomp.send('/app/WayTalk',{},JSON.stringify(sendMsg))
            }
            this.content = ''
          }

        },


        //check my tag
        toTag: function() {
          this.setFalse()
          this.showTags = true
          var that = this
          this.axios.post('/tag/get',{
            tagReceiverId: this.$store.state.name
          })
            .then(function (response) {
              that.tags = response.data
              console.log(response.data)
            })
            .catch(function (error) {
              console.log(error)
            })
        },

        //page jump
        setFalse: function() {
          this.showChat = false
          this.showSearch = false
          this.showTags = false
          this.showReq = false
        },

        toReq:function () {
          this.setFalse()
          this.showReq = true
        },

        back: function() {
          this.setFalse()
          this.showChat = true
        },

        //Search friends
        search: function() {
          this.setFalse()
          this.showSearch = true
          var that = this
          this.axios.post('/user/list',{
            userId: this.searchID,
          })
            .then(function (response) {
              var data = response.data;
              that.results = data;
            })
            .catch(function (error) {
              console.log(error)
            })

        },

        collapse:function() {
          this.isCollapse = !this.isCollapse
          if(this.isCollapse)
            this.asideWidth = '70px'
          else
            this.asideWidth = '210px'
        },

        //Start talk with someone
        startChat:function (index) {
          var id = this.friends[index].id
          for (var j = 0;j<this.talks.length;j++) {
            var talk = this.talks[j]
            if (id == talk.id) {
              this.activeTalk(j)
              return
            }
          }
          this.talks.push({
            id: id,
            avatar: this.friends[index].avatar,
            remark: this.friends[index].remark,
            hasNew: false,
            new: 0,
            active: false,
            message: []
          })
          this.activeTalk(this.talks.length-1)
        },

        //Active talk
        activeTalk: function(index) {
          this.back()
          this.current = index
          for(var i = 0;i<this.talks.length;i++) {
            this.$set(this.talks[i],'active',false)
          }
          this.$set(this.talks[index],'active',true)
          this.$set(this.talks[index],'new',0)
          this.$set(this.talks[index],'hasNew',false)
        },


        //format date string
        Format:function (fmt) {
          var date = new Date()
          var o = {
            "M+": date.getMonth() + 1, //月份
            "d+": date.getDate(), //日
            "h+": date.getHours(), //小时
            "m+": date.getMinutes(), //分
            "s+": date.getSeconds(), //秒
            "q+": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
          };
          if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
          for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
          return fmt;
        },


        //quit
        quit:function () {
          if (thisStomp != null) {
            thisStomp.disconnect()
            this.$store.state.name = null
            this.$store.state.avatar = null
            this.$store.state.nickname = null
          }
          this.$router.push({name:'Login'})
        },


        //Close Talk
        closeTalk:function () {
          console.log(this.current)
          console.log(this.talks.length)
          this.talks.splice(this.current,1)
          console.log(this.talks.length)
          this.current = 0
          this.activeTalk(this.current)
        },

        //DownLoad history
        downloadHistory:function () {
          var target = this.current
          var that = this
          this.axios.post('/message/download',{
            messageSenderId: this.$store.state.name,
            messageReceiverId: this.talks[target].id
          })
            .then(function (response) {
              that.$notify({
                title: 'Download Succeed',
                message: 'path: '+response.data,
              })
            })
            .catch(function (error) {
              console.log(error)
              that.$notify({
                title: 'Download failed',
                message: 'Sorry',
              })
            })
        },

        //Get History
        getHistory:function () {
          var target = this.current
          var that = this
          this.axios.post('/message/history',{
            messageSenderId: this.$store.state.name,
            messageReceiverId: this.talks[target].id
          })
            .then(function (response) {
              that.talks[target].message = response.data
            })
            .catch(function (error) {
              console.log(error)
            })
        },

        //Delete Friend
        delFriend: function (index) {
          var that = this
          this.$confirm('Do you really want to delete this guy?', 'Oh God', {
            confirmButtonText: 'confirm',
            cancelButtonText: 'cancel',
            type: 'warning'
          }).then(() => {
            thisStomp.send('/app/delete',{},JSON.stringify({
              friendAId: that.$store.state.name,
              friendBId: that.friends[index].id
            }))
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },

        addTag: function (index) {
          var that = this;
          this.$prompt('What do you think about your friend', 'Tips', {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel'
          }).then(({value})=>{
            this.axios.post('/tag/insertTag', {
              tagSenderId: this.$store.state.name

              ,
              tagReceiverId: this.friends[index].id

              ,
              tagContent: value
            })
              .then(function (response) {
                console.log(response)
                var msg = response.data
                if(msg=='Insert Successfully'){
                  that.$message({
                    type: 'success',
                    message: '添加标签成功',
                  });
                }
                else{
                  that.$message({
                    type: 'error',
                    message: '添加标签失败',
                  });
                }
              })
          })
        },

        //Change Remark
        changeRemark: function (index) {
          var that = this
          var friend = this.friends[index].id
          this.$prompt('Remark your friend', 'Remark', {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
          }).then(({ value }) => {
            that.axios.post("/friend/remark",{
              friendAId: that.$store.state.name,
              friendBId: friend,
              friendRemark: value
            })
              .then(function (response) {
                console.log(response.data)
                var remark = response.data
                if (remark == 'Fail') {
                  that.$message({
                    type: 'error',
                    message: 'Setting remark failed'
                  })
                } else {
                  that.$message({
                    type: 'success',
                    message: 'Setting remark succeed'
                  })
                  that.$set(that.friends[index],'remark',remark)
                }

              })
              .catch(function (error) {
                console.log(error)
              })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消输入'
            });
          });

        },


        //handle friend req
        //type = 0 add
        //type = -1 refuse
        //type = 1 accept
        addFriend:function(result,type){
          console.log(result)
          var friendMsg={
            'friendAId':this.$store.state.name
            ,
            'friendBId':result.id
            ,
            'friendRemark':result.nickname,
            'friendStat':type
          }
          thisStomp.send('/app/friend',{},JSON.stringify(friendMsg))
          if(type == 0){
            this.$message({
              type:"success",
              message:'Request delivered!'
            })
          }
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
  .head-line {
    text-align: left;
    height: 30px;
    padding: 2px 5px;
    background: #3a3a3a
  }

  .head-line h1 {
    display: inline;
    font-style: italic;

    font-size: 26px;
    color:white;
  }

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
    max-width: 100px
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
