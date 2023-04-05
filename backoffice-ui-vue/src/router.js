import { createRouter, createWebHistory } from 'vue-router';

import AdminPage from './pages/AdminPage.vue';
import CreateRestaurant from './pages/CreateRestaurant.vue';
import RestaurantList from './pages/RestaurantList.vue';
import ShowRestaurant from './pages/ShowRestaurant.vue';
import MenuPage from './pages/MenuPage.vue';
import ShowProduct from './pages/ShowProduct.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'admin',
            component: AdminPage
        },
        {
            path: '/create-restaurant',
            name: 'createRestaurant',
            component: CreateRestaurant
        },
        {
            path: '/restaurants',
            name: 'restaurantList',
            component: RestaurantList
        },
        {
            path: '/restaurants/:restaurantId',
            name: 'showRestaurant',
            component: ShowRestaurant
        },
        {
            path: '/restaurants/:restaurantId/menu/sections',
            name: 'menu',
            component: MenuPage
        },
        {
            path: '/restaurants/:restaurantId/menu/sections/:sectionId/products',
            name: 'product',
            component: ShowProduct
        },
  
    ]
});

export { router };