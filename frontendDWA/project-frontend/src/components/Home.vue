<template>
<div>
  <nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <p class="navbar-item">
      StuPraksa!
    </p>

  </div>

  <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-end">
      <div class="navbar-item">
        <div class="buttons">
          <a class="button is-primary" @click="$router.push('/registracija')" v-if="!isAuthed">
            <strong>Registracija</strong>
          </a>
          <a class="button is-light" @click="$router.push('/login')" v-if="!isAuthed">
            Log in!
          </a>
          <a class="button is-success" v-if="isAuthed" @click ="$router.push('/dodajpraksu')" >
            <strong>Dodaj praksu !</strong>
          </a>
        </div>
      </div>
    </div>
  </div>
</nav>

  <section class="hero is-primary">
    <div class="hero-body">
      <div class="container has-text-centered">
        <h2 class="title">Ovdje se nalaze dostupne prakse</h2>
      </div>
    </div>
  </section>
  <section class="section">
    <div class="container">
      <div class="card" v-for="praksa in data" v-bind:key="praksa.id">
        <div class="card-content">
          <p class="title">{{praksa.ime}}</p>
          <p class="subtitle"> {{ praksa.podrucjePrakse }} </p>
          <p>Potreban broj studenata : {{ praksa.brojStudenta}}</p>
          <p class="izbrisi" style="display:none;">
          <button class="button is-danger">Izbrisi</button>
          </p>
          <p> Kreirano : {{ praksa.created_at }} </p>
          
        </div>
      </div>
    </div>
  </section>
</div>
</template>

<script>
  import axios from "axios"

  export default{
    data(){
      return{
        data : [],
        data2: []
      }
    },
    methods: {
      getPrakse(){
        const path = "http://127.0.0.1:5000/prakse"
        axios.get(path)
          .then((res)=>{

            this.data = res.data.data
            console.log(this.data)
          }).catch((error)=>{
            console.error(error)
          })
      },
      getPosloPrakse(){
         // ovdje se izvlaci email iz tokena
        const token = this.$store.state.jwt.token
        const tokenParts = token.split(".")
        const body = JSON.parse(atob(tokenParts[1]))
        const email = String(body.sub)+"/"
        const path2 = "http://127.0.0.1:5000/poslodavci/"+email
        axios.get(path2).then((res)=> {
            this.data2 = res.data.data
            console.log(this.data2)
        }).catch((err)=>{
          console.error(err)
        })
      }
    },
    computed: {
        isAuthed(){
            console.log(this.$store.getters.isAuth)
            return this.$store.getters.isAuth
        },
        samePoslodavac(){
          //lista svih praksi od jednog poslodavca
          //preko email
          //
          return 
        }
    },
    created(){
      this.getPrakse(),
      this.getPosloPrakse() 
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.izbrisi{
  text-align: end;
}
.section{
  padding-left: 25%;
  padding-right: 25%;
}
</style>