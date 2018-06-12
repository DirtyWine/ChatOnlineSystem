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
          <el-form-item  class="input-holder" prop="user_birthday">
            <el-date-picker
              v-model="register.user_birthday"
              align="right"
              class="item-left"
              prefix-icon="none"
              placeholder="*Birthday"
              type="date">
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
          <!--Submit button-->
          <el-form-item>
            <el-button @click="submitRegister('register')" type="info" class="btn-block">Sign up</el-button>
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
        register: {
          user_id: '',
          user_nickname: '',
          user_password: '',
          password_confirm: '',
          user_gender: 'male',
          protocol_agreed: true,
          user_birthday:'',
          user_hometown: ''
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
          user_birthday: [
            { required: true, message: 'Please input your birthday', trigger: 'blur' }
          ],
          user_hometown: [
            { required: true, message: 'Please input your hometown', trigger: 'blur' }
          ]
        }//rule_register
      }
    },
    methods: {
      submitRegister: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('submit succeed!')
          } else {
            console.log('error submit!!')
            return false
          }
        })
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
