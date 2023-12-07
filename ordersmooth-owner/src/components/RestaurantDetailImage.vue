<template>
  <h2 class="text-h4 font-weight-black text-primary">
    {{ $t("createRestaurant.title") }}
  </h2>
  <v-divider class="my-6"></v-divider>

  <v-btn
    block
    class="mb-4"
    color="primary"
    variant="tonal"
    prepend-icon="mdi-image"
    @click="selectImage">
    Upload Image
  </v-btn>

  <div
    v-if="showUploadError"
    class="text-center text-error mb-4"
    style="font-size: 12px">
    {{ uploadError }}
  </div>

  <input
    type="file"
    ref="fileInput"
    accept="image/*"
    style="display: none"
    @change="cropperImage" />

  <v-item-group multiple>
    <v-item v-for="item in restaurantImage" :key="item.seq">
      <v-img
        cover
        class="bg-grey-lighten-2 text-right pa-2 mb-4"
        :src="item.imageAddress"
        :lazy-src="item.imageAddress"
        :aspect-ratio="this.aspectRatio">
        <v-btn
          icon="mdi-close"
          @click="deleteRestaurantImageConfirm(), (selectedSeq = item.seq)" />
        <template v-slot:placeholder>
          <v-row
            class="fill-height ma-0"
            style="align-items: center; justify-content: center">
            <v-progress-circular indeterminate color="grey-lighten-5">
            </v-progress-circular>
          </v-row>
        </template>
      </v-img>
    </v-item>
  </v-item-group>

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
        @updateImageSrc="updateImage" />
    </v-card>
  </v-dialog>

  <v-dialog persistent v-model="deleteConfirm" max-width="500">
    <v-card class="pa-4">
      <v-row>
        <v-col cols="10">
          <p>Confirm</p>
        </v-col>
        <v-col cols="2" class="text-right">
          <v-btn
            icon="mdi-close"
            variant="text"
            size="x-small"
            @click="deleteConfirm = false" />
        </v-col>
      </v-row>

      <v-divider class="my-2"></v-divider>

      <p class="py-4 text-center">
        <v-icon color="warning" size="28">mdi-alert-circle</v-icon>
        Confirm your delete
      </p>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" @click="deleteConfirm = false"> Cancel </v-btn>
        <v-btn
          color="error"
          :loading="loading"
          :disabled="loading"
          @click="deleteRestaurantImage">
          Delete
        </v-btn>
      </v-card-actions>
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
  },
  data: () => ({
    restaurantImage: [],
    imageMaxNumber: 0,

    uploadError: "",
    showUploadError: false,

    cropper: false,
    imageSrc: "",
    imageName: "",
    aspectRatio: 16 / 9,

    selectedSeq: "",
    deleteConfirm: false,

    loading: false,
  }),
  methods: {
    selectImage() {
      if (this.restaurantImage.length >= this.imageMaxNumber) {
        this.showUploadError = true;
        this.uploadError = `You can upload up to ${this.imageMaxNumber} images`;
        return;
      }
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
    async updateImage(updateImageSrc) {
      this.$refs.cropperImage.load();

      var formData = new FormData();
      let bf = await fetch(updateImageSrc).then((r) => r.blob());
      formData.append("restaurantImage", bf, this.imgName);

      await this.$http
        .post(`/restaurant/${this.restaurantId}/image`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${JSON.parse(
              window.localStorage.getItem("owner-token")
            )}`,
          },
        })
        .then((response) => {
          if (response.status == 201) {
            this.getRestaurantImage();
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
    deleteRestaurantImageConfirm() {
      this.deleteConfirm = true;
    },
    async deleteRestaurantImage() {
      this.loading = true;

      await this.$http
        .delete(`/restaurant/${this.restaurantId}/image/${this.selectedSeq}`, {
          headers: {
            Authorization: `Bearer ${JSON.parse(
              window.localStorage.getItem("owner-token")
            )}`,
          },
        })
        .then((response) => {
          if (response.status === 204) {
            this.getRestaurantImage();
          }
        })
        .catch((error) => {
          if (error.response.status === 401) {
            this.$router.push("/Signin");
          }
        });

      this.loading = false;
      this.deleteConfirm = false;
    },
    resetFileInput() {
      this.$refs.fileInput.value = "";
    },
    async getRestaurantImage() {
      await this.$http
        .get(`/restaurant/${this.restaurantId}/image`, {
          headers: {
            Authorization: `Bearer ${JSON.parse(
              window.localStorage.getItem("owner-token")
            )}`,
          },
        })
        .then((response) => {
          if (response.status === 200) {
            this.restaurantImage = response.data;
          }
        })
        .catch((error) => {
          if (error.response.status === 401) {
            this.$router.push("/Signin");
          }
        });

      this.restaurantImage.sort((a, b) => b.seq - a.seq);
    },
  },
  async created() {
    await this.$http
      .get(`/restaurant/${this.restaurantId}/image/max-number`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.imageMaxNumber = response.data;
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

    await this.getRestaurantImage();
  },
};
</script>
