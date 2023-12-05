<template>
  <div class="text-center">
    <v-avatar v-if="logoSrc" :image="logoSrc" :size="logoSize" />
    <v-avatar v-if="!logoSrc" color="surface-variant" :size="logoSize" />

    <v-btn
      class="ma-4"
      color="primary"
      variant="tonal"
      prepend-icon="mdi-image"
      @click="selectImage">
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
      @change="cropperImage" />
  </div>

  <v-dialog persistent v-model="cropper" max-width="500">
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
            @click="(cropper = false), resetFileInput()" />
        </v-col>
      </v-row>

      <v-divider class="my-2"></v-divider>

      <cropper-img
        ref="cropperImage"
        btnName="Upload"
        btnColor="deep-purple-accent-4"
        :imageSrc="imageSrc"
        :aspectRatio="aspectRatio"
        @updateImageSrc="updateLogo" />
    </v-card>
  </v-dialog>
</template>

<script>
import CropperImg from "./common/CropperImage.vue";

export default {
  components: { CropperImg },
  props: {
    restaurantId: {
      required: true,
    },
    logoSize: {
      type: Number,
      default: 120,
    },
  },
  data: () => ({
    logoSrc: "",

    uploadError: "",
    showUploadError: false,

    cropper: false,

    imageSrc: "",
    imageName: "",
    aspectRatio: 1,
  }),
  methods: {
    selectImage() {
      this.$refs.fileInput.click();
    },
    cropperImage(e) {
      const img = e.target.files[0];
      if (!img) {
        this.showUploadError = true;
        this.uploadError = "Please select an image";
        return;
      }
      if (img.size > 1024 * 1024 * 8) {
        this.showUploadError = true;
        this.uploadError = "Image size must be less than 8MB";
        return;
      }
      this.showUploadError = false;

      this.imgName = img.name;
      this.imageSrc = URL.createObjectURL(img);

      this.cropper = true;
      this.resetFileInput();
    },
    async updateLogo(updateImageSrc) {
      this.$refs.cropperImage.load();

      var formData = new FormData();
      let bf = await fetch(updateImageSrc).then((r) => r.blob());
      formData.append("restaurantLogo", bf, this.imgName);

      await this.$http
        .put(`restaurant/logo/${this.restaurantId}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${JSON.parse(
              window.localStorage.getItem("owner-token")
            )}`,
          },
        })
        .then((response) => {
          if (response.status == 200) {
            this.logoSrc = response.data;
          }
        })
        .catch((error) => {
          if (error.response.status === 401) {
            this.$router.push("/Signin");
          }
        });

      this.$refs.cropperImage.done();
      this.cropper = false;
      this.resetFileInput();
    },
    resetFileInput() {
      this.$refs.fileInput.value = "";
    },
  },
  async created() {
    await this.$http
      .get(`/restaurant/logo/${this.restaurantId}`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.logoSrc = response.data;
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });
  },
};
</script>
