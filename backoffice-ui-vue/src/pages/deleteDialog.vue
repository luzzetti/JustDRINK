<template>
    <div class="delete-dialog">
      <h3>Sei sicuro di voler eliminare?</h3>
      <div class="delete-buttons">
        <button @click="deleteItem">Elimina</button>
        <button @click="cancelDelete">Annulla</button>
      </div>
    </div>
  </template>

<script>
    export default {
        name:'deleteDialog',
        data(){
            return{
                
            }
        }
    }
</script>

<style lang="scss" scoped>
 .delete-dialog {
      background-color: #fff;
      border: 1px solid #ccc;
      padding: 20px;
    }
    .delete-buttons {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
</style>