import Vue from "vue";
import firebase from "firebase";
import App from "./App.vue";
import router from "./router";
import config from "./config";

Vue.config.productionTip = false;


firebase.initializeApp(config);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

export const db = firebase.firestore()