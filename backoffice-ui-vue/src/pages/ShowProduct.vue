<template>
   <h1>Aggiungi Prodotti</h1> 

    <div>
        <form action="" method="POST">
            <div class="d-flex flex-column w-25 mt-4">
                <h2 class="mt-2" for="">Inserisci nome prodotto</h2>
                <input type="text w-25" placeholder="inserisci prodotto">
                <button class="btn btn-secondary mt-2" @click.prevent="InviaProdotto()">Crea prodotto</button>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import { store } from '../store';
    export default {
        name:'ShowProduct',
        data() {
        return {
           product: '',
            sections: [],
        }
    },
    methods: {

        InviaProdotto() {
            let newData = {
                product: this.product
            }
            axios.post(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.id}/menu/sections/${this.$route.params.id}/products`, newData).then((response) => {
                console.log(response.data);
                this.product = '';
                
            })
        },
    }
}
</script>

<style lang="scss" scoped>

</style>