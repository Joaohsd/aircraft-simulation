<template>
    <v-card class="fill-height" fluid>
        <v-banner app color="green" dark>
            <div class="d-flex align-center">
                <v-title class="text-h1">
                    <v-icon large left>
                        mdi-airplane
                    </v-icon>
                    Aircraft Tester
                </v-title>
            </div>
        </v-banner>

        <v-banner app color="green" dark>
            <div class="d-flex align-center">
                <v-title class="text-h4">
                    <v-icon large left>
                        mdi-airplane-search
                    </v-icon>
                    Consulta de Ensaios
                </v-title>
            </div>
        </v-banner>

        <v-container class="text-center">
            <v-form ref="form" v-model="valid" lazy-validation>
                <v-container class="text-center">
                    <v-select v-model="select" :items="items" :rules="[v => !!v || 'Item is required']" label="Aeronave"
                        required>
                    </v-select>

                    <v-text-field v-model="name" :rules="nameRules" label="Número do Ensaio" required>
                    </v-text-field>

                    <v-checkbox v-model="checkbox" :rules="[v => !!v || 'Você deve confimar antes de continuar!']"
                        label="Todos dados estão corretos?" required></v-checkbox>

                    <v-btn rounded :disabled="!valid" color="success" class="mr-4" @click="validate">
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