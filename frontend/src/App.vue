<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view/>
</template>

<style>
#app {
  font-family: Georgia, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: purple;
  background: ;
}

nav {
  padding: 50px;
}

nav a {
  font-weight: bold;
  color: green;
}

nav a.router-link-exact-active {
  color: pink;
}
</style>
