<template>
    <div class="login container">
        <h3>Log in</h3>
        <form @submit.prevent="login">
            <div class="form-group">
                <input class="form-control" type="text" v-model="email" placeholder="Email"><br>
            </div>
            <div class="form-group">
                <input class="form-control" type="password" v-model="password" placeholder="Password"><br>
            </div>
        <button type="submit" class="btn btn-primary">Log in</button>
        </form>
        <br>

        <p>If you don't have an account you can <router-link to="/sign-up">create one</router-link>!</p>
    </div>
</template>

<script>
import firebase from "firebase";
import store from "@/store.js";
import {db} from "@/main.js"

export default {
    name:"login",
    data(){
        return {
            email:"",
            password:"",
            store
        }
    },
    methods:{
        login: function(){
            //logira usera
            firebase.auth().signInWithEmailAndPassword(this.email,this.password).then(
                () => {
                    //prvo sto radi nakon auth uzima iz baze
                    //dal je user delegate il ne i sprema u store
                    //opcinalno sprema i email mozd bude trebao
                    db.collection("users").where("email", "==", this.email).get().then((querySnapshot)=>{
                        querySnapshot.forEach((doc)=>{
                            store.userDelegate = doc.data().delegate;
                            store.userEmail = doc.data().email;
                            store.userID = doc.id
                            if (store.userDelegate) {
                                store.candidateName = doc.data().info.firstName+ " "+ doc.data().info.lastName;
                                store.candidateID = doc.id
                            };
                        })
                    }),
                    //premjesta rutu na home
                    this.$router.replace("home")
                },
                (err) => {
                    alert("Oops . " + err.message)
                }
            )
        }
    }

}
</script>

<style>

</style>