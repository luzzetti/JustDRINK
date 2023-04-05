<template>
    <h1 class="mt-2">Tabella dei ristoranti</h1>
    
    <table class="table">
        <thead>
            <tr>
                <th scope="col">name</th>
                <th scope="col">Id Restaurant</th>
                <th scope="col">delete</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(item, index) in restaurants" :key="index">
                <td class="text-capitalize">{{item.name}}</td>
                <td class="text-capitalize"> <router-link :to="{name:'showRestaurant',params:{id:item.id}}">
                        {{item.id}}
                    </router-link></td>
               
                <td><form action="" method="DELETE">

                </form><i class="fa-solid fa-trash" @click="deleteRestaurantById(item.id)"></i></td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import { store } from '../store';
import axios from 'axios';
export default {
    name: 'ResturantList',
    data() {
        return {
            restaurants: [],
        }
    },
    methods: {
        infoRestaurant() {
            axios.get(`${store.apiBaseUrl}/api/1.0/restaurants`).then((response) => {
                console.log(response.data);
                this.restaurants = response.data.restaurants;
                // console.log(this.restaurant);
            })
        },
        deleteRestaurantById(id){
            axios.delete(`${store.apiBaseUrl}/api/1.0/restaurants/${id}`).then((response) => {
                location.reload();
            })
        }
    },
    
    mounted() {
        this.infoRestaurant();
    },

}
</script>

<style lang="scss" scoped>
.fa-solid {
    color: red;
}
</style>