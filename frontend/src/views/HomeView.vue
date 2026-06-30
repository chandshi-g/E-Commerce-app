<template>
  <div id="home">

    <!-- Hero Section -->
    <div id="background-div" class="page-holder bg-cover">
      <div class="container py-5">
        <header class="text-left text-white py-5">
          <h3 class="mb-4 rounded">
            <a
              href="#start-shopping"
              class="bg-white px-2 py-2 rounded"
              id="heading">
              Start Shopping
            </a>
          </h3>
        </header>
      </div>
    </div>

    <!-- Categories -->
    <div id="start-shopping" class="container mt-5">
      <div class="row">
        <div class="col-12">
          <h2>Top Categories</h2>
        </div>
      </div>

      <div class="row">
        <div
          class="col-md-6 col-xl-4 col-12 pt-3 d-flex justify-content-around"
          v-for="category in categories.slice(0,6)"
          :key="category.id">

          <CategoryBox :category="category" />

        </div>
      </div>
    </div>

    <hr>

    <!-- Products -->
    <div class="container">
      <div class="row">
        <div class="col-12">
          <h2>Top Products</h2>
        </div>
      </div>

      <div class="row">
        <div
          class="col-md-6 col-xl-4 col-12 pt-3 d-flex justify-content-around"
          v-for="product in products.slice(0,8)"
          :key="product.id">

          <ProductBox :product="product" />

        </div>
      </div>
    </div>

  </div>
</template>

<script>
import CategoryBox from "../components/Category/CategoryBox.vue";
import ProductBox from "../components/Product/ProductBox.vue";

export default {
  name: "HomeView",

  components: {
    CategoryBox,
    ProductBox
  },

  props: {
    baseURL: String,
    products: Array,
    categories: Array
  }
}
</script>

<style scoped>
.page-holder {
  min-height: 100vh;
}

.bg-cover {
  background-size: cover !important;
}

#background-div {
  background: url("../assets/home.png") center center no-repeat;
  background-size: cover;
}

#heading {
  text-decoration: none;
  font-family: "Josefin Sans", sans-serif;
  color: black;
}

#content {
  opacity: 0.9;
}

h2 {
  font-family: "Josefin Sans", sans-serif;
}
</style>