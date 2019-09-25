<template>
  <div class="container">
      <div class="container has-text-centered">
        <h2 class="title">Nova Praksa</h2>
      </div>
    <div class="field">
    <label class="label">Ime prakse</label>
        <div class="control">
            <input class="input" type="text" placeholder="" v-model="postIme">
        </div>
    </div>
    <div class="field">
    <label class="label">Kategorija</label>
        <div class="control">
            <input class="input" type="text" placeholder="" v-model="postKategorija">
        </div>
        <p class="help">Npr. Frontend, Backend, DevOps ...</p>
    </div>
    <div class="field">
    <label class="label">Broj potrebnih studenata</label>
        <div class="control">
            <input class="input" type="number" placeholder="Minimalno 1" v-model="postBroj">
        </div>
        <p class="help">Upisite brojku</p>
    
    </div>
   <div class="notRealyAcontrol">
   <button class="button is-primary" @click="postPrak()" >Dodaj praksu</button>
   </div>
   <p class="notRealyAcontrol">
    <button class="button is-light"  @click="$router.push('/')">
      Povratak na pocetnu
    </button>
  </p>
  </div>
</template>

<script>
import axios from "axios"

export default {
    data(){
        return{
            poslodavac:"",
            postIme:"",
            postKategorija:"",
            postBroj:""

        }
    },
    methods: {
        postPrak(){
            // ovdje se izvlaci email iz tokena
            const token = this.$store.state.jwt.token
            const tokenParts = token.split(".")
            const body = JSON.parse(atob(tokenParts[1]))
            // poslodavac sad sadrzi email poslodavca
            this.poslodavac = body.sub
            const path = "http://127.0.0.1:5000/prakse"
            axios.post(path, {
                ime: this.postIme,
                podrucjePrakse: this.postKategorija,
                brojStudenta: this.postBroj,
                poslodavac: this.poslodavac
            }).then(() => this.$router.push("/")).catch((e)=>{
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
.notRealyAcontrol{
    padding: 0.5%;
}
</style>