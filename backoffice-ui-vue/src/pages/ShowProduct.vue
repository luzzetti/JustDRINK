<template>
   <h1>Aggiungi Prodotti</h1> 

    <div>
        <form action="" method="POST">
            <div class="d-flex flex-column w-25 mt-4">
                <h2 class="mt-2" for="">Inserisci nome prodotto</h2>
                <input type="text w-25" placeholder="inserisci prodotto">
                <button class="btn btn-secondary mt-2" @click.prevent="">Crea sezione</button>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        name:'ShowProduct'
        
    }
</script>

<style lang="scss" scoped>

</style>