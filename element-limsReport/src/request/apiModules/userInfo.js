export default {
  getInsuranceTypeList: {
    url: 'v1/patient/health/insurance/list',
    method: 'get'
  },
  getEducationalList: {
    url: 'v1/patient/educational/status/list',
    method: 'get'
  },
  getProvinceList: {
    url: 'v1/province/city/list',
    method: 'get'
  },
  queryMedicineList: {
    url: 'v1/medicine/list/{condition}',
    method: 'get'
  },
  queryDiseasesList: {
    url: 'v1/diseases/list/{condition}',
    method: 'get'
  },
  getAllergicList: {
    url: 'v1/patient/allergic/material/list',
    method: 'get'
  },
  getPatientDetail: {
    url: 'v1/patient/detail/{id}',
    method: 'get'
  }
}
