export default {
  getQuestionList: {
    url: 'v1/question/template/list/detail',
    method: 'get'
  },
  getOriginalList: {
    url: 'v1/detection/item/list/detail',
    method: 'get'
  },
  getTargetList: {
    url: 'v1/assess/target/organ/list',
    method: 'get'
  },
  saveAssessForm: {
    url: 'v1/assess/save',
    method: 'post'
  },
  getReportTodayList: {
    url: 'v1/report/today',
    method: 'get'
  },
  getReportDetail: {
    url: 'v1/report/detail/{id}',
    method: 'get'
  },
  updateReportForm: {
    url: 'v1/report/update',
    method: 'put'
  }
}
