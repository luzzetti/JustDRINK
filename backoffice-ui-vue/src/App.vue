<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Test from './components/test.vue';

</script>

<template>
  <RouterView />
  <test></test>

</template>

<script>
export default {
  components: { Test }
}
</script>

<style lang="scss" scoped></style>