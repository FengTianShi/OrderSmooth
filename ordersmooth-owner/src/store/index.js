import { createStore } from "vuex";

const moduleX = {
  state() {
    return {
      str: "test",
    };
  },
  getters: {},
  mutations: {},
  actions: {},
};

const store = createStore({
  state() {
    return {
      count: 0,
      todos: [
        { id: 1, text: "eat", done: true },
        { id: 2, text: "sleep", done: false },
      ],
    };
  },
  getters: {
    doneTodos: (state) => {
      return state.todos.filter((todo) => todo.done);
    },
  },
  mutations: {
    increment(state, n) {
      state.count += n;
    },
  },
  actions: {
    incrementInActions(context, n) {
      // 异步操作

      // 提交
      context.commit("increment", n);
    },
  },
  modules: {
    x: moduleX,
  },
});

export default store;
