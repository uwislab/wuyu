import request from '@/utils/request'

export function getNoticeList(){
  return request({
    url: '/getNoticeList',
    method: 'get',
  })
}

export function deleteById(id){
  return request({
    url: '/deleteById/' + id,
    method: 'get',
  })
}

export function addList(form){

  console.log(form)
  return request({
    url: '/addList',
    method: 'post',
    data: form
  })
}

export function getDomainList() {
  return request({
    url: '/fuScale/getDomainList',
    method: 'get',


  })
}

export function getAward(){
  return request({
    url: '/awards',
    method: 'get',
  })
}

export function addAward(award) {
  return request({
    url: '/awards',
    method: 'post',
    data: award,
  })
}

export function deleteAward(id) {
  return request({
    url: `/awards/${id}`,
    method: 'delete'
  });
}



export function insertFuScale(scaleInfo) {
  return request({
    url: '/fuScale/insertFuScale',
    method: 'post',
    data: scaleInfo,
  })
}

//获取年级数据
export function getGradeScore(shuju) {
    return request({
      url: `/GradeScore/getGradeScore`,
      method: 'get',
      params: {
        shuju: shuju
      }
    })
 }

 //获取年级平均数据
export function gradeScore(grade) {
  return request({
    url: `/diagnose/grade/average/scores`,
    method: 'get',
    params: {
      grade: grade
    }
  })
}

 //获取班级平均数据
 export function gradeclassScore(grade, sclass) {
  return request({
    url: '/diagnose/class/average/scores',
    method: 'get',
    params: {
      grade,
      sclass
    }
  })
}

export function getFuScaleId() {
  return request({
    url: '/fuScale/getFuScaleId',
    method: 'get',


  })
}

export function getFuScaleByTitle(title) {
  return request({
    url: '/fuScale/getFuScaleByTitle',
    method: 'get',
    params: { title: title },



  })
}

export function getRadarList(){
  return request({
    url: '/getRadarList',
    method: 'get',
  })
}

export function getAllFuScale() {
  return request({
    url: '/fuScale/getAllFuScale',
    method: 'get',


  })
}

export function getItemContent() {
  return request({
    url: '/fuScale/getItemContent',
    method: 'get',


  })
}

export function deleteItemContent(id) {
  return request({
    url: '/fuScale/deleteItemContent',
    method: 'get',
    params: { id: id },


  })
}

export function insertScaleContent(scaleContent) {
  return request({
    url: '/fuScale/insertScaleContent',
    method: 'post',
    data: scaleContent,


  })
}

//获取当前与父层级的分数和  参数:评价项id
//响应参数：CurSum(当前层级分数和),PreSum(上1层级分数和)
export  function SumScores(ItemId) {
  return request({
    url: '/fuScale/SumScores',
    method: 'get',
    params: { id: ItemId },


  })
}

// 获取当前与子层级的分数和  请求参数：id(评价项id)
// 响应参数：CurSum(当前层级分数和),ChildSum(下1层级分数和)
export  function ChildSumScores(ItemId) {
  return request({
    url: '/fuScale/ChildSumScores',
    method: 'get',
    params: { id: ItemId },

  })
}

//这里需要一个根据量表id查所有层级分数和的方法，返回map
export  function getAllLevelScore(scaleId) {
  return request({
    url: '/fuScale/getAllLevelScore',
    method: 'get',
    params: { scaleId: scaleId },

  })
}

export function updateScaleContent(scaleContent) {
  return request({
    url: '/fuScale/editItem',
    method: 'post',
    data: scaleContent,
  })
}

//获取量表的一级项目信息
export function getContentByIdCopy(scaleId) {
  return request({
    url: '/fuScale/getContentByIdCopy',
    method: 'get',
    params: { scaleId: scaleId },


  })
}

//获取量表的所有项目信息
export function getContentById(scaleId) {
  return request({
    url: '/fuScale/getContentById',
    method: 'get',
    params: {scaleId: scaleId},


  })
}

export function getTreeItemList(scaleId) {
  return request({
    url: '/fuScale/getTreeItemList',
    method: 'get',
    params: { scaleId: scaleId },


  })
}

export function getEditableContent(scaleId) {
  return request({
    url: '/fuScale/getEditableContent',
    method: 'get',
    params: { scaleId: scaleId },


  })
}

export function getBy(queryForm) {
  return request({
    url: '/fuScale/getBy',
    method: 'post',
    data: queryForm,

  })
}

export function deleteScale(scaleId) {
  return request({
    url: '/fuScale/deleteScale',
    method: 'post',
    params: { scaleId: scaleId },

  })
}

export function scaleCopy(scaleId) {
  return request({
    url: '/fuScale/scaleCopy',
    method: 'post',
    params: { scaleId: scaleId },

  })
}

//获取下级项目信息
export function getItemByPreCopy(itemId) {
  return request({
    url: '/fuScale/getItemByPreCopy',
    method: 'get',
    params: { preItemId: itemId },

  })
}

export function changeState(scaleId, state) {
  return request({
    url: '/fuScale/changeState',
    method: 'get',
    params: { scaleId: scaleId, state: state },

  })
}

export function getFuScaleByState() {
  return request({
    url: '/fuScale/getFuScaleByState',
    method: 'get',
    params: { stateId: 0 },

  })
}

//需要一个选择多项状态获取FuScale
export function getFuScaleByStates(stateIds) {
  return request({
    url: '/fuScale/getFuScaleByStates',
    method: 'get',
    params: { stateIds: stateIds },

  })
}

export function getAllTeacher() {
  return request({
    url: '/teacher/getAllTeacher',
    method: 'get',

  })
}

export function deleteItem(itemId) {
  return request({
    url: '/fuScale/deleteItem',
    method: 'post',
    params: { itemId: itemId },

  })
}
export function editScale(scaleInfoForm) {
  return request({
    url: '/fuScale/editScale',
    method: 'post',
    data: scaleInfoForm,

  })
}

export function getExam(scaleId) {
  return request({
    url: '/fuScale/getExam',
    method: 'get',
    params: {scaleId: scaleId},


  })
}

//需要插入数据到fu_score表的接口
export function insertFuScore(scoreInfo) {
  return request({
    url: '/fuScore/insertFuScore',
    method: 'post',
    data: scoreInfo,
  })
}

//获取年级列表
export function getGrade() {
  return request({
    url: '/fuScale/getGrade',
    method: 'get',

  })
}

//根据量表id获取绑定的年级
export function fetchGrades(scaleId) {
  return request({
    url: '/fuScale/getExecution',
    method: 'get',
    params: {scaleId: scaleId},
  })
}

export function addExecution(excuteForm) {
  return request({
    url: '/fuScale/addExecution',
    method: 'post',
    data: excuteForm,
  })

}

export function delExecution(execId) {
  return request({
    url: '/fuScale/delExecution',
    method: 'post',
    params: { execId: execId },


  })
}
