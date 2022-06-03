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
          <v-icon large left> mdi-airplane-plus </v-icon>
          Cadastro de Parâmetros
        </v-title>
      </div>
    </v-banner>

    <v-container class="text-center">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-container class="text-center">
          <v-switch
            v-model="activeStatus"
            label="Parâmetro Ativo"
            color="info"
            value="true"
            hide-details
          >
          </v-switch>

            <v-select
              v-model="selectAircraft"
              :items="aircraftCodes"
              :rules="[(v) => !!v || 'Item is required']"
              label="Aeronave"
              required
            >
            </v-select>

          <v-text-field v-model="parameterName" label="Nome do Parâmetro" required>
          </v-text-field>

          <v-text-field
            v-model="parameterCode"
            :rules="parameterCodeRules"
            :counter="6"
            label="Código do Parâmetro"
            required
          >
          </v-text-field>

            <v-select
              v-model="selectType"
              :items="types"
              :rules="[(v) => !!v || 'Item is required']"
              label="Tipo"
              required
              ></v-select>

          <v-text-field v-model="samplinRate" label="Sampling Rate" required>
          </v-text-field>

          <v-text-field v-model="minValue" label="Valor Mínimo Permitido" required>
          </v-text-field>

          <v-text-field v-model="maxValue" label="Valor Máximo Permitido" required>
          </v-text-field>

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
  data: () => ({
    parameterCode: "",
    parameterCodeRules: [
      (v) => !!v || "Código do Parâmetro é necessário",
      (v) => (v && v.length == 6) || "Deve conter exatamente 6 digitos",
    ],
    types: ["Temperatura", "Pressão"],
    activeStatus: false,
    selectAircraft: null,
    selectType: null,
    parameterName: null,
    samplingRate: null,
    minValue: null,
    maxValue: null,
    aircraftCodes: [],
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
      registerParameter() {
          axios.post(this.url + "parameters",{
              "code":this.parameterCode,
              "name":this.parameterName,
              "type":this.selectType,
              "samplingRate":this.samplingRate,
              "minValue":this.minValue,
              "maxValue":this.maxValue,
              "activeStatus":this.activeStatus,
              "aircraftCode":this.selectAircraft,
          }).then((response) => {
              console.log(response.status)
              console.log(response.data)
          })
      }
  }
};
</script>