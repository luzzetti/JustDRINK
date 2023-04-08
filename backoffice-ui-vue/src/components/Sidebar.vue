<template>
    <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-body-tertiary sidebar collapse ">
        <div class="position-sticky pt-3 sidebar-sticky">
            <ul class="nav flex-column" >
                <li class="nav-item" v-for="(item, index) in menuPage" :key="index">
                    <router-link :to="{name:item.routeName}">
                        {{item.info}}
                    </router-link>
                </li>
            </ul>

        </div>
    </nav>
</template>

<script>
export default {
    name: 'Sidebar',
    data() {
        return {
            menuPage: [
                {
                    routeName: 'admin',
                    info: 'Admin',
                    id: 1,
                },
                {
                    routeName: 'createRestaurant',
                    info: 'Creazione ristorante',
                    id: 2,
                },
                {
                    routeName: 'restaurantList',
                    info: 'Restaurants',
                    id: 3,
                },
                // {
                //     routeName: 'produtcs',
                //     info: 'Products',
                //     id: 4,
                // },
            ]
        }
    }
}
</script>

<style lang="scss" scoped>
.sidebar{
    border-right:1px solid black;
    height: 95vh;
}
</style>