<template>
    <h1>Aggiungi Prodotti</h1>

    <div>
        <form action="" method="POST">
            <div class="d-flex flex-column w-25 mt-4">
                <h2 class="mt-2" for="">Inserisci nome prodotto</h2>
                <label for="">Nome</label>
                <input type="text w-25" v-model="name" placeholder="inserisci nome prodotto">
                <label for="">Prezzo</label>
                <input type="number w-25" v-model="price" placeholder="inserisci prezzo prodotto">
                <button class="btn btn-secondary mt-2" @click.prevent="InviaProdotto()">Crea prodotto</button>
            </div>
        </form>
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
                this.price='';
            })
        },
    }
}
</script>

<style lang="scss" scoped></style>