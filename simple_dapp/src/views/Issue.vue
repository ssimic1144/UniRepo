<template>
    <div class="issue">
        <NavBar />
        <h3>This is place where you will describe the issue you want to see resolved</h3>
        <div class="container">
            <form @submit.prevent="submitIssue">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Name of the issue</label>
                    <div class="col-sm-10">
                        <input v-model="issueName" type="text" class="form-control" placeholder="Write the name of the issue">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Description of the issue</label>
                    <div class="col-sm-10">
                        <textarea v-model="issueDesc" class="form-control" placeholder="Explain the problem in a few sentences so candidates can see if they are able to solve it" rows="3"></textarea>
                    </div>
                </div>
                <button type="submit" class="btn btn-outline-primary">Register the issue</button>
            </form>
            <br>
            <button class="btn btn-outline-info" @click="goBack">Back</button>
        </div>
    </div>

</template>
<script>
import NavBar from "@/components/NavBar.vue"
import {db} from "../main.js"

export default {
    name:"Issue",
    data(){
        return{
            issueName:"",
            issueDesc:""
        }
    },
    components:{
        NavBar
    },
    methods:{
        goBack: function(){
            this.$router.push("/home")
        },
        submitIssue: function(){
            //dodaje podatke u kolekciju
            db.collection("problems").add({
                issueName: this.issueName,
                issueDecs: this.issueDesc,
            }),
            this.$router.push("/home")
        }
    }

}
</script>

<style>

</style>