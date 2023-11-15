<template>
  test1:{{ this.$store.state.count }}
  <br />
  test2:{{ count }}
  <br />
  <v-btn @click="plus">puls</v-btn>
  <br />
  <v-btn @click="increment(4)">puls by mapped increment</v-btn>
  <br />
  <v-btn @click="plusByActions(-1)">puls by Actions</v-btn>
  <br />
  <v-btn @click="incrementInActions(-2)">puls by Actions</v-btn>
  <br />
  <v-list lines="one">
    <v-list-item
      v-for="todo in todos"
      :key="todo.id"
      :title="'Item ' + todo.text">
      {{ todo.id }}
      {{ todo.text }}
      {{ todo.done }}
    </v-list-item>
  </v-list>
  <br />
  <v-list lines="one">
    <v-list-item
      v-for="todo in doneTodos"
      :key="todo.id"
      :title="'Item ' + todo.text">
      {{ todo.id }}
      {{ todo.text }}
      {{ todo.done }}
    </v-list-item>
  </v-list>
  <br />
  test module:{{ this.$store.state.x.str }}
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";

export default {
  data: () => ({}),
  methods: {
    plus() {
      console.log("plus!");
      this.$store.commit("increment", 3);
    },
    plusByActions(n) {
      this.$store.dispatch("incrementInActions", n);
    },
    ...mapMutations(["increment"]),
    ...mapActions(["incrementInActions"]),
  },
  computed: {
    ...mapState([
      // 映射 this.count 为 store.state.count
      "count",
      "todos",
    ]),
    ...mapGetters([
      // 映射 this.count 为 store.state.count
      "doneTodos",
    ]),
  },
  // computed: {
  //     count() {
  //         return this.$store.state.count
  //     },
  // }
};
</script>
