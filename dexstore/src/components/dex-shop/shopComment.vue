<template>
  <div class="shop-detail-comment">
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <div class="clearfix-title">商品评论</div>
        <div class="clearfix-right" v-if="commentHeadMsg[0] !== 'NaN'"><span style="color: #888888">满意度</span><span class="right-data">{{commentHeadMsg[0]}}%</span></div>
      </div>
      <div v-if="comment.length > 0">
        <div>
          <span>全部({{commentHeadMsg[1]}})</span><el-divider direction="vertical"></el-divider><span>有图({{commentHeadMsg[2]}})</span>
        </div>
        <el-divider></el-divider>
        <!--用户评论节点-->
        <div v-for="(o,i) in comment" :key="comment.id">
          <div class="comment-content">
            <!--用户头像和昵称-->
            <div class="content-user">
              <div class="user">
                <div class="user-header"><el-avatar :src="o.user_headimage"></el-avatar></div>
                <div class="user-name">{{o.user_nickname}}</div>
              </div>
              <div style="margin-left: 66px">
                <el-rate
                    v-model="o.comment_rate"
                    disabled
                    show-score
                    text-color="#ff9900"
                    :score-template="o.comment_rate.toString()">
                </el-rate>
              </div>
            </div>
            <div class="content-content">
              <!--评论字段-->
              <div style="line-height: 2em;word-break: break-all;font-family:'Arial';color: #666">
                {{o.comment_connect}}
              </div>
              <!--评论图片-->
              <div class="list-image">
                <div class="demo-image__preview" v-for="(z,i) in o.comment_image">
                  <el-image
                      style="width: 100px; height: 100px"
                      :src="z"
                      :preview-src-list="o.comment_image">
                  </el-image>
                </div>
              </div>
            </div>
            <div class="content-date">
              <div>{{o.comment_date}}</div>
            </div>
          </div>
          <el-divider></el-divider>
        </div>
      </div>
      <div v-else>
        <el-empty description="当前暂无评论，赶快购买来评论吧"></el-empty>
      </div>

    </el-card>
  </div>
</template>

<script>
export default {
  name: "shopComment",
  props:['comment','commentHeadMsg'],
  data(){
    return {
      value:'',
    }
  }

}
</script>

<style scoped>
  .clearfix{
    content: "";
    display: flex;
    clear: both;
    justify-content: space-between;
    align-items: center;
  }
  .clearfix-title{
    font-size: 34px;
  }
  .clearfix-right .right-data{
    font-size: 50px;
  }
  /*用户评论模板*/
  .comment-content{
    width: 1442.430px;
    display: flex;
    align-items: flex-start;
  }
  /*用户头像和满意度空间*/
  .content-user{
    width: 300px;
/*    background-color: #6b95ea;*/
  }
  /*评论内容和照片空间*/
  .content-content{
    width: 900px;
/*    background-color: #1a1a1a;*/
  }
  /*时间空间*/
  .content-date{
    width: 242.43px;
  }
  .user{
    display: flex;
    justify-content: left;
    align-items: center;
  }
  .user-header{
    margin-left: 20px;
    margin-right: 10px;
  }
  .list-image{
    display: flex;
    margin-top: 10px;
  }
  .demo-image__preview{
    margin-right: 10px;

  }
  .content-date{
    display: flex;
    justify-content: center;
    color: #333333;
  }




</style>