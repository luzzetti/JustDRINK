<template>
    <h1>Aggiungi Prodotti</h1>

    <div>
        <form action="" method="POST">
            <div class="d-flex flex-column w-25 mt-4">
                <h2 class="mt-2" for="">Inserisci nome prodotto</h2>
                <label for="">Nome</label>
                <input type="text" v-model="name" placeholder="inserisci nome prodotto">
                <label for="">Prezzo</label>
                <input type="number" v-model="price" placeholder="inserisci prezzo prodotto">
                <button class="btn btn-secondary mt-2" @click.prevent="InviaProdotto()">Crea prodotto</button>
            </div>
        </form>
    </div>
    <h1 class="mt-5">Tabella dei Prodotti</h1>
    <div>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Id Product</th>
                    <th scope="col">Delete</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in products" :key="index">
                    <td class="text-capitalize">{{ item.name }}</td>
                    <td class="text-capitalize">{{ item.price }} </td>
                    <td>{{ item.id }}</td>
                    <td>
                        <form action="" method="DELETE">

                        </form><i class="fa-solid fa-trash" @click="deleteProductById(item.id)"></i>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>



<script>
import axios from 'axios';
import { store } from '../store';
export default {
    name: 'ShowProduct',
    data() {
        return {
            name: '',
            price: '',
            products: [],
        }
    },
    methods: {

        InviaProdotto() {
            let newData = {
                name: this.name,
                price: this.price
            }
            axios.post(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}/menu/sections/${this.$route.params.sectionId}/products`, newData).then((response) => {
                console.log(response.data);
                this.name = '';
                this.price = '';
                this.getProduct();
            })
        },
        getProduct() {
            axios.get(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}/menu/sections/${this.$route.params.sectionId}/products`).then((response) => {
                console.log(response.data);
                this.products = response.data;

            })
        },
        deleteProductById(id) {
            axios.delete(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}/menu/sections/${this.$route.params.sectionId}/products/${id}`).then((response) => {
                console.log(response)
                location.reload();
                
            })
        }
    },
    mounted() {
        this.getProduct()
    }
}
</script>

<style lang="scss" scoped>
.fa-solid {
    color: red;
}
</style>