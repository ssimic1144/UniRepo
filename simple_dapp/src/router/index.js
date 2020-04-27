import Vue from "vue";
import VueRouter from "vue-router";
import firebase from "firebase";

import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import SignUp from "../views/SignUp.vue";
import Issue from "../views/Issue.vue";
import Vote from "../views/Vote.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/home",
    name: "home",
    component: Home,
    meta: {
      requiresAuth: true
    }
  },
  {
    path:"/vote/:id",
    props:true,
    name:"vote",
    component:Vote,
    meta: {
      requiresAuth: true
    }
  },
 {
    path: "/",
    redirect:"/login"
  },
  {
    //redirect every "bad" path to /login
    path: "*",
    redirect: "/login"
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path:"/sign-up",
    name:"SignUp",
    component: SignUp
  },
  {
    path:"/issue",
    name:"Issue",
    component: Issue,
    meta:{
      requiresAuth:true
    }
  }
];

const router = new VueRouter({
  routes
});

// If user is not auth go to login
router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record  => record.meta.requiresAuth)
  const currentUser = firebase.auth().currentUser

  if (requiresAuth && !currentUser) {
      next('/login')
  } else if (requiresAuth && currentUser) {
      next()
  } else {
      next()
  }
})


export default router;
