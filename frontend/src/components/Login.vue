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
              <el-form-item class="input-holder" prop="user_id">
                <el-input
                  v-model="user.user_id"
                  @keyup.enter.native="signIn('user')"
                  type="text"
                  placeholder="User ID"
                  clearable>
                </el-input>
              </el-form-item>
              <!--Input password-->
              <el-form-item class="input-holder" prop="user_password">
                <el-input
                  v-model="user.user_password"
                  @keyup.enter.native="signIn('user')"
                  type="password"
                  placeholder="Password"
                  clearable>
                </el-input>
              </el-form-item>
              <!--Forget password-->
              <el-form-item>
                <a href="#" class="item-right">Forget passwords?</a>
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
            user_id: '',
            user_password: ''
          },
          contentRequired: {
            user_id: [
              { required: true, message: 'Username required', trigger: 'blur'}
            ],
            user_password: [
              { required: true, message: 'Password required', trigger: 'blur'}
            ]
          }
        }
      },
    methods: {
      signIn: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('submit succeed!');
          } else {
            console.log('error submit!!');
            return false;
          }
        })
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
