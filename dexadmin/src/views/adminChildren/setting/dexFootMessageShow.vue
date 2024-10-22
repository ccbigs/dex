<template>
  <div class="demo">
    <div v-if="info === '0'">
      <el-empty description="改标题暂无文章信息，赶快来编写吧">
        <el-button type="primary" @click="EditInfo">编写文章</el-button>
      </el-empty>
    </div>
    <div v-if="info === 'edit' || info === 'hasinfo'" class="demo-switch">
      <el-switch
          v-model="infoState"
          active-text="查看"
          inactive-text="编辑">
      </el-switch>
    </div>
    <div v-if="info === 'edit'">
      <el-card class="box-card components-container">
        <div slot="header" class="clearfix">
          <span>发布信息</span>
          <el-button  style="float: right;margin-bottom: 3px" type="primary" @click="publicInfo">发布</el-button>
        </div>
        <div class="text item">
          <div>
            <el-form :ref="information" label-width="80px">
              <el-form-item label="文章标题" >
                <el-input v-model="information.title"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <aside>
            <a target="_blank" class="link-type" href="https://panjiachen.github.io/vue-element-admin-site/feature/component/rich-editor.html"></a>
          </aside>
          <div>
            <tinymce v-model="information.content" :height="530" />
          </div>
        </div>
      </el-card>
    </div>
    <div v-if="info === 'hasinfo'">
      <div class="demo-header">
        <div>{{information.title}}</div>
      </div>
      <div v-html="information.content" class="demo-html"></div>
    </div>
  </div>
</template>

<script>
import Tinymce from "../../../components/Tinymce"
export default {
  name: "dexFootMessageShow",
  components:{
    "Tinymce":Tinymce,
  },
  data(){
    return{
      info:'0',
      tab:'',
      infoState:true,
      information:{
        title:'',
        content:'请输入内容',
        auther:'',
        title_type:'',
      },

    }
  },
  created() {
    this.tab = this.$route.query
    this.info = this.tab.infoId+""
    if (this.info === '0'){
    }else {
      this.getTabInfoDate()
    }
  },
  watch:{
    infoState(newName, oldName){
      if (newName){
        this.info = 'hasinfo'
      }else {
        this.info = 'edit'
      }
    }
  },
  methods:{
    /*获取改标题的文章数据*/
    async getTabInfoDate(){
      const res = await this.axios.post('tab/getTabInfo',this.$qs.stringify({
        info_id:this.tab.infoId
      }))

      if (res.data.code === 200){
        this.information = res.data.object;
        this.info = 'hasinfo';
        this.infoState=true;
      }else {
        this.$message.error("获取文章信息失败")
      }
    },


    /*-------------编写文章--------------*/
    async EditInfo(){
      this.info = 'edit'
    },
    /*---------发表文章----------*/
    async publicInfo(){
      this.information.auther=this.$store.state.adminInfo.account;
      /*将tab的id值填充到approve_number里面*/
      this.information.approve_number = this.tab.id;
      this.information.title_type = "全部";

      if (this.tab.infoId === 0){
        const res=await this.axios.post('tab/adminPublicInformationByTab',this.$qs.stringify(
            this.information
        ))
        if (res.data.code === 200){
          this.$message.success("标题文章发布成功")
          this.tab.infoId = res.data.size
          await this.getTabInfoDate();
        }else {
          this.$message.error("标题文章发布失败")
        }
      }else {
        const res=await this.axios.post('tab/adminUpdateInformationByTab',this.$qs.stringify(
            this.information
        ))
        if (res.data.code === 200){
          this.$message.success("标题文章更新成功")
          await this.getTabInfoDate();
        }else {
          this.$message.error("标题文章更新失败")
        }
      }


    },
  }
}
</script>

<style scoped>
  .demo{
    width: 1500px;
    background-color: white;
    margin: 0 auto;
    margin-top: 30px;
    border-radius: 15px;
    padding: 10px;
  }

  .demo-switch{
    margin:10px 10px 10px 20px;
  }

  .demo-header{
    margin-top: 20px;
    font-size: 30px;
    font-weight: bold;
    display: flex;
    justify-content: center;
  }

  .demo-html{
    padding: 20px;
  }

</style>