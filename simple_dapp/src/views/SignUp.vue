<template>
    <div class="sign-up container">
        <h3>Create new account</h3>
        <form @submit.prevent="signUp">
            <div class="form-group">
                <input class="form-control" type="email" v-model="email" placeholder="Email"><br>
            </div>
            <div class="form-group">
                <input class="form-control" type="password" v-model="password" placeholder="Password"><br>
            </div>
            <div class="form-check">
                <input v-model="delegate" type="checkbox" class="form-check-input" >
                <label class="form-check-label" for="exampleCheck1">Want to be a delegate</label>
            </div>
            <div v-if="delegate" class="form-group">
                <input class="form-control" type="text" v-model="info.firstName" placeholder="First name"><br>
            </div>
            <div v-if="delegate" class="form-group">
                <input class="form-control" type="text" v-model="info.lastName" placeholder="Last name"><br>
            </div>
            <div v-if="delegate" class="form-group">
                <textarea class="form-control" type="text" v-model="info.description" placeholder="Write a short description why You would make a good delegate"></textarea><br>
            </div>
            <br>
            <button type="submit" class="btn btn-primary">Sign up</button>
        </form>
        <br>
        <p>Go back to <router-link to="/login">login screen</router-link>.</p>
    </div>
</template>

<script>
import firebase from "firebase";
import {db} from "../main.js";

export default {
    name:"signUp",
    data(){
        return {
            email:"",
            password:"",
            delegate:false,
            info:{
                firstName:null,
                lastName:null,
                description:null
            }
        }
    },
    methods: {
        signUp: function(){
            firebase.auth().createUserWithEmailAndPassword(this.email, this.password).then(
                () => {
                    db.collection("users").add({
                      email: this.email,
                      delegate: this.delegate,
                      hasVoted: false,
                      info: this.info

                    })
                    this.$router.replace("home")
                },
                (err) => {
                    alert("Oops. "+ err.message)
                }
            );
        }
    }

}
</script>

<style>

</style>