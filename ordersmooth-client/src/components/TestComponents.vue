<template>
  <div class="test">
    <h1>{{ msg }}</h1>
    <p>this is a test!!</p>
  </div>
</template>

<script>
export default {
  name: 'TestComponents',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
