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
          <v-icon large left> mdi-airplane-search </v-icon>
          Consulta de Ensaios
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
            v-on:change="getTestNumber()"
          >
          </v-select>

          <v-select
            v-model="testNumber"
            :items="parameterCodes"
            label="Número do Ensaio"
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
            v-on:click="getTestResult()"
          >
            Consultar
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
          <v-card
            class="mx-auto text-center"
            color="green"
            dark
            v-if="testData.length > 0"
          >
            <v-card-text>
              <v-sheet color="rgba(0, 0, 0, .12)">
                <v-sparkline
                  :value="testData"
                  color="rgba(255, 255, 255, .7)"
                  height="100"
                  padding="24"
                  line-width="0.5"
                  stroke-linecap="round"
                  smooth
                >
                </v-sparkline>
              </v-sheet>
            </v-card-text>

            <v-card-text>
              <div class="text-h4 font-weight-thin">0 - Erro | 1 - Sucesso</div>
            </v-card-text>

            <v-divider></v-divider>
          </v-card>
        </v-container>
      </v-form>
    </v-container>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "home-page",
  data: () => ({
    parameterCode: "",
    parameterCodeRules: [
      (v) => !!v || "Código do Parâmetro é necessário",
      (v) => (v && v.length == 6) || "Deve conter exatamente 6 digitos",
    ],
    selectAircraft: null,
    testName: null,
    testNumber: null,
    testTime: null,
    engineer: null,
    checkbox: false,
    aircraftCodes: [],
    parameterCodes: [],
    testData: [],
    testDate: new Date().toISOString().slice(0, 10),
    url: "http://localhost:8080/",
  }),

  mounted() {
    console.log;
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
    getTestNumber() {
      axios
        .get(this.url + "aircrafts/" + this.selectAircraft + "/tests")
        .then((response) => {
          if (response.data != null) {
            response.data.forEach((element) => {
              this.parameterCodes.push(element.testNumber);
            });
          }
          console.log(response.status);
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getTestResult() {
      axios
        .get(
          this.url + "test-data/" + this.selectAircraft + "/tests/" + this.testNumber
        )
        .then((response) => {
          console.log(response.data);
          if (response.data != null) {
            response.data.forEach((element) => {
              let status = 0
              if (element.status == true) {
                  status = 1;
              }
              this.testData.push(status);
            });
          }
          console.log(this.testData);
          console.log(response.status);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
