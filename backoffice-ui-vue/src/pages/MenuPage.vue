<template>
    <div>
        <h1>Aggiungi Sezioni </h1>
        <form action="" method="POST">
            <div class="d-flex flex-column w-25 mt-4">
                <h2 class="mt-2" for="">Inserisci nome sezione</h2>
                <input type="text w-25" v-model="title" placeholder="inserisci titolo">
                <button class="btn btn-secondary mt-2" @click.prevent="InviaSezione()">Crea sezione</button>
            </div>
        </form>
    </div>
    <h2></h2>
    <div>
        <h1 class="mt-2">Tabella delle section</h1>

        <table class="table">
            <thead>
                <tr>
                    <th scope="col">name</th>
                    <th scope="col">Id Section</th>
                    <th scope="col">delete</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in sections" :key="index">
                    <td class="text-capitalize">{{ item.title }}</td>
                    <td class="text-capitalize"><router-link :to="{name:'product',params:{restaurantId:this.$route.params.restaurantId,sectionId:item.id}}">
                         {{item.id}}</router-link>
                    
                   </td>
                    <td>
                        <form action="" method="DELETE">

                        </form><i class="fa-solid fa-trash" @click="deleteSectionById(item.id)"></i>
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
    name: 'MenuPage',
    data() {
        return {
            title: '',
            sections: [],
        }
    },
    methods: {

        InviaSezione() {
            let newData = {
                title: this.title
            }
            axios.post(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}/menu/sections`, newData).then((response) => {
                console.log(response.data);
                this.title = '';
                this.getSection();
            })
        },
        getSection() {
            axios.get(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}/menu/sections`).then((response) => {
                console.log(response.data);
                this.sections = response.data;
                
            })
        },
        deleteSectionById(id) {
            axios.delete(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}/menu/sections/${id}`).then((response) => {
                location.reload();
            })
        }
    },
    mounted() {
        this.getSection()
        
    }

}
</script>

<style lang="scss" scoped>
.fa-solid {
    color: red;
}
</style>