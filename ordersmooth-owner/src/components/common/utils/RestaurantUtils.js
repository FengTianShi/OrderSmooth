export const restaurantUtils = {
  methods: {
    async updateRestaurant(restaurant) {
      try {
        const response = await this.$http.put(
          `/restaurant/${restaurant.restaurantId}`,
          {
            genreId: restaurant.genreId,
            restaurantTel: restaurant.restaurantTel,
            restaurantPostalCode: restaurant.restaurantPostalCode,
            restaurantLatitude: restaurant.restaurantLatitude,
            restaurantLongitude: restaurant.restaurantLongitude,
            restaurantServiceDistance: restaurant.restaurantServiceDistance,
            isLimitServiceDistance: restaurant.isLimitServiceDistance,
            currencyId: restaurant.currencyId,
            defaultServiceFee: restaurant.defaultServiceFee,
            defaultTax: restaurant.defaultTax,
            isDisplayServiceFee: restaurant.isDisplayServiceFee,
            isDisplayTax: restaurant.isDisplayTax,
            wifiSsid: restaurant.wifiSsid,
            wifiPassword: restaurant.wifiPassword,
            payMethodIds: restaurant.payMethods.map((item) => item.payMethodId),
            restaurantI18ns: restaurant.i18ns.map((item) => {
              return {
                langCode: item.langCode,
                restaurantName: item.restaurantName,
                restaurantAddress: item.restaurantAddress,
                restaurantDescription: item.restaurantDescription,
              };
            }),
            restaurantOpeningHours: await this.convert(restaurant),
          },
          {
            headers: {
              Authorization: `Bearer ${JSON.parse(
                window.localStorage.getItem("owner-token")
              )}`,
            },
          }
        );
        return response;
      } catch (error) {
        return error;
      }
    },
    async convert(restaurant) {
      let restaurantOpeningHours = [];
      restaurant.openingHours.map((item) => {
        let index = restaurantOpeningHours.findIndex(
          (x) => x.dayInWeekId === item.dayInWeekId
        );
        if (index === -1) {
          restaurantOpeningHours.push({
            dayInWeekId: item.dayInWeekId,
            dayInWeekOpeningHours: [
              {
                openTime: item.openTime.slice(0, 5),
                closeTime: item.closeTime.slice(0, 5),
              },
            ],
          });
        } else {
          restaurantOpeningHours[index].dayInWeekOpeningHours.push({
            openTime: item.openTime.slice(0, 5),
            closeTime: item.closeTime.slice(0, 5),
          });
        }
      });
      return restaurantOpeningHours;
    },
  },
};
