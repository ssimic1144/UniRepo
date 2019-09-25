<template>
<div class="container">
      <div class="container has-text-centered">
        <h2 class="title">Registracija</h2>
      </div>
    <div class="field">
      <label class="label">Ime</label>
      <div class="control">
        <input class="input" type="text" placeholder="e.g Alex Smith" v-model="postIme">
      </div>
    </div>
    <div class="field">
      <label class="label">Email</label>
      <div class="control">
        <input class="input" type="email" placeholder="e.g smith@gmail.com" v-model="postEmail">
      </div>
    </div>
    <div class="field">
      <label class="label">Lozinka</label>
      <div class="control">
        <input class="input" type="password" placeholder="*****************" v-model="postPasw">
      </div>
    </div>
    <div class="field">
      <p class="control">
        <button class="button is-success" @click="postPoslo()">
          Registriraj se!
        </button>
      </p>
      
    </div>
    <div class="field">
    <p class="control">
        <button class="button is-light" @click="$router.push('/')">
          Povratak na pocetnu
        </button>
    </p>
    </div>
</div>
</template>

<script>
import axios from "axios"

export default {
    data(){
        return{
            postIme:null,
            postEmail:null,
            postPasw: null
        }
    },
    methods:{
        postPoslo(){
            const path = "http://127.0.0.1:5000/poslodavci"
            axios.post(path, {
                ime: this.postIme,
                email: this.postEmail,
                pasw: this.postPasw
            }).then(() => this.$router.push("/login")).catch((e)=>{
                console.error(e)
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