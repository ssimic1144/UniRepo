<template>
    <div class="issueCard">
        <div class="card" style="width: 18rem;">
            <div class="card-header bg-transparent">
                {{info.issueName}}
            </div>
            <div class="card-body">
              <a @click="goToVote" class="btn btn-outline-primary" id="footerBtns" >Vote</a>
              <!-- Za poslat varijablu {{ovog.tipa}} u modal treba dodat v-bind(:) ispred id modala i data-target -->
              <a v-if="store.userDelegate" class="btn btn-outline-primary" id="footerBtns" data-toggle="modal" :data-target="'#regDele' + info.issueName">Register as candidate</a>
            </div>
        </div>
        <div class="regDele">
            <div class="modal fade" :id="'regDele' + info.issueName" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                  <div class="modal-body">
                    Are you sure you want to be a delegate for {{info.issueName}}? 
                    <p></p>
                  </div>
                  <div class="modal-footer">
                    <button @click="register" type="button" class="btn btn-outline-primary">Yes</button>
                    <button type="button" class="btn btn-outline-info" data-dismiss="modal">No</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

    </div>
    
</template>

<script>
//kad se store importa treba ga stavit i u data(){return}
import store from "@/store.js";
import newContract from "../web3.js"
import {db} from "@/main.js"
import firebase from "firebase";



export default {
    props: ["info"],
    data(){
        return{
            store,
            cand:"test",
            name:"lal",
            problemID: this.info.id,
            
        }
    },
    methods:{
      register(){
        newContract.methods.registerCandidateForProblem(store.candidateID,store.candidateName, this.problemID).send({from:store.account}).then( () => {
          console.log("updating db")
          //nakon sto se registra na bc registriraj u bazi 
          db.collection("problems").doc(this.info.id).update({
            hasCandidate : true,
            //ovo updejta array (ne brise postojeci unos) 
            candidates : firebase.firestore.FieldValue.arrayUnion(store.candidateID)
          });
        });     
      },
      goToVote(){
        this.$router.push({path: `/vote/${this.problemID}`})
      }
    },
    computed:{
    },

    created(){
      store.account = window.ethereum.selectedAddress;
      console.log(store.account)
      console.log(this.problemID)
    }
}
</script>

<style>

#footerBtns{
    margin: 5px;
}

.card{
  margin : 20px;
}

</style>