<template>
  <v-card class="fill-height" fluid>
    <v-banner app color="green" dark>
      <div class="d-flex align-center">
        <v-title class="text-h1">
          <v-icon large left> mdi-airplane </v-icon>
          Aircraft Tester
        </v-title>
      </div>
    </v-banner>

    <v-banner app color="green" dark>
      <div class="d-flex align-center">
        <v-title class="text-h4">
          <v-icon large left> mdi-airplane-remove </v-icon>
          Exclusão de Parâmetros
        </v-title>
      </div>
    </v-banner>

    <v-container class="text-center">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-container class="text-center">
          <v-select
            v-model="selectAircraft"
            :items="aircraftCodes"
            :rules="[(v) => !!v || 'Item is required']"
            label="Aeronave"
            required
            v-on:change="getParameters()"
          >
          </v-select>

          <v-select
            v-model="parameterCode"
            :items="parameterCodes"
            label="Código do Parâmetro"
            required
          >
          </v-select>

          <v-checkbox
            v-model="checkbox"
            :rules="[(v) => !!v || 'Você deve confimar antes de continuar!']"
            label="Todos dados estão corretos?"
            required
          ></v-checkbox>

          <v-btn
            rounded
            :disabled="!checkbox"
            color="success"
            class="mr-4"
            v-on:click="deleteParameter()"
          >
            Excluir
          </v-btn>

          <v-btn rounded color="error" class="mr-4" @click="reset">
            Limpar Preenchimento
          </v-btn>
          <router-link :to="{ path: '/' }" tag="v-btn">
            <v-btn rounded color="error" class="my-4">
              <v-icon>mdi-close</v-icon>
              Cancelar
            </v-btn>
          </router-link>
        </v-container>
      </v-form>
    </v-container>
  </v-card>
</template>

<script>

import axios from "axios";

export default {
  data: () => ({
    parameterCode: null,
    parameterCodeRules: [
      (v) => !!v || "Código do Parâmetro é necessário",
      (v) => (v && v.length == 6) || "Deve conter exatamente 6 digitos",
    ],
    selectAircraft: null,
    aircraftCodes: [],
    parameterCodes: [],
    checkbox: false,
    url: "http://localhost:8080/",
  }),

  mounted() {
    axios
      .get(this.url + "aircrafts")
      .then((response) => {
        if (response.data != null) {
          response.data.forEach((element) => {
            this.aircraftCodes.push(element.aircraftCode);
          });
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    getParameters() {
      axios
        .get(this.url + "aircrafts/" + this.selectAircraft + "/parameters")
        .then((response) => {
          if (response.data != null) {
            response.data.forEach((element) => {
              this.parameterCodes.push(element.code);
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteParameter() {
      axios
        .delete(this.url + "aircrafts/" + this.selectAircraft + "/parameters/" + this.parameterCode)
        .then((response) => {
          console.log(response.status);
          console.log(response.data);
        });
    },
  },
};
</script>