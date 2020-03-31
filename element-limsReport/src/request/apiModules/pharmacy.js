export default {
  getConsultingList: {
    url: 'v1/clinic/consulting/type/list',
    method: 'get'
  },
  saveClinicForm: {
    url: 'v1/clinic/save',
    method: 'post'
  },
  getClinicTodayList: {
    url: 'v1/clinic/today',
    method: 'get'
  },
  queryClinicTodayForm: {
    url: 'v1/clinic/{id}',
    method: 'get'
  },
  queryMedicineOfIdsList: {
    url: 'v1/medicine/list/ids',
    method: 'get'
  },
  updateClinicForm: {
    url: 'v1/clinic/update',
    method: 'put'
  },
  queryInteractionList: {
    url: 'v1/interaction/list/medicines',
    method: 'post'
  }
}
