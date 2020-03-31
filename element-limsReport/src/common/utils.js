import { fetch } from '../request/fetch'

export const getAgeNum = timer => {
  let ageNum = 0
  let birthYear = timer.getFullYear()
  let birthMonth = timer.getMonth() + 1
  let birthDay = timer.getDate()
  let d = new Date()
  let nowYear = d.getFullYear()
  let nowMonth = d.getMonth() + 1
  let nowDay = d.getDate()
  if (nowYear == birthYear) {
    ageNum = 0 //同年 则为0岁
  } else {
    let ageDiff = nowYear - birthYear //年之差
    if (ageDiff > 0) {
      if (nowMonth == birthMonth) {
        let dayDiff = nowDay - birthDay //日之差
        if (dayDiff < 0) {
          ageNum = ageDiff - 1
        } else {
          ageNum = ageDiff
        }
      } else {
        let monthDiff = nowMonth - birthMonth //月之差
        if (monthDiff < 0) {
          ageNum = ageDiff - 1
        } else {
          ageNum = ageDiff
        }
      }
    } else {
      ageNum = -1 //返回-1 表示出生日期输入错误 晚于今天
    }
  }
  return ageNum
}

export const getCurrentKey = obj => {
  return Object.keys(obj)[0]
}

export const getSetCookieAttr = str => {
  let obj = {}
  let attrArr = str.split(';')
  attrArr.forEach(param => {
    let newParam = param.trim()
    let attrs = newParam.split('=')
    obj[attrs[0]] = attrs[1]
  })
  return obj
}

export const checkToken = () => {
  return new Promise((resolve, reject) => {
    fetch('user.checkToken')
      .then(res => {
        resolve(res.data.result)
      })
      .catch(e => {
        reject(e)
      })
  })
}
