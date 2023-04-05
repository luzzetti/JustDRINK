<template>
    <div class="d-flex justify-content-center mt-5 min">
        <div class="card" style="width: 18rem;">
            <!-- <img src="..." class="card-img-top" alt="..."> -->
            <div class="card-body">
                <h5 class="card-title text-capitalize">{{ restaurant.name }}</h5>
                <p class="card-text">Qui specifiche varie</p>
                <router-link :to="{path:`/restaurants/${this.$route.params.id}/menu/section`}"><button class="btn btn-primary">Vai al Menu</button></router-link>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { store } from '../store';
export default {
    name: 'ShowRestaurant',
    data() {
        return {
            restaurant: '',
        }
    },
    methods: {
        infoRestaurant() {
            axios.get(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.id}`).then((response) => {
                console.log(response.data);
                this.restaurant = response.data;
                // console.log(this.restaurant);
            })
        },
    },
    mounted() {
        this.infoRestaurant()
    }
}
</script>

<style lang="scss" scoped>
.min{
    // transform: -50%;
    translate: -10%;
}
</style>