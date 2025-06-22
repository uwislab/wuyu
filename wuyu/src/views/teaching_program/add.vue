<template>


  <div class="createPost-container">
    <!--    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">-->
    <el-form ref="postForm" :model="postForm"   class="form-container">

      <div class="createPost-main-container">
        <el-row>

          <template>
            <aside>
              制定教学大纲
            </aside>
          </template>

          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="100" name="title" required>
                大纲标题
              </MDinput>
            </el-form-item>

            <div class="postInfo-container">
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="60px" label="制定人:" class="postInfo-container-item">
                    <!--                    <el-select v-model="postForm.author" :remote-method="getRemoteUserList" filterable default-first-option remote placeholder="编辑作者">-->
                    <el-input v-model="postForm.username" filterable default-first-option remote placeholder="编辑作者">
                      <!--                      <el-option v-for="(item,index) in userListOptions" :key="item+index" :label="item" :value="item" />-->
                    </el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="120px" label="制定时间:" class="postInfo-container-item">
                    <el-date-picker v-model="postForm.time" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期" />
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label-width="90px" label="重要性:" class="postInfo-container-item">
                    <el-rate
                      v-model="postForm.importance"
                      :max="3"
                      :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                      :low-threshold="1"
                      :high-threshold="3"
                      style="display:inline-block"
                    />
                  </el-form-item>
                </el-col>

              </el-row>
            </div>
          </el-col>
        </el-row>

        <el-form-item style="margin-bottom: 40px;" label-width="70px" label="概要:">
          <el-input v-model="postForm.outline" :rows="1" type="textarea" class="article-textarea" autosize placeholder="请输入内容" />
          <!--          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}words</span>-->
        </el-form-item>

        <el-form-item prop="content" style="margin-bottom: 30px;">
          <Tinymce ref="editor" v-model="postForm.content" :height="400" />
        </el-form-item>

        <!--        <el-form-item prop="image_uri" style="margin-bottom: 30px;">-->
        <!--          <Upload v-model="postForm.image_uri" />-->
        <!--        </el-form-item>-->

        <!--        <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">-->
        <sticky :z-index="10" :class-name="'sub-navbar '">
          <!--          <CommentDropdown v-model="postForm.comment_disabled" />-->
          <!--          <PlatformDropdown v-model="postForm.platforms" />-->
          <!--          <SourceUrlDropdown v-model="postForm.source_uri" />-->
          <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
            提交
          </el-button>

          <!--          <el-button v-loading="loading" type="warning" @click="draftForm">-->
          <!--            草稿-->
          <!--          </el-button>-->

        </sticky>
      </div>
    </el-form>
  </div>

</template>

