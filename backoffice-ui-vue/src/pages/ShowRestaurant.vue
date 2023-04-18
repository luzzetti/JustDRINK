<template>
    <header>

    </header>
    <div class="mainContainer">
        <div class="d-flex justify-content-around text-center">

            <div class="card1" style="width: 10rem;">
                <div class="card-body">
                    <ul class="ulMenu">
                        <li><a href="">Antipasto</a></li>
                        <li><a href="">Primo</a></li>
                        <li><a href="">Secondo</a></li>
                        <li><a href="">Pizze</a></li>
                        <li><a href="">Fritti</a></li>
                        <li><a href="">Bibite</a></li>
                        <li><a href="">Snack</a></li>
                        <li><a href="">Contorni</a></li>
                        <li><a href="">Dolci</a></li>
                        <li><a href="">Vini</a></li>
                        <li><a href="">Amari</a></li>
                        <li><a href="">Caffè</a></li>
                    </ul>
                </div>
            </div>


            <div class="card2" style="width: 40rem;">
                <div class="card-body">
                    <div class="logo-restaurant">
                    </div>
                    <h2 class="card-title mt-5">{{ restaurant.name }} - Viterbo</h2>
                    <p class="card-text mt-3">Pollo &middot; Fritti</p>
                    <div class="cursor">
                        <span><i class="fa-solid fa-star"></i><i class="fa-solid fa-star"></i><i
                                class="fa-solid fa-star"></i><i class="fa-solid fa-star"></i><i
                                class="fa-solid fa-star"></i> <span class="text-black"><u>205
                                    recensioni</u></span></span>
                    </div>
                    <br>
                    <span class="text-black">Via Guglielmo Marconi 15, Viterbo, 01100</span>
                    <div class="consegna-ritiro mt-3">
                        <div class="consegna-ritiro-elemento">
                            <p class="consegna">Consegna</p>
                            <p class="v-ritirare"><u>Voglio ritirare</u></p>
                        </div>
                        <div class="consegna-ritiro-elemento">
                            <p class="consegna">Bho</p>
                            <p class="v-ritirare"><u>Bho2</u></p>
                        </div>
                    </div>
                    <span class="text-black mt-3">Costo del servizio: 5% dell’ordine (min 0,49 € - max 1,99 €)</span>
                    <router-link :to="{ name: 'menu', params: { restaurantId: this.$route.params.restaurantId } }"><button
                            class="mybtn">Vai al Menu</button></router-link>
                </div>
            </div>

            <div class="card3" style="width: 24rem;">
                <div class="card-body">
                    <h3 class="mycard-title ms-2 mt-2"><b>Il tuo ordine</b></h3>
                    <hr>
                    <div class="d-flex align-items-center">
                        <div class="cerchio"><i class="fa-regular fa-exclamation"></i></div> <span class="allergeni"><u>Info
                                sugli allergeni</u></span>
                    </div>
                    <div class="pagamento mt-3">
                        Vai al pagamento
                    </div>
                    <br>
                    <div class="ordine-tipo d-flex justify-content-around">
                        <button class="btnConsegna">Consegna</button> <button class="btnConsegna">Asporto</button>
                    </div>
                </div>
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
            axios.get(`${store.apiBaseUrl}/api/1.0/restaurants/${this.$route.params.restaurantId}`).then((response) => {
                console.log(response.data);
                this.restaurant = response.data;

                // console.log(this.restaurant);
            })
                .catch(function (error) {
                    console.log('C\'è stato un errore' + error);
                })
        },
    },
    mounted() {
        this.infoRestaurant()
    }
}
</script>

<style lang="scss" scoped>
header {
    position: relative;
    background-image: url("/img/mcSfondo.webp");
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    width: 100%;
    height: 600px;
}

.mainContainer {
    margin: 0 auto;
    max-width: 1300px;

}

.card1 {
    z-index: 1;
    background-color: rgb(255, 255, 255);
    border-left: 2px solid #dbd9d7;
    margin-top: 50px;
}

.card2 {
    display: flex;
    justify-content: center;
    margin-top: -100px;
    z-index: 1;
    background-color: rgb(255, 255, 255);
    -webkit-box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.14);
    box-shadow: 0px 0px 10px 8px rgba(0, 0, 0, 0.08);
    border-radius: 12px;
    height: 400px;
}

.card3 {
    height: 300px;
    margin-top: -100px;
    z-index: 1;
    background-color: rgb(255, 255, 255);
    border-radius: 5%;
    -webkit-box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.14);
    box-shadow: 0px 0px 10px 8px rgba(0, 0, 0, 0.08);
    padding: 20px;
}

.card-body {
    display: flex;
    flex-direction: column;
    position: relative;
}

.logo-restaurant {
    position: absolute;
    align-self: center;
    top: -30px;
    width: 60px;
    height: 60px;
    border-radius: 5px;
    background-image: url('/img/logo-McDonalds.jpg');
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    border: 1px solid #dbd9d7;
}

.cursor {
    cursor: pointer;
}

.card-title {
    text-transform: capitalize;
    text-align: center;
}

.mycard-title {
    text-align: start;
}

.cerchio {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border-color: black;
    border: 1px solid;
}

.consegna-ritiro-elemento {
    display: flex;
    justify-content: space-between;
}

.consegna-ritiro {
    display: flex;
    flex-direction: column;
    background-color: #F5F3F1;
    border-radius: 10px;
    width: 60%;
    height: 100px;
    margin: 0 auto;
}

.fa-exclamation {
    transform: translate(12%, -18%);

}

.consegna {
    padding-left: 12px;
    margin-top: 10px;
    font-weight: 600;
}

.v-ritirare {
    padding-right: 12px;
    margin-top: 10px;
    font-weight: 600;
    font-size: 14px;
}

.allergeni {
    text-transform: capitalize;
    text-align: start;
    color: black;
    padding-left: 10px;
}

.pagamento {
    background-color: #EFEDEA;
    width: 100%;
    height: 50px;
    border-radius: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 800;
    color: #8C999B;
}


.ordine-tipo {
    background-color: #EFEDEA;
    width: 100%;
    height: 50px;
    border-radius: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 800;
    color: #8C999B;
}

.btnConsegna {
    border-radius: 20px;
    background-color: white;
    color: #8C999B;
    border-color: #dbd9d7;
    border: 1px solid;
    width: 150px;
    height: 40px;
}

.mybtn {
    border-color: #F5F3F1;
    border-radius: 5px;
}

ul {
    list-style-type: none;
    padding: 0;
}

a {
    font-size: 16px;
    text-decoration: none;
    color: black;
}

span {
    color: orange;
}

li {
    text-align: start;
    padding-top: 10px;
    padding-left: 10px;

    &:hover {
        //border-left: 2px solid black;
        box-shadow: -1px 0px 0px 0px #000000;
        outline: none;
        
    }
}

@media screen and (max-width: 1340px) {

    .card1 {
        display: none;
    }

}

@media screen and (max-width: 1140px) {

    .card3 {
        display: none;
    }

}
</style>