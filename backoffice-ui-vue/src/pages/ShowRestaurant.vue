<template>
    <header>
        <div class="mainContainer">
            <div class=" row d-flex justify-content-around text-center g-0">
                <!-- Prima Card-->
                <div class="contCard1 d-none d-xl-block col-xl-2">
                    <div class="card1" style="width: 100%">
                        <div class="card-body">
                            <ul class="ulMenu">
                                <li><a href="#Antipasto">Antipasto</a></li>
                                <li><a href="#Primo">Primo</a></li>
                                <li><a href="#Secondo">Secondo</a></li>
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
                </div>
                <!-- Seconda Card-->
                <div class="contCard2 col-12 px-0 col-lg-8 px-lg-2 col-xl-6">
                    <div class="card2" style="width: 100%;">
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
                            <span class="text-black mt-3">Costo del servizio: 5% dell’ordine (min 0,49 € - max 1,99
                                €)</span>
                            <router-link
                                :to="{ name: 'menu', params: { restaurantId: this.$route.params.restaurantId } }"><button
                                    class="mybtn">Vai al Menu</button></router-link>
                        </div>
                    </div>
                    <div class="menu-info mt-3 d-flex justify-content-around align-items-center">
                        <div class="cursor underline"><a class="" href="">Menu</a></div>
                        <div class="cursor underline"><a class="" href="">Info</a></div>
                    </div>

                    <div class="carosel mt-3">
                        <!-- Carosello-->


                    </div>

                    <!-- Accordion-->
                    <div class="mt-3">
                        <div class="accordion" id="accordionPanelsStayOpenExample">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true"
                                        aria-controls="panelsStayOpen-collapseOne">
                                        <div id="Antipasto">Antipasto</div>
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show"
                                    aria-labelledby="panelsStayOpen-headingOne">
                                    <div class="accordion-body">
                                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Hic cum natus ullam quo
                                        soluta maxime quisquam delectus repellendus minima praesentium. Et excepturi odio
                                        delectus magni! Excepturi ducimus repellat dolores laborum minima dicta quo quaerat
                                        quibusdam esse, fuga, consequuntur debitis dolorum autem tempore. Mollitia ducimus
                                        illum porro, debitis doloremque maxime optio iure nisi soluta esse fugit nesciunt
                                        ipsam eveniet impedit rerum voluptates nihil quo natus quaerat doloribus consequatur
                                        id harum velit nulla? Blanditiis, tempore cupiditate architecto minus minima ab
                                        distinctio fuga, animi quae aperiam, adipisci similique. Harum aliquid, id
                                        blanditiis nobis alias placeat distinctio! Dicta iure deserunt quis laudantium
                                        molestias fugit.
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false"
                                        aria-controls="panelsStayOpen-collapseTwo">
                                        <div id="Primo">Primo</div>
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse"
                                    aria-labelledby="panelsStayOpen-headingTwo">
                                    <div class="accordion-body">
                                        Lorem ipsum dolor, sit amet consectetur adipisicing elit. Aperiam illum eveniet ut
                                        sint quis in nihil minus, placeat voluptas perspiciatis harum ex modi incidunt,
                                        ipsum at? Vitae laboriosam animi, nulla accusamus beatae dolores quisquam nobis
                                        minima alias earum quaerat? Ducimus quis, totam tempora veniam libero veritatis et
                                        non iure officiis voluptate iusto porro corrupti dolores rem consequuntur
                                        asperiores. Fugiat eum velit eaque voluptas repudiandae! Ipsum nulla eaque
                                        voluptatum dolor autem totam, repellendus commodi a quo omnis, non dignissimos
                                        harum? Doloremque repellat minima perferendis alias culpa quia in ducimus labore
                                        veniam ex, praesentium a quos assumenda dolor molestiae, accusamus sed? Quod?
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="panelsStayOpen-headingThree">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false"
                                        aria-controls="panelsStayOpen-collapseThree">
                                        <div id="Secondo">Secondo</div>
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse"
                                    aria-labelledby="panelsStayOpen-headingThree">
                                    <div class="accordion-body">
                                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Quibusdam tempora odit
                                        impedit sed, sit eos. Pariatur adipisci eaque suscipit amet fuga? Odit voluptates ab
                                        officia debitis dolorem illum, magnam pariatur ea necessitatibus nostrum officiis
                                        distinctio mollitia dolore perspiciatis iusto culpa impedit nesciunt? Labore
                                        expedita perferendis est libero beatae quo deleniti nemo numquam aperiam! Suscipit
                                        nobis ipsam autem necessitatibus aspernatur, aliquam eius quibusdam delectus
                                        sapiente ab modi adipisci! Ratione saepe officia placeat. Corrupti, tempora quaerat
                                        deleniti aperiam eveniet, sunt a repudiandae eaque dolor, quae excepturi nihil
                                        numquam ullam ab labore earum accusamus consequatur perferendis impedit? Animi
                                        corporis sit repellat excepturi modi?
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Terza Card-->
                <div class="contCard3 d-none d-lg-block col-lg-4 px-lg-2 col-xl-4">
                    <div class="card3" style="width: 100%;">
                        <div class="card-body">
                            <h3 class="mycard-title ms-2 mt-2"><b>Il tuo ordine</b></h3>
                            <hr>
                            <div class="d-flex align-items-center">
                                <div class="cerchio"><i class="fa-regular fa-exclamation"></i></div> <span
                                    class="allergeni"><u>Info
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
        </div>
    </header>
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
    padding-top: 600px;
}

.card1 {

    background-color: rgb(255, 255, 255);
    border-left: 2px solid #dbd9d7;
    margin-top: 50px;

}

.card2 {
    position: relative;
    margin-top: -100px;
    background-color: rgb(255, 255, 255);
    -webkit-box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.14);
    box-shadow: 0px 0px 10px 8px rgba(0, 0, 0, 0.08);
    border-radius: 12px;
    height: 400px;
}

.card3 {
    height: 300px;
    margin-top: -100px;
    background-color: rgb(255, 255, 255);
    border-radius: 5%;
    -webkit-box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.14);
    box-shadow: 0px 0px 10px 8px rgba(0, 0, 0, 0.08);
    padding: 20px;
}

.card-body {
    display: flex;
    flex-direction: column;
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

.menu-info {
    width: 100%;
    height: 50px;
    background-color: white;
    border-radius: 20px;
    border: 1px solid #efedea;
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
        box-shadow: -1px 0px 0px 0px #000000;
        outline: none;

    }
}

.underline {
    height: 100%;
    width: 60px;
    display: flex;
    justify-content: center;
    align-items: center;

    &:hover {
        outline: none;
        box-shadow: 0px 3px 0px 0px #F36805;

    }
}

.carosel{
    width: 100%;
    height: 140px;
    background-color: #EBF6FA;
    border-radius: 20px;
}
</style>