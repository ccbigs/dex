<template>
  <div>
    <div class="mode">
      <div>
        <el-card shadow="hover">
          <el-input
              style="width: 300px"
              placeholder="请输入要搜索的标题内容"
              prefix-icon="el-icon-search"
              v-model="input1">
          </el-input>
          <el-button type="primary" style="margin-left: 14px" @click="research()">搜索</el-button>
        </el-card>
      </div>
      <div class="mode-title" v-for="(o,i) in titleDate" :key="o.id">
        <el-card :body-style="{ padding: '10px' }" shadow="hover">
          <div style="display: flex;justify-content: space-between;align-items: center">
            <h2>{{ o.title }}</h2>
          <!--   输入管理内容-->
            <div>
              <el-button  type="primary" @click="updateTitle(o)">修改</el-button>
              <el-button type="danger" @click="deleteTitle(o.id)">删除</el-button>
            </div>
          </div>
          <div class="span">
            <span>
              &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
              {{ o.subtitle }}
            </span>
          </div>
          <div class="button">
            <div>时间:{{ o.public_time }}</div>
            <div>
              <el-button type="text" class="button" @click="GoInformationDetail(o.id)">>>查看全文</el-button>
            </div>
          </div>

        </el-card>
      </div>
    </div>
    <admin-foot style="position: fixed;bottom: 0px;"></admin-foot>
  </div>
</template>

<script>
import adminFoot from "../../../components/adminFoot";
export default {
  name: "InformationManager",
  components: {adminFoot},
  data(){
    return{
      /*翻页*/
      input1:null,
      titleDate:[],
    }
  },
  created() {
    this.getInformation()
  },
  methods:{
    /*刚打开页面，获取全部文章信息*/
    async getInformation(){
      const res= await this.axios.post('DexAdmin/adminGetInformation')

      if (res.data.code === 200){
        this.titleDate= res.data.object
      }else {
        this.$message.error("当前暂无文章信息")
      }
    },
    /*查看全文跳转*/
    GoInformationDetail(info_id){
      this.$router.push({
        name:'InformationShow',
        query:{
          info_id:info_id
        },
      })
    },
    /*删除选中的文章*/
    async deleteTitle(info_id){
      const res=await this.axios.post('DexAdmin/deleteInformationById',this.$qs.stringify({
        info_id:info_id
      }))
      if (res.data.code === 200){
        await this.getInformation()
      }else {
        this.$message.error("文章删除失败")
      }
    },
    /*修改删除的文章内容*/
    updateTitle(info){
      this.$router.push({
        name: 'updateInformation',
        params: info
      })
    },
    /*搜索文章内容*/
    async research(){
      if (this.input1 === null){
        await this.getInformation()
      }else {
        const res= await this.axios.post('DexAdmin/researchInformationByTitle',this.$qs.stringify({
          title:this.input1
        }))

        if (res.data.code === 200){
          this.titleDate = res.data.object;
          this.input1 = null;
        }else {
          this.$message.error("未搜索到相关文章")
        }
      }
    },
  }
  }
</script>

<style scoped>
  h2{
    margin: 0px;
  }

  .span{
    margin-top: 8px;
    margin-bottom: 8px;
    font-size: 18px;
  }

  .mode{
    margin: 10px auto;
    padding: 15px 15px 40px 15px;
    width: 1606.670px;
    background-color: white;
  }

  .mode-title{
    margin-top: 18px;
  }

  .button{
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .info-page{
    float: right;
    margin-top: 24px;
  }


</style>