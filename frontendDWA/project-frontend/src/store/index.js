import Vue from "vue"
import Vuex from "vuex"

import {isValidJwt, EventBus} from "@/utils"
import { auth } from "@/api"

Vue.use(Vuex)

const state = {
    user: {},
    jwt:""
}

const actions = {
    login(context, userData){
        context.commit("setUserData", {userData})
        return auth(userData).then(response => context.commit("setJwtToken", {jwt: response.data}))
                            .catch(error =>{
                                console.log("Error Auth:", error)
                                EventBus.$emit("failedAuth", error)
                            })
    }
}

const mutations = {
    setUserData(state, payload){
        console.log("setUserData payload = ", payload)
        state.userData = payload.userData
    },
    setJwtToken(state, payload){
        console.log("setJwtToken payload = ", payload)
        localStorage.token = payload.jwt.token
        state.jwt = payload.jwt
    }
}

const getters = {
    isAuth(state){
        return isValidJwt(state.jwt.token)
    }
}

const store = new Vuex.Store({
    state,
    actions,
    mutations,
    getters
})

export default store