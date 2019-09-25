<template>
<div class="container">
    <h1><strong>Loading</strong></h1>
    <progress class="progress is-large is-info" max="100">40%</progress>
    <a class="button is-dark" v-if="isAuthed" @click="$router.push('/')" >Ulogiran si kreni na pocetnu stranicu!</a>
    <a class="button is-white" v-if="!isAuthed">White</a>
</div>
</template>

<script>
export default {
    computed: {
        isAuthed(){
            console.log(this.$store.getters.isAuth)
            return this.$store.getters.isAuth
        }
    }

}
</script>

<style scoped>
.container{
    padding : 40%
}
</style>