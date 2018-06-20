<template>
  <el-container>
    <el-main style="margin-top: 50px">
      <el-row :gutter="10" >
        <el-col  :lg="2" >
          <div class="grid-content"></div>
        </el-col>
        <el-col  :lg="10" >
          <div>
            <img class="welcome-img" src="../assets/welcome.png">
          </div>
        </el-col>
        <el-col :lg="1" class="col-line" />
        <el-col  :lg="9" >
          <div class="form-view">
            <el-form ref="user" :model="user" :rules="contentRequired">
              <!--Title-->
              <el-form-item>
                <img class="login-img item-left" src="../assets/login.png">
              </el-form-item>
              <!--Input username-->
              <el-form-item class="input-holder" prop="userId">
                <el-input
                  v-model="user.userId"
                  @keyup.enter.native="signIn('user')"
                  type="text"
                  placeholder="User ID"
                  clearable>
                </el-input>
              </el-form-item>
              <!--Input password-->
              <el-form-item class="input-holder" prop="userPassword">
                <el-input
                  v-model="user.userPassword"
                  @keyup.enter.native="signIn('user')"
                  type="password"
                  placeholder="Password"
                  clearable>
                </el-input>
              </el-form-item>
              <!--Forget password-->
              <el-form-item>
                <a href="#" @click="forget()" class="item-right">Forget passwords?</a>
              </el-form-item>
              <!--Submit button-->
              <el-form-item>
                <el-button @click="signIn('user')"  type="info" class="btn-block item-left">Login</el-button>
              </el-form-item>
            </el-form>
            <!--Register-->
            <a href="/#/register">Register now</a>

          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Login",
      data() {
        return {
          user: {
            userId: '',
            userPassword: ''
          },
          contentRequired: {
            userId: [
              { required: true, message: 'Username required', trigger: 'blur'}
            ],
            userPassword: [
              { required: true, message: 'Password required', trigger: 'blur'}
            ]
          }
        }
      },
    methods: {
      signIn: function (formName) {
        var that = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('submit succeed!');
            var id = this.user.userId
            this.axios.post('/user/login',{
              userId: this.user.userId,
              userPassword: this.user.userPassword
            })
              .then(function (response) {
                console.log(response)
                var msg = response.data
                if(msg == 'wrong password'){
                  that.$notify({
                    title: 'Whoops',
                    message: 'Wrong Password!',
                  })
                }
                else if(msg=='userID not exits'){
                  that.$notify({
                    title: 'Whoops',
                    message: 'This ID dose not exit',
                  })
                }
                else if (msg == 'error'){
                  that.$notify({
                    title: 'Whoops',
                    message: 'Something is wrong, try again',
                  })
                }
                else {
                  that.$message({
                    type: 'success',
                    message: 'Yeah,Login success',
                  })
                  that.$store.commit("newName",id)
                  that.axios.post('/user/info',{
                    userId: that.$store.state.name
                  })
                    .then(function (response) {
                      console.log(response.data)
                      var data = response.data;
                      that.$store.commit("newAvatar",data.userAvatar)
                      that.$store.commit("newNickname",data.userNickname)
                      that.$router.push({name:'test'})
                    })
                    .catch(function (error) {
                      console.log(error)
                    })
                }

              })
              .catch(function (error) {
                console.log(error)
              })

          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },

      forget:function() {
        this.$alert('你是猪吗', '密码都会忘', {
          confirmButtonText: '是',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      }
    }
  }
</script>

<style>

  /* --- welcome style --- */
  @media screen and (max-width:1199px) {
    .col-line { display: none }
  }

  .col-line{
    height: 25vw;
    border-right: 2px solid #767676;
  }

  .welcome-img {
    width: 40vw;
    height: 18vw;
  }


  h1,h2 {
    text-align: left;
    font-style: italic;
  }

  h1 {
    font-size: 53px;
  }

  h2 {
    font-size: 40px;
  }

  /* --- form-view --- */
  .login-img{
    width: 120px;
    height: 53px;
  }

  .form-view {
    background: white;
    margin-left: auto;
    margin-right: auto;
    padding: 50px;
    max-width: 450px;
    box-shadow: 0 0 5px white;
    transition: 0.3s;
  }

  .form-view:hover {
    box-shadow: 0 0 2px #ecf0f1;
  }

  .form-view p{
    color: #3f6bcb;
    font-size: 29px;
    text-align: left;
    font-weight: bold;
  }

  .form-view a {
    text-decoration: none;
    color: #2d2d2d;
    font-weight: bold;
    font-size: 15px;
  }

  .form-view a:hover {
    color: #6e6e6e;
  }

  .btn-block {
    width: 100%;
    height: 53px;
    background: #2d2d2d;
    font-size: 19px;
  }

  .btn-block:hover {
    background: #000000;
  }


  .el-input__inner {
    border:none;
    border-radius: 0;
    border-bottom: 2px solid #6e6e6e;
    font-size: 20px;
    padding-left: 0px;
    transition: 1s;
  }

  .el-input__inner:hover {
    border-bottom: 2px solid black;
    padding-left: 10px;
  }

  .el-input__inner:focus {
    border-bottom: 2px solid black;
    padding-left: 10px;
  }

  .input-holder {
    padding: 10px 0px;
  }


  /* --- Layout design --- */
  .grid-content {
    min-height: 1px;
  }

  .item-left{
    float: left;
  }

  .item-right{
    float: right;
  }


</style>
