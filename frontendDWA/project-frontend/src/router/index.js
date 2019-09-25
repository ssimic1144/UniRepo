import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from "@/components/Login"
import Register from "@/components/Register"
import Loading from "@/components/Loading"
import DodajPraksu from "@/components/DodajPraksu"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path:"/login",
      name:"Login",
      component: Login
    },
    {
      path:"/registracija",
      name:"Register",
      component: Register
    },
    {
      path:"/loading",
      name:"Loading",
      component: Loading
    },
    {
      path:"/dodajpraksu",
      name:"DodajPraksu",
      component: DodajPraksu
    }
  ]
})
