<template>
  <div id="app">
      <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<style>
#app {
  margin: 0 auto;
}
</style>
