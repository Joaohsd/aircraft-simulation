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
            v-on:change="getTestNumber()"
          >
          </v-select>

          <v-col cols="3">
            <v-text-field
              v-model="testNumber"
              type="number"
              label="Número do Ensaio"
              filled
              readonly
            ></v-text-field>
          </v-col>

          <v-text-field v-model="testName" label="Nome do Ensaio" required>
          </v-text-field>

          <v-text-field v-model="testTime" label="Tempo de Ensaio" required>
          </v-text-field>

          <v-text-field
            v-model="testDate"
            type="date"
            label="Data do Ensaio"
            filled
            readonly
          ></v-text-field>

          <v-select
            v-model="engineer"
            :items="engineerName"
            :rules="[(v) => !!v || 'Item is required']"
            label="Engenheiro Resposável"
            required
            v-on:change="setEngineerCPF()"
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
            v-on:click="sendTestInfo()"
          >
            Realizar Ensaio
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

          <div style="min-height: 4px">
            <v-progress-linear
              v-model="value"
              :active="progressBar"
              indeterminate
            ></v-progress-linear>
          </div>

          <div class="text-center">
            <v-dialog v-model="dialog" width="500">
              <v-card>
                <v-card-title class="text-h5 grey lighten-2">
                  Teste Concluido
                </v-card-title>

                <v-card-text> {{ textDialog }} </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="primary"
                    :disabled="flagButtonDialog"
                    text
                    @click="saveOnDB"
                  >
                    Sim
                  </v-btn>
                  <v-btn
                    color="danger"
                    :disabled="flagButtonDialog"
                    text
                    @click="dialog = false"
                  >
                    Não
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
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
    progressBar: false,
    dialog: false,
    textDialog: "Deseja salvar os dados do ensaio no banco de dados?",
    flagDialog: false,
    flagButtonDialog: false,
    aircraftCodes: [],
    engineerName: [],
    cpfEngineer: "",
    engineers: [{}],
    testDate: new Date().toISOString().slice(0, 10),
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
    axios
      .get(this.url + "engineers")
      .then((response) => {
        if (response.data != null) {
          response.data.forEach((element) => {
            this.engineerName.push(element.name);
            this.engineers.push(element);
          });
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    setEngineerCPF() {
      this.engineers.forEach((element) => {
        if (element.name == this.engineer) {
          this.cpfEngineer = element.cpf;
        }
      });
    },

    getTestNumber() {
      axios
        .get(this.url + "aircrafts/" + this.selectAircraft + "/tests")
        .then((response) => {
          if (response.data != null) {
            response.data.forEach((element) => {
              if (this.testNumber < element.testNumber) {
                this.testNumber = element.testNumber;
              }
            });
          }
          this.testNumber += 1;
          console.log(response.status);
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    sendTestInfo() {
      this.progressBar = true;
      axios
        .post(this.url + "tests", {
          aircraftCode: this.selectAircraft,
          testNumber: this.testNumber,
          testName: this.testName,
          cpfEngineer: this.cpfEngineer,
          time: this.testTime,
          testDate: this.testDate,
        })
        .then((response) => {
          console.log(response.status);
          console.log(response.data);

          if (response.status == 200) {
            this.dialog = true;
            this.progressBar = false;
          }
        });
    },

    saveOnDB() {
      if (!this.flagDialog) {
        this.flagButtonDialog = true;
        this.textDialog = "Salvando no Banco de Dados. Aguarde...";
        axios.post(this.url + "test-data/save").then((response) => {
          if (response.status == 200) {
            this.textDialog = "Deseja ver os resultados do ensaio?";
            this.flagDialog = true;
            this.flagButtonDialog = false;
          }
        });
      } else {
        this.flagDialog = false;
        this.$router.push("/TestConsultation");
      }
    },
  },
};
</script>