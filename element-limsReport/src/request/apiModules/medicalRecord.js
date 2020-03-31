export default {
  queryAllPatient: {
    url: 'v1/patient/list',
    method: 'get'
  },
  querySinglePatient: {
    url: 'v1/patient/list/{condition}',
    method: 'get',
    mode: 'search'
  },
  queryPatientRecord: {
    url: 'v1/case/list/patient/{id}',
    method: 'get'
  }
}
