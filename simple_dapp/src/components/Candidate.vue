<template>
<div class="candidate">
    <div class="card" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">{{this.fullName}}</h5>
        <p class="card-text">{{this.desc}}</p>
        <p class="card-text">Current number of votes : {{inf.numVotes}}</p>
        <a v-if="!hasVoted" @click="vote" class="btn btn-primary">Vote</a>
      </div>
    </div>
</div>
</template>

<script>
import store from "@/store.js";
import {db} from "../main.js";
import newContract from "../web3.js"



export default {
    props:["inf"],
    data(){
        return {
            store,
            tempCandId: this.inf.candId,
            fullName:null,
            desc:null,
            hasVoted:null
        }
    },
    name:"candidate",
    created(){
      db.collection("users").doc(this.tempCandId).get().then((doc)=>{
        this.fullName = doc.data().info.firstName + " "+ doc.data().info.lastName;
        this.desc = doc.data().info.description
        this.hasVoted = doc.data().hasVoted
      })
    },
    methods:{
        vote(){
          newContract.methods.vote(this.tempCandId, store.problemID).send({from:store.account}).then(()=>{
           console.log("updating user db")
           db.collection("users").doc(store.userID).update({
             hasVoted:true
           })
           this.hasVoted = true
           this.$router.push("/home")
          })
        }
    }
}
</script>

<style>

</style>