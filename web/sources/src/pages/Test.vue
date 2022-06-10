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
          <v-icon large left> mdi-airplane-cog </v-icon>
          Teste
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
            v-on:change="getEngineers()"
          >
          </v-select>

          <v-text-field v-model="testName" label="Nome do Ensaio" required>
          </v-text-field>

          <v-text-field
            v-model="TestTime"
            label="Tempo de Ensaio"
            required
          >
          </v-text-field>

          <v-text-field
            v-model="date"
            type="date"
            label="Data do Ensaio"
            filled
            readonly
          ></v-text-field>

          <v-select
            v-model="engineer"
            :items="engineers"
            :rules="[(v) => !!v || 'Item is required']"
            label="Engenheiro Resposável"
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
            v-on:click="registerParameter()"
          >
            Cadastrar
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
  name: "home-page",
  data: () => ({
    parameterCode: "",
    parameterCodeRules: [
      (v) => !!v || "Código do Parâmetro é necessário",
      (v) => (v && v.length == 6) || "Deve conter exatamente 6 digitos",
    ],
    selectAircraft: null,
    testName: null,
    testTime: null,
    engineer: null,
    checkbox: false,
    aircraftCodes: [],
    engineers: [],
    date: new Date().toISOString().slice(0, 10),
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
    axios
      .get(this.url + "engineers")
      .then((response) => {
        if (response.data != null) {
          console.log(response.data);
          response.data.forEach((element) => {
            this.engineers.push(element.name);
          });
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    sendTestInfo() {
      axios
        .post(this.url + "", {
        })
        .then((response) => {
          console.log(response.status);
          console.log(response.data);
        });
    },
  },
};
</script>