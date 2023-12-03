<template>
  <div class="text-center">
    <v-avatar :image="logoSrc" :size="logoSize" />
    <v-btn class="ma-4" color="primary" text @click="selectImageSrc">
      Upload Image
    </v-btn>

    <div
      v-if="showUploadError"
      class="text-center text-error"
      style="font-size: 12px">
      {{ uploadError }}
    </div>

    <input
      type="file"
      ref="fileInput"
      accept="image/*"
      style="display: none"
      @change="UploadImageSrc" />
  </div>

  <v-dialog v-model="cropper" max-width="500">
    <v-card class="pa-4">
      <v-row>
        <v-col cols="10">
          <p>Crop your new profile picture</p>
        </v-col>
        <v-col cols="2" class="text-right">
          <v-btn
            icon="mdi-close"
            variant="text"
            size="x-small"
            @click="cropper = false" />
        </v-col>
      </v-row>

      <v-divider class="my-2"></v-divider>

      <cropper-img
        btnName="Upload"
        btnColor="deep-purple-accent-4"
        :imageSrc="imageSrc"
        :aspectRatio="aspectRatio"
        @updateImageSrc="updateImageSrc"
        class="cropper-img-container" />
    </v-card>
  </v-dialog>
</template>

<script>
import CropperImg from "../components/common/CropperImage.vue";

export default {
  components: { CropperImg },
  props: {
    logoSrc: {
      type: String,
    },
    logoSize: {
      type: Number,
      default: 120,
    },
  },
  data: () => ({
    showUploadError: false,
    uploadError: "",
    cropper: false,
    aspectRatio: 1,
    imageSrc: "",
    imageNew: "",
  }),
  methods: {
    selectImageSrc() {
      this.$refs.fileInput.click();
    },
    UploadImageSrc(e) {
      const img = e.target.files[0];
      if (!img) {
        this.showUploadError = true;
        this.uploadError = "Please select an image";
        return;
      }
      if (img.size > 1024 * 1024 * 16) {
        this.showUploadError = true;
        this.uploadError = "Image size must be less than 2MB";
        return;
      }

      this.imageSrc = URL.createObjectURL(img);

      this.showUploadError = false;
      this.cropper = true;
    },
    updateImageSrc(updateImageSrc) {
      this.imageNew = updateImageSrc;
    },
    resetFileInput() {
      this.$refs.fileInput.value = "";
    },
  },
  mounted() {},
  created() {},
};
</script>
