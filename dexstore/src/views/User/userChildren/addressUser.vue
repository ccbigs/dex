<template>
  <div>
    <!--页头信息-->
    <div class="head-title">我的地址</div>
    <el-divider></el-divider>
    <div class="address-table">
      <el-table
          :data="addressData"
          style="width: 100%">
        <el-table-column
            prop="name"
            label="收货人"
            width="150">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="联系电话"
            width="150">
        </el-table-column>
        <el-table-column label="地址">
          <el-table-column
              prop="local"
              label="省份-市区"
              width="240">
          </el-table-column>
          <el-table-column
              prop="detail"
              label="地址"
              width="300">
          </el-table-column>
          <el-table-column
              prop="postal"
              label="邮编"
              width="120">
          </el-table-column>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row.id)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click.prevent="deleteRow(scope.$index, addressData,scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="修改地址" :visible.sync="dialogBindEditAddress">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="收货人姓名" prop="name">
          <el-input v-model="ruleForm.name" placeholder="填写收货人姓名" style="width: 300px;"></el-input>
        </el-form-item>
        <!--手机号码-->
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="ruleForm.phone" placeholder="填写收货人联系电话" style="width: 300px;"></el-input>
        </el-form-item>
        <!--邮政编码-->
        <el-form-item label="邮政编码" prop="postal">
          <el-input v-model="ruleForm.postal" placeholder="填写收货人所在地的邮政编码" style="width: 200px;"></el-input>
        </el-form-item>
        <!--地址信息-->
        <el-form-item label="地址信息" prop="local">
          <el-input v-model="ruleForm.local" style="display: none"></el-input>
          <area-select @Area="AreaFunc"></area-select>
        </el-form-item>
        <!--详细地址-->
        <el-form-item label="详细地址" prop="detail">
          <el-input type="textarea" v-model="ruleForm.detail" placeholder="请输入详细地址信息，如道路、门牌号、小区、楼栋号、单元等信息"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import areaSelect from '../../../components/dex-shop/shopAreaSelect'
export default {
  name: "addressUser",
  components:{
    'areaSelect':areaSelect
  },
  data() {
    return {

      addressData: [],
      dialogBindEditAddress : false,
      ruleForm:{
        name:'',
        phone:'',
        postal:'',
        detail: '',
        local:'',
        user_id:'',
      },
      rules: {
        name:[
          { required: true, message: '请输入收货人姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        phone:[
          { required: true, message: '请输入收货人联系电话', trigger: 'blur' },
          { min: 11, max: 11, message: '输入的电话不正确', trigger: 'blur'}
        ],
        postal:[
          { required: true, message: '请输入收货人所在地的邮政编码', trigger: 'blur' },
          { min: 6, max: 6, message: '输入的邮政编码不正确', trigger: 'blur'}
        ],
        Area: [
          { required: true, message: '请输入地址信息', trigger: 'blur' }
        ],
        detail: [
          { required: true, message: '请填写详细地址', trigger: 'blur' }
        ]
      }
    }
  },
  async created() {
    /*getUserAddress*/
    const res=await this.axios.post('http://localhost:8681/dex/address/getUserAddress',this.$qs.stringify({
      user_id:this.$store.state.userInfo.id
    }))
    if (res.data.code === 200){
        this.addressData=res.data.object
      console.log("123",this.$store.state.userAddress)
        this.$store.commit('updateUserAddress',res.data.object)
      console.log("1234",this.$store.state.userAddress)
    }else {

    }
  },
  methods:{
    /*删除该行*/
    async deleteRow(index, rows , address_id) {
      rows.splice(index, 1);
      const res=await this.axios.post('http://localhost:8681/dex/address/delUserAddress',this.$qs.stringify({
        address_id:address_id,
      }))
      this.$store.commit('updateUserAddress',res.data.object)
      if (res.data.code === 200){
        this.successMsg()
      }else {
        this.filedMsg()
      }
    },

    /*修改指定的地址*/
    async handleEdit(index,address_id){
      this.ruleForm=this.addressData[index];
      this.dialogBindEditAddress=true;

    },

    AreaFunc(area){
      this.ruleForm.local=area
    },

    /*修改地址信息*/
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.user_id=this.$store.state.userInfo.id;
          this.axios({
            url:'http://localhost:8681/dex/address/modifyAddress',
            method:'post',
            data:this.$qs.stringify(this.ruleForm)
          }).then((res)=>{
            if (res.data.code === 200){
              this.successModify();
              location.reload()
              this.$router.push({
                name:'addressUser'
              })
            }else {
              this.filedMsg();
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    /*修改成功提示*/
    successModify() {
      this.$notify({
        title: '修改成功',
        message: '成功修改当前地址',
        type: 'success'
      });
    },
    /*成功提示*/
    successMsg() {
      this.$notify({
        title: '删除成功',
        message: '成功删除地址',
        type: 'success'
      });
    },
    /*失败提示*/
    filedMsg() {
      this.$notify.error({
        title: '删除失败',
        message: '地址删除失败',
      });
    },
  }
}
</script>

<style scoped>
  .head-title{
    margin-top: 14px;
    font-size: 28px;
    font-weight: 500;
  }
  .address-table{
    width: 1200px;
    height: 1020px;
    background-color: #FFFFFF;
  }
</style>