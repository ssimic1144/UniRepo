<template>
<div class="vote">
    <NavBar/>
    <br>
    <h2>{{this.problemName}}</h2>
    <br>
    <div class="container">
        <p>{{this.problemText}}</p>
        <div class="card-deck">
            <Candidate v-for="cand in candidateToSend" v-bind:key="cand.id" v-bind:inf="cand" />
        </div>
    </div>
</div>  
</template>

<script>
import NavBar from "@/components/NavBar.vue"
import store from "@/store.js";
import {db} from "../main.js";
import newContract from "../web3.js";
import Candidate from "@/components/Candidate.vue"



export default {
    components:{
        NavBar,
        Candidate
    },
    name:"vote",
    data(){
        return {
            store,
            candidateArray:[],
            candidate:{
                candId:"",
                numVotes:""
            },
            candidateToSend:[],
            problemName:"",
            problemText:""
        }
    },
    created(){
        store.problemID = this.$route.params.id;
        console.log(store.problemID);
        db.collection("problems").doc(store.problemID).get().then((doc) => {
            var temp = doc.data()
            this.candidateArray = temp["candidates"]
            this.problemName = temp["issueName"]
            this.problemText = temp["issueDecs"]
            console.log("From db",this.candidateArray)
            this.candidateArray.forEach(element => {
                newContract.methods.getVotesForCandidate(element, store.problemID).call({from:store.account}).then((result)=>{
                    console.log(element, result)
                    this.candidate.candId = element
                    this.candidate.numVotes = result
                    this.candidateToSend.push(this.candidate)
                })
        });
            
        });
        

        

    }
}
</script>

<style>

</style>