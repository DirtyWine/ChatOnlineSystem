<template>
  <el-container>
    <el-main>
      <div class="form-view">
        <el-form  ref="register" :model="register" :rules="rule_register">
          <!--Title-->
          <el-form-item>
            <img @click="goChat()" class="register-img item-left" src="../assets/register.png"/>
          </el-form-item>
          <!--Email-->
          <el-form-item class="input-holder" prop="user_id">
            <el-input
              v-model="register.user_id"
              placeholder="*ID"
              clearable>
            </el-input>
          </el-form-item>
          <!--Nick name-->
          <el-form-item  class="input-holder" prop="user_nickname">
            <el-input
              v-model="register.user_nickname"
              placeholder="*Nickname"
              clearable>
            </el-input>
          </el-form-item>
          <!--Avatar-->
          <el-form-item  class="input-holder">
            <img style="height: 200px;width: 200px;" :src="register.user_avatar"/>
            <el-select v-model="register.user_avatar" placeholder="*Choose your avatar">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!--Password-->
          <el-form-item  class="input-holder" prop="user_password">
            <el-input
              v-model="register.user_password"
              type="password"
              placeholder="*Password"
              clearable>
            </el-input>
          </el-form-item>
          <!--Confirm Password-->
          <el-form-item  class="input-holder" prop="password_confirm">
            <el-input
              v-model="register.password_confirm"
              type="password"
              placeholder="*Confirm password"
              clearable>
            </el-input>
          </el-form-item>
          <!--Gender-->
          <el-form-item  class="input-holder" >
            <el-radio-group fill="#000000"  class="item-left" v-model="register.user_gender">
              <el-radio-button label="male">Male</el-radio-button>
              <el-radio-button label="female">Female</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <!--Birthday-->
          <el-form-item  class="input-holder">
            <el-date-picker
              v-model="register.user_birthday"
              align="right"
              class="item-left"
              prefix-icon="none"
              placeholder="*Birthday"
              type="date"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <!--Hometown-->
          <el-form-item  class="input-holder" prop="user_hometown">
            <el-input
              v-model="register.user_hometown"
              placeholder="*Hometown"
              clearable>
            </el-input>
          </el-form-item>
          <!--Sign-->
          <el-form-item  class="input-holder" prop="user_sign">
            <el-input
              v-model="register.user_sign"
              placeholder="*Introduce yourself"
              clearable>
            </el-input>
          </el-form-item>
          <!--Submit button-->
          <el-form-item>
            <el-button @click="submitregister()" type="info" class="btn-block">Sign up</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Register",
    data () {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Please input password'));
        } else {
          if (this.register.password_confirm !== '') {
            this.$refs['register'].validateField('password-confirm');
          }
          callback();
        }
      }

      var validateCheckPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Please input password again'));
        } else if (value !== this.register.user_password) {
          callback(new Error('Password is not consistent'));
        } else {
          callback();
        }
      }

      return {
        options: [{
          value: require('../assets/avatar/m1.jpg'),
          label: 'Loki'
        }, {
          value: require('../assets/avatar/f1.jpg'),
          label: 'Katarina'
        }, {
          value: require('../assets/avatar/m2.jpg'),
          label: 'Musashi'
        }, {
          value: require('../assets/avatar/f2.jpg'),
          label: 'Qi'
        }, {
          value: require('../assets/avatar/m3.jpg'),
          label: 'Rogue'
        }],

        register: {
          user_id: '',
          user_nickname: '',
          user_password: '',
          password_confirm: '',
          user_avatar: require('../assets/avatar/m1.jpg'),
          user_gender: 'male',
          user_birthday:'1995-01-01',
          user_hometown: '',
          user_sign:''
        },
        rule_register: {
          user_id: [
            { required: true, message: 'Please input your id', trigger: 'blur' }
          ],
          user_nickname: [
            { required: true, message: 'Please input nickname', trigger: 'blur' }
          ],
          user_password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          password_confirm: [
            { validator: validateCheckPass, trigger: 'blur' }
          ],
          user_hometown: [
            { required: true, message: 'Please input your hometown', trigger: 'blur' }
          ],
          user_sign: [
            { required: true, message: 'Please introduce yourself', trigger: 'blur' }
          ]
        }//rule_register
      }
    },
    methods: {
      submitregister:function() {
        if(this.register.user_id==''||this.register.user_nickname==''||this.register.user_password==''||
          this.register.user_gender==''||this.register.user_hometown==''|| this.register.user_sign==''){
          this.$alert('信息还没填全呢', '天呐', {
            confirmButtonText: '返回',})
        }
        else if(this.register.user_password!=this.register.password_confirm){
          this.$alert('两次密码不一样啊兄dei', '你在干什么', {
            confirmButtonText: '返回',})
        }
        else{
          var thisMsg = this
          this.axios.post('/user/register',{
            userId: this.register.user_id,
            userNickname: this.register.user_nickname,
            userPassword: this.register.user_password,
            userAvatar: this.register.user_avatar,
            userGender: this.register.user_gender,
            userBirthday: this.register.user_birthday,
            userHome: this.register.user_hometown,
            userSign: this.register.user_sign
          })
            .then(function (response) {
              console.log(response)
              var msg  = response.data
              if(msg=='Successfully registered'){
                thisMsg.$notify({
                  title: '提示',
                  message: '注册成功',
                });
                thisMsg.$router.push({name:'Login'})
              }
              else if(msg=='This ID has been registered'){
                thisMsg.$notify({
                  title: '提示',
                  message: '该ID已被注册，请重新输入',
                });
                thisMsg.$router.push({name:'Register'})
              }
              else{
                thisMsg.$notify({
                  title: '提示',
                  message: '出错了，请重试',
                });
                thisMsg.$router.push({name:'Register'})
              }

            })
            .catch(function (error) {
              console.log(error)

            })
        }

      },

      goChat: function () {
        this.$router.push({name:'test'})
      }
    }
  }

</script>

<style>
  /* --- form-view --- */
  .register-img {
    width: 300px;
    height: 80px;
  }

  .form-view {
    background: white;
    margin-left: auto;
    margin-right: auto;
    padding: 20px;
    max-width: 450px;
    box-shadow: 0 0 5px white;
    transition: 0.3s;
  }

  .form-view:hover {
    box-shadow: 0 0 1px #ecf0f1;
  }

  .form-view p {
    color: #3f6bcb;
    font-size: 29px;
    text-align: left;
    font-weight: bold;
  }

  .form-view a {
    text-decoration: none;
    color: #409eff;
    font-weight: bold;
    font-size: 15px;
  }

  .form-view a:hover {
    color: #234985;
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

  .date-selector {
    padding-left: 20px;
    transition: 1s;
  }

  .date-selector:hover {
    padding-left: 20px;
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


  .input-holder {
    padding: 10px 0px;
  }


  /* --- Layout design --- */
  .item-left{
    float: left;
  }
</style>
