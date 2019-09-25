import Vue from "vue"

export const EventBus = new Vue()

export function isValidJwt(jwt){
    if (!jwt || (jwt.split(".").length < 3)){
        console.log(jwt)
        return false
    }
    console.log("First pass")
    const data = JSON.parse(atob(jwt.split(".")[1]))
    console.log(data)
    const exp = new Date (data.exp)
    console.log(exp)
    const now = new Date()
    console.log(now)
    return now < exp
}