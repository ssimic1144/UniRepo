<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <span class="navbar-brand" >Because Your Vote Matters!</span>
  <div class="ml-auto">
  <button id="navbtn" class="btn btn-outline-success my-2 my-sm-0" type="submit" @click="problem">Submit a problem</button>
  <button id="navbtn" class="btn btn-outline-secondary my-2 my-sm-0" type="submit" @click="logout">Logout</button>
  </div>
</nav>
</template>

<script>
import firebase from "firebase";

export default {
    data(){
        return {}
    },
    methods:{
    logout: function() {
      firebase.auth().signOut().then( () => {
        this.$router.push("login")
      })
    },
    problem: function(){
      this.$router.push("/issue")
    }
  }
}
</script>

<style>
#navbtn{
  margin: 13px 12px 12px 10px;
}

</style>