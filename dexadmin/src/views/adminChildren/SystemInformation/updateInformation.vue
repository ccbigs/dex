<template>
  <div class="">
    <el-card class="box-card components-container">
      <div slot="header" class="clearfix">
        <span>更新新闻</span>
        <el-button  style="float: right;margin-bottom: 3px" type="primary" @click="publicInfo">更新</el-button>
      </div>
      <div class="text item">
        <div>
          <el-form :ref="information" label-width="80px">
            <el-form-item label="文章标题" >
              <el-input v-model="information.title"></el-input>
            </el-form-item>
            <el-form-item label="副标题" >
              <el-input type="textarea" v-model="information.subtitle"></el-input>
            </el-form-item>
            <el-form-item label="发布人群" prop="resource">
              <el-radio-group  v-model="information.title_type">
                <el-radio label="全部"></el-radio>
                <el-radio label="网红"></el-radio>
                <el-radio label="商家"></el-radio>
              </el-radio-group>
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

<!--    <div class="editor-content" v-html="content" />-->
    <!--  最底部  -->
    <admin-foot style="margin-top: 20px;"></admin-foot>
  </div>
</template>

<script>
import Tinymce from "../../../components/Tinymce"
import adminFoot from "../../../components/adminFoot";
export default {
  name: "publicInformation",
  components: {
    "Tinymce" :Tinymce,
    "adminFoot":adminFoot,
  },
  data() {
    return {
      content:'请输入内容',
      information:{
        title:'',
        subtitle:'',
        content:'',
        auther:'',
        title_type:'',
      },
    }
  },
  created() {
    this.information = this.$route.params
  },
  methods:{
    async publicInfo(){
      this.information.auther=this.$store.state.adminInfo.account;
      const res=await this.axios.post('DexAdmin/adminUpdateInformation',this.$qs.stringify(
          this.information
      ))
      if (res.data.code === 200){
        this.$message.success("系统消息更新成功")
        await this.$router.push({
          name:'InformationManager'
        })
      }else {
        this.$message.error("系统消息更新失败")
      }
    }
  }
}
</script>

<style scoped>
  .components-container{
    margin: 10px auto;
    width: 1636.670px;
  }
</style>