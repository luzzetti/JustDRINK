import { createRouter, createWebHistory } from 'vue-router';

import AdminPage from './pages/AdminPage.vue';
import CreateRestaurant from './pages/CreateRestaurant.vue';
import RestaurantList from './pages/RestaurantList.vue';
import ShowRestaurant from './pages/ShowRestaurant.vue';
import MenuPage from './pages/MenuPage.vue';

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
            path: '/restaurants/:id',
            name: 'showRestaurant',
            component: ShowRestaurant
        },
        {
            path: '/restaurants/:id/menu/section',
            name: 'menu',
            component: MenuPage
        },
        // {
        //     path: '/restaurants/:id/menu/:sectionId/products',
        //     name: 'product',
        //     component: ShowProduct
        // },
  
    ]
});

export { router };