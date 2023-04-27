<template>
    <div>
        <div class="pt-3">
            <carousel :itemsToShow="2" :wrapAround="true" :transition="1000" :autoplay="0">
                <slide v-for="(slide, index) in paths" :key="index">
                    <div class="mycard-contenitore">
                        <div class="mycontainerR">
                            <div class="imgContainer">
                                <img :src="slide.path" :alt="slide.alt">
                            </div>

                        </div>
                        <div class="mycontainerS">
                            <span class="tipologia-prodotto">Panino</span>
                            <div class="titolo-prodotto">Mc Christian</div>
                            <div class="ricercatezza"> Amabile</div>
                        </div>
                    </div>
                </slide>
            </carousel>
            <!-- <button class="prev" @click="prevSlide">&#8249;</button>
            <button class="next" @click="nextSlide">&#8250;</button> -->
        </div>
    </div>
</template>

<script>

import 'vue3-carousel/dist/carousel.css'
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel';


export default {
    name: 'CarouoselComponent',
    components: {
        Carousel,
        Slide,
        Pagination,
        Navigation,
    },
    data: function () {
        return {

            paths: [
                {
                    path: '/img/JustDrink.png',
                    alt: 'justlogo',
                    info: 'bho',
                    id: 1,
                },
                {
                    path: '/img/logo-McDonalds.jpg',
                    alt: 'Mc',
                    info: 'bho2',
                    id: 2,
                },
                {
                    path: '/img/McDonaldsLogo.svg.png',
                    alt: 'Mc2',
                    info: 'bho3',
                    id: 3,
                },
                {
                    path: '/img/sfondoProva.webp',
                    alt: 'sfondo',
                    info: 'bho4',
                    id: 4,
                },

            ],
        
        }
    },
    methods: {
    
    }

}
</script>

<style lang="scss" scoped>
@use '../assets/styles/partials/variables' as *;

h2 {
    font-size: 30px;
    font-weight: bold;
    color: red;
}

.carousel__slide {
    padding: 5px;
}

.carousel__viewport {
    perspective: 2000px;
}

.carousel__track {
    transform-style: preserve-3d;
}

.carousel__slide--sliding {
    transition: 0.5s;
}

.carousel__slide {
    opacity: 0.9;
    transform: rotateY(-20deg) scale(0.9);
}

.carousel__slide--active~.carousel__slide {
    transform: rotateY(20deg) scale(0.9);
}

.carousel__slide--prev {
    opacity: 1;
    transform: rotateY(-10deg) scale(0.95);
}

.carousel__slide--next {
    opacity: 1;
    transform: rotateY(10deg) scale(0.95);
}

.carousel__slide--active {
    opacity: 1;
    transform: rotateY(0) scale(1.1);
}

.carousel__prev,
.carousel__next {
    box-sizing: content-box;
    border: 5px solid white;
}

.mycard-contenitore {
    position: relative;
    display: flex;
    align-items: center;
    background-color: white;
    border-radius: 10px;
    width: 300px;
    height: 100px;

}

.mycontainerR {
    position: relative;
    width: 40%;
    height: 100%;
    border-radius: 10px;
}

.imgContainer {
    position: absolute;
    top: 10px;
    left: 9px;
    background-color: rgb(0, 255, 115);
    border-radius: 5px;
    height: 80%;
    width: 85%;
}

.mycontainerS {
    display: flex;
    flex-direction: column;
    text-align: start;
    width: 60%;
    height: 100%;
    border-radius: 10px;
    padding: 10px;
}

.tipologia-prodotto {
    font-size: 10px;
    margin-top: 5px;
}

.titolo-prodotto {
    font-size: 18px;
}

.ricercatezza {
    font-size: 10px;
    margin-top: 5px;
}

img {
    width: 100%;
    height: 100%;
    border-radius: 5px;
}
</style>