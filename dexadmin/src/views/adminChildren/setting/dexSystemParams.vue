<template>
  <div>
    <div class="busman-manager">
      <div>
        <el-descriptions class="margin-top" title="DEX系统配置信息" :column="2"  border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="dialogUsuallyVisible=true">修改常用信息</el-button>
            <el-button type="primary" size="small" @click="dialogAllVisible=true">修改全部信息</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              系统名称
            </template>
            {{ system.dexName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              商城联系电话
            </template>
            {{ system.dexPhone}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              商城搜索词
            </template>
            {{ system.dexSearch }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              商城备案号
            </template>
            <el-tag size="small">{{ system.dexRecord }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城商标
            </template>
            <img :src="system.dexLogo" style="height: 100px">
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城Email
            </template>
            {{ system.dexEmail }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城联系人电话
            </template>
            {{ system.dexUserPhone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城创建时间
            </template>
            {{ system.dexCreateTime }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城启动时间
            </template>
            {{ system.dexBeginTime }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城配置信息状态
            </template>
            {{ system.dexState }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              商城配置备注信息
            </template>
            {{ system.dexRemarks }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <admin-foot style="position:fixed;bottom: 0px;"></admin-foot>

    <el-dialog title="常用配置信息修改" :visible.sync="dialogUsuallyVisible">
      <el-form :model="system">
        <el-form-item label="DEX系统名称">
          <el-input v-model="system.dexName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="DEX系统客户联系电话">
          <el-input v-model="system.dexPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="DEX系统搜索关键词">
          <el-input v-model="system.dexSearch" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUsuallyVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUsuallySystem()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="配置信息修改" :visible.sync="dialogAllVisible">
      <el-form :model="system">
        <el-form-item label="商城备案号">
          <el-input v-model="system.dexRecord" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商城商标">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8683/dex/DexAdmin/uploadUserHeaderImage"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="system.dexLogo" :src="system.dexLogo" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商城Email">
          <el-input v-model="system.dexEmail" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商城负责人联系电话">
          <el-input v-model="system.dexUserPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商城备注信息">
          <el-input v-model="system.dexRemarks" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAllVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAllSystem()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import adminFoot from "../../../components/adminFoot";
export default {
  name: "dexSystemParams",
  components:{
    'adminFoot':adminFoot,
  },
  data(){
    return{
      system:'',

      dialogUsuallyVisible:false,
      dialogAllVisible:false,

    }
  },
  created() {
    this.getEnableSystem()
  },
  methods:{
    async getEnableSystem(){
      const res = await this.axios.post('http://localhost:8680/dex/system/getEnableSystem')
      if (res.data.code === 200){
        this.system = res.data.object;
      }else {
        this.$message.warning("系统配置信息读取失误")
      }
    },

    /*修改经常使用的信息*/
    async updateUsuallySystem(){
      const res = await this.axios.post('http://localhost:8680/dex/system/updateUsuallySystem',this.$qs.stringify({
        id:this.system.id,
        dexName:this.system.dexName,
        dexPhone:this.system.dexPhone,
        dexSearch:this.system.dexSearch,
        dexRemarks:this.system.dexRemarks,
      }))

      if (res.data.code === 200){
        this.$message.success("常用配置信息已更新")
        this.dialogUsuallyVisible = false
      }else {
        this.$message.error("常用配置信息更新失败")
      }
    },

    /*更改全局的信息配置*/
    async updateAllSystem(){
      const res = await this.axios.post('http://localhost:8680/dex/system/updateAllSystem',this.$qs.stringify({
        id:this.system.id,
        dexRecord:this.system.dexRecord,
        dexLogo:this.system.dexLogo,
        dexEmail:this.system.dexEmail,
        dexUserPhone:this.system.dexUserPhone,
        dexRemarks:this.system.dexRemarks
      }))

      if (res.data.code === 200){
        this.$message.success("修改系统配置信息成功")
        this.dialogAllVisible=false
      }else {
        await this.getEnableSystem()
      }
    },

    /*上传商城商标*/
    /*上传图片*/
    handleAvatarSuccess(res, file) {
      this.system.dexLogo = res.url;
    },

  }
}
</script>

<style scoped>
.busman-manager{
  margin: 10px auto;
  width: 1536.670px;
  padding: 30px 20px 20px 20px;
  height: 620px;
  background-color: white;
}

.my-label {
  background: #E1F3D8;
}

.my-content {
  background: #FDE2E2;
}

.avatar{
  height: 100px;
}
</style>