<template>
    <div id="main">
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

        <div class="my-10">
            <v-card class="mx-auto" color="#26c6da" dark max-width="400">
                <v-card-title>
                    <v-icon large left>
                        mdi-airplane
                    </v-icon>
                    <span class="text-h4 font-weight-light">Aircraft Tester</span>
                </v-card-title>

                <v-card-text class="text-h5 font-weight-bold">
                    Escolha uma opção:
                </v-card-text>

                <v-card-actions>
                    <v-row align="center">
                        <v-col cols="12" sm="6">
                            <div class="text-center">
                                <div class="my-4">
                                    <router-link :to="{ path: '/ParameterRegister' }" tag="v-btn">
                                        <v-btn rounded color="success" dark>
                                            <v-icon left>
                                                mdi-airplane-plus
                                            </v-icon>
                                            Adicionar parâmetro
                                        </v-btn>
                                    </router-link>
                                </div>

                                <div class="my-4">
                                    <router-link :to="{ path: '/ParameterDelete' }" tag="v-btn">
                                        <v-btn rounded color="error" dark>
                                            <v-icon left>
                                                mdi-airplane-remove
                                            </v-icon>
                                            Deletar parâmetro
                                        </v-btn>
                                    </router-link>
                                </div>

                                <div class="my-4">
                                    <router-link :to="{ path: '/ParameterUpdate' }" tag="v-btn">
                                        <v-btn rounded color="primary" @click="say('hello')">
                                            <v-icon left>
                                                mdi-airplane-edit
                                            </v-icon>
                                            Atualizar parâmetro
                                        </v-btn>
                                    </router-link>
                                </div>

                                <div class="my-4">
                                    <router-link :to="{ path: '/Test' }" tag="v-btn">
                                        <v-btn rounded color="warning" @click="say('hello')">
                                            <v-icon left>
                                                mdi-airplane-cog
                                            </v-icon>
                                            Realizar ensaio
                                        </v-btn>
                                    </router-link>

                                    <div class="my-4">
                                        <router-link :to="{ path: '/TestConsultation' }" tag="v-btn">
                                            <v-btn rounded color="normal" @click="say('hello')">
                                                <v-icon left>
                                                    mdi-airplane-search
                                                </v-icon>
                                                Consultar Ensaios Realizados
                                            </v-btn>
                                        </router-link>
                                    </div>
                                </div>
                            </div>
                        </v-col>
                    </v-row>
                </v-card-actions>
            </v-card>
        </div>

    </div>
</template>

<script>
export default {
    name: 'home-page'
}
</script>