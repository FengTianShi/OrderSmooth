<template>
  <div>
    <img ref="imageRef" :src="imageSrc" alt="image" />
    <v-divider class="my-2"></v-divider>
    <v-btn
      text
      block
      :color="btnColor"
      :loading="loading"
      :disabled="loading"
      @click="cropImage">
      {{ btnName }}
    </v-btn>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.css";

// eslint-disable-next-line
const props = defineProps({
  imageSrc: {
    type: String,
    required: true,
  },
  aspectRatio: {
    type: Number,
    default: 1,
  },
  viewMode: {
    type: Number,
    default: 1,
  },
  autoCropArea: {
    type: Number,
    default: 1,
  },
  btnName: {
    type: String,
    default: "crop",
  },
  btnColor: {
    type: String,
    default: "primary",
  },
});

const imageRef = ref(null);
let cropper = null;
onMounted(() => {
  cropper = new Cropper(imageRef.value, {
    viewMode: props.viewMode,
    aspectRatio: props.aspectRatio,
    autoCropArea: props.autoCropArea,
  });
});

// eslint-disable-next-line
const emit = defineEmits(["updateImageSrc"]);
const cropImage = () => {
  const canvas = cropper.getCroppedCanvas();
  const croppedImage = canvas.toDataURL();
  emit("updateImageSrc", croppedImage);
};

onBeforeUnmount(() => {
  cropper.destroy();
});
</script>

<script>
export default {
  expose: ["load", "done"],
  data() {
    return {
      loading: false,
    };
  },
  methods: {
    load: function () {
      this.loading = true;
    },
    done: function () {
      this.loading = false;
    },
  },
};
</script>

<style scoped>
img {
  display: block;
  max-width: 100%;
  max-height: 240px;
}
</style>
