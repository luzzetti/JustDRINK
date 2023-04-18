
<template>
  <HeaderComponent></HeaderComponent>

  <div class="container-fluid">
    <div class="row">
      <Sidebar></Sidebar>
      <main class="col-md-9 ms-sm-auto col-lg-10 px-0">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>


<script>

import HeaderComponent from './components/HeaderComponent.vue';
import Sidebar from './components/Sidebar.vue';
export default {
  name: "App",
  components: { HeaderComponent, Sidebar }
}
</script>

<style lang="scss" scoped></style>