<script>
  import Tinymce from '@/components/Tinymce'
  import Upload from '@/components/Upload/SingleImage3'
  import MDinput from '@/components/MDinput'
  import Sticky from '@/components/Sticky' // 粘性header组件

  import { PlatformDropdown, SourceUrlDropdown } from './components/Dropdown'
  import teachingProgram  from '@/api/teaching_program'
  import  qs from 'qs'

  const defaultForm = {
    // status: 'draft',
    title: '', // 文章题目
    content: '', // 文章内容
    outline: '', // 文章摘要
    name:'',
    time:'',
    // source_uri: '', // 文章外链
    // image_uri: '', // 文章图片
    // display_time: undefined, // 前台展示时间
    id: '',
    // platforms: ['a-platform'],
    // comment_disabled: false,
    importance: 1
  }

  export default {
    name: 'ArticleDetail',
    components: { Tinymce, MDinput, Upload, Sticky, PlatformDropdown, SourceUrlDropdown },
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      // const validateRequire = (rule, value, callback) => {
      //   if (value === '') {
      //     this.$message({
      //       message: rule.field + '为必传项',
      //       type: 'error'
      //     })
      //     callback(new Error(rule.field + '为必传项'))
      //   } else {
      //     callback()
      //   }
      // }
      // const validateSourceUri = (rule, value, callback) => {
      //   if (value) {
      //     if (validURL(value)) {
      //       callback()
      //     } else {
      //       this.$message({
      //         message: '外链url填写不正确',
      //         type: 'error'
      //       })
      //       callback(new Error('外链url填写不正确'))
      //     }
      //   } else {
      //     callback()
      //   }
      // }
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        userListOptions: [],
        // rules: {
        //   image_uri: [{ validator: validateRequire }],
        //   title: [{ validator: validateRequire }],
        //   content: [{ validator: validateRequire }],
        //   source_uri: [{ validator: validateSourceUri, trigger: 'blur' }]
        // },
        tempRoute: {}
      }
    },
    computed: {
      // contentShortLength() {
      //   return this.postForm.outline.length
      // },
      displayTime: {
        // set and get is useful when the data
        // returned by the back end api is different from the front end
        // back end return => "2013-06-25 06:59:25"
        // front end need timestamp => 1372114765000
        get() {
          return (+new Date(this.postForm.display_time))
        },
        set(val) {
          this.postForm.display_time = new Date(val)
        }
      }
    },

    created() {
      if (this.isEdit) {
        const id = this.$route.params && this.$route.params.id
        this.fetchData(id)
      }

      // Why need to make a copy of this.$route here?
      // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
      // https://github.com/PanJiaChen/vue-element-admin/issues/1221
      this.tempRoute = Object.assign({}, this.$route)
    },

    methods: {
      fetchData(id) {

        fetchArticle(id).then(response => {
          this.postForm = response.data

          // just for test
          // this.postForm.title += `   Article Id:${this.postForm.id}`
          // this.postForm.content_short += `   Article Id:${this.postForm.id}`

          // set tagsview title
          this.setTagsViewTitle()

          // set page title
          this.setPageTitle()

        }).catch(err => {
          console.log(err)
        })
      },

      setTagsViewTitle() {
        const title = 'Edit Article'
        const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` })
        this.$store.dispatch('tagsView/updateVisitedView', route)
      },

      setPageTitle() {
        const title = 'Edit Article'
        document.title = `${title} - ${this.postForm.id}`
      },

      //提交表单
      submitForm() {
        console.log(this.postForm)
        this.$refs.postForm.validate(valid => {
          if (valid) {
            this.loading = true
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              /*在Vue + elementui 的项目中,请求数据使用axios发送请求发现后端接收不到参数,需要把参数序列化。
                  Vue中提供了qs这个插件，可以进行数据序列化。*/
              /*qs.stringify()将对象 序列化成URL的形式以&进行拼接*/
              teachingProgram.saveTeachingProgram(this.postForm).then(response => {
                if(response.code=200) {
                  this.$notify({
                    title: '成功',
                    message: '制定大纲成功',
                    type: 'success',
                    duration: 2000
                  })
                  // this.$message({
                  //   message: '提交成功',
                  //   type: 'success',
                  //   duration:1000
                  // });
                  this.$router.back();
                  // this.postForm.status = 'published'
                  this.loading = false
                }

              });
            }).catch(() => {
              console.log("catch")
              this.loading = false
            })
          } else {
            console.log('提交错误')
            return false
          }
        })
      },

      draftForm() {
        if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
          this.$message({
            message: '请填写必要的标题和内容',
            type: 'warning'
          })
          return
        }
        this.$message({
          message: '保存成功',
          type: 'success',
          showClose: true,
          duration: 1000
        })
        this.postForm.status = 'draft'
      },
      /*作者获取信息*/
      // getRemoteUserList(query) {
      //   searchUser(query).then(response => {
      //     if (!response.data.items) return
      //     this.userListOptions = response.data.items.map(v => v.name)
      //   })
      // }
    }
  }
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";

  .createPost-container {
    position: relative;

    .createPost-main-container {
      padding: 40px 45px 20px 50px;

      .postInfo-container {
        position: relative;
        @include clearfix;
        margin-bottom: 10px;

        .postInfo-container-item {
          float: left;
        }
      }
    }

    .word-counter {
      width: 40px;
      position: absolute;
      right: 10px;
      top: 0px;
    }
  }

  .article-textarea ::v-deep {
    textarea {
      padding-right: 40px;
      resize: none;
      border: none;
      border-radius: 0px;
      border-bottom: 1px solid #bfcbd9;
    }
  }
</style>
