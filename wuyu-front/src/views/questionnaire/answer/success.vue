<template>
  <div class="app-container">
    <div class="show-failure-frame">
      <div class="result-wrap">
        <div class="result-icon-wrap">
          <img
            src="https://cdn.showmebug.com/packs/media/images/teams/result/written-pad-success-e4f7d92e0dae193442ef0fc6337a6b51.svg"
          />
        </div>
        <div class="result-content-wrap">
          <div class="result-desc-title">您的答卷已经提交，感谢您的参与！</div>
          <a class="smb-btn smb-btn-blue result-op" @click="$router.go(-2)"
            >返回主页</a
          >
        </div>
      </div>
    </div>
  </div>
</template>
      
<script>
  export default {};
</script>
      
  <style scoped >
.app-container {
  height: 100vh;
  background: #f5f5f5;
  padding-left: 0px;
  padding-right: 0px;
}
.result-wrap {
  height: 310px;
  background: #fff;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  padding: 40px 80px;
  width: 1260px;
  margin: auto;
}
.result-wrap .result-icon-wrap,
.result-wrap.with-extra-info .result-base-info .result-icon-wrap {
  width: 80px;
  height: 80px;
}
.result-wrap .result-content-wrap,
.result-wrap.with-extra-info .result-base-info .result-content-wrap {
  flex: auto;
  display: flex;
  flex-direction: column;
  padding-left: 20px;
  padding-top: 12px;
}
img {
  border-style: none;
}
img,
svg {
  vertical-align: middle;
}
.result-wrap .result-content-wrap .result-desc-title,
.result-wrap.with-extra-info
  .result-base-info
  .result-content-wrap
  .result-desc-title {
  font-weight: 600;
  font-size: 16px;
  line-height: 22px;
  color: #333;
}
.result-wrap .result-content-wrap .result-desc-content,
.result-wrap.with-extra-info
  .result-base-info
  .result-content-wrap
  .result-desc-content {
  margin-top: 12px;
  font-family: PingFang SC;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  color: #666;
}
.result-wrap .result-content-wrap .result-op,
.result-wrap.with-extra-info .result-base-info .result-content-wrap .result-op {
  margin-top: 33px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  width: -webkit-fit-content;
  width: -moz-fit-content;
  width: fit-content;
  height: 36px;
  background: #408cff;
  border-radius: 4px;
  border: 1px solid #408cff;
  color: #fff;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  box-sizing: border-box;
}
</style>