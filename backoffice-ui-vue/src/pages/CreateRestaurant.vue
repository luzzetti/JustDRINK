<template>
    <h1>Creazione Ristorante</h1>
    <form action="" method="POST" class="mt-5">
        <div class="d-flex flex-column">
            <label for="">Name:</label>
            <input type="text" v-model="nameRestaurant" placeholder="inserisci nome ristorante" class="w-25">
            <button class="w-25 mt-1" @click.prevent="submitRestaurant()">Submit</button>
        </div>
    </form>
</template>

<script>
import { store } from '../store';
import axios from 'axios';
export default {
    name: 'CreateRestaurant',
    data() {
        return {
            nameRestaurant: '',

        }

    },
    methods: {
        submitRestaurant() {
            let newData = {
                name: this.nameRestaurant
            }
            axios.post(`${store.apiBaseUrl}/api/1.0/restaurants`, newData).then((response) => {
                console.log(response.data)
                this.nameRestaurant = ''
            })
        }
    }
}
</script>

<style lang="scss" scoped>

</style>