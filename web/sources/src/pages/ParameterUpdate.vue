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
          <v-icon large left> mdi-airplane-edit </v-icon>
          Atualização de Parâmetros
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
            v-on:change="getParameters()"
          >
          </v-select>

          <v-select
            v-model="parameterCode"
            :items="parameterCodes"
            label="Código do Parâmetro"
            required
            v-on:change="getParameterInfo()"
          >
          </v-select>

          <v-text-field v-model="parameterName" label="Nome do Parâmetro" required>
          </v-text-field>

          <v-select v-model="selectType" :items="types" label="Tipo" required> </v-select>

          <v-text-field v-model="samplingRate" label="Sampling Rate" required>
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
            v-on:click="updateParameter()"
          >
            Atualizar
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
    types: ["Temperatura", "Pressão"],
    activeStatus: false,
    selectAircraft: null,
    selectType: null,
    parameterName: null,
    samplingRate: null,
    minValue: null,
    maxValue: null,
    aircraftCodes: [],
    parameterCodes: [],
    parameters: [{}],
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
              this.parameters.push(element);
            });
          }
          console.log(response.status);
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getParameterInfo() {
            this.parameters.forEach((element) => {
            if(element.code == this.parameterCode) {
                this.activeStatus = (element.activeStatus===true)?"true":"false";
                console.log(this.activeStatus)
                this.parameterName = element.name;
                this.selectType = element.type;
                this.samplingRate = element.samplingRate;
                this.minValue = element.minValue;
                this.maxValue = element.maxValue;
            }
        });
    },
    updateParameter() {
        console.log(this.selectType)
          axios.put(this.url + "aircrafts/" + this.selectAircraft + "/parameters/" + this.parameterCode, {
              "aircraftCode":this.selectAircraft,
              "code":this.parameterCode,
              "name":this.parameterName,
              "type":this.selectType,
              "samplingRate":this.samplingRate,
              "minValue":this.minValue,
              "maxValue":this.maxValue,
              "activeStatus":this.activeStatus,
          }).then((response) => {
              console.log(response.status)
              console.log(response.data)
          })
      }
  },
};
</script>