<template>
  <div class="home">
    <NavBar />
    <br>
    <div class="container">
      <h1>Here is a display of all current issues</h1>
      <div class="card-deck">
      <IssueCard v-for="problem in problems" v-bind:info="problem" v-bind:key="problem.id" />
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue"
import IssueCard from "@/components/IssueCard.vue"
import {db} from "../main.js"
import store from "@/store.js"

export default {
  name: "Home",
  data(){
    return {
      problems:[],
      store
    }
  },
  methods:{

  },
  components:{
    NavBar,
    IssueCard
  },
  created(){
    //get request- uzima sve dokumente iz kolekcije
    db.collection("problems").get().then((querySnapshot) => {
      querySnapshot.forEach( (doc) => {
        //push u array for each
        var temp = doc.data()
        //dodaj id
        temp["id"] = doc.id;
        

        this.problems.push(temp)
      })
    })
  }

};
</script>
