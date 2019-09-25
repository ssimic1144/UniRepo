<template>
<div class="container">

      <div class="container has-text-centered">
        <h2 class="title">Login</h2>
      </div>
<div class="field">
  <p class="control has-icons-left has-icons-right">
    <input class="input" type="email" placeholder="Email" v-model="email">
    <span class="icon is-small is-left">
      <i class="fas fa-envelope"></i>
    </span>
    <span class="icon is-small is-right">
      <i class="fas fa-check"></i>
    </span>
  </p>
</div>
<div class="field">
  <p class="control has-icons-left">
    <input class="input" type="password" placeholder="Password" v-model="pasw">
    <span class="icon is-small is-left">
      <i class="fas fa-lock"></i>
    </span>
  </p>
</div>
<div class="field">
  <p class="control">
    <button class="button is-success" @click="auth">
      Login
    </button>
  </p>
</div>
<div class="field">
  <p class="control">
    <button class="button is-light"  @click="$router.push('/')">
      Povratak na pocetnu
    </button>
  </p>
</div>
</div>
</template>

<script>
import { EventBus } from "@/utils"
export default {
    data(){
        return{
            email:"",
            pasw:""
        }
    },
    methods: {
        auth(){
            this.$store.dispatch("login", {email: this.email, pasw: this.pasw})
                .then(()=> this.$router.push("/loading")).catch((err)=>{
                    console.log(err)
                })
        }
    }

}
</script>

<style scoped>
 .container{
    padding-top: 10%;

}
.button{
    margin: auto;
    display: block;
}

</style>