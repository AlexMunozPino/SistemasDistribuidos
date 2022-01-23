<template>
  <v-row>
    <v-col
      class="mt-16"
      v-for="(integrante,i) in integrantes"
      :key="i"
      cols="12"
      sm="4"
    >
      <v-hover>
        <template> <!-- v-slot:default="{ hover }"  (para vista de cada uno) -->
          <v-card
            class="mx-auto integrante-card"
            elevation="24"
            max-width="300"
            color="#f6f4f5"
          >
            <v-avatar
              class="profile contenido"
              size="300"
              rounded
            >
              <v-img 
                :src="integrante.img"
                transition="fade-transition"
              >
                <template v-slot:placeholder>
                  <v-row
                    class="fill-height ma-0"
                    align="center"
                    justify="center"
                  >
                    <v-progress-circular
                      indeterminate
                      color="grey lighten-5"
                    ></v-progress-circular>
                  </v-row>
                </template>
              </v-img>
            </v-avatar>

            <v-card-text class="contenido">
              <h2 class="text-h6 primary--text">
              {{integrante.nombre}}
              </h2>
              <h4>
              {{integrante.email}}
              </h4>

            </v-card-text>

            <!--
            <v-fade-transition>
              <v-overlay
                v-if="hover"
                absolute
                color="#030303"
              >
                <v-btn
                  @click="verIntegrante(integrante.nombre, integrante.id)"
                >

                See more info
                
                </v-btn>
              </v-overlay>
            </v-fade-transition>
            -->
          </v-card>
        </template>
      </v-hover>
    </v-col>
  </v-row>
</template>

<script>

export default{
  name:'About',
  data(){
    return{
      integrantes:[
        {
          nombre: 'Alex Muñoz',
          email: 'alex.munoz@usach.cl',
          img: require('../assets/v38dapnttwo51.jpg')
        },
        {
          nombre: 'Llianluca Lucaveche',
          email: 'llianluca.lucaveche@usach.cl',
          img: require('../assets/gato-empresario.png')
        },
        {
          nombre: 'César Rivera',
          email: 'cesar.rivera@usach.cl',
          img: require('../assets/NMUOC4i__400x400.jpg')
        }
      ]
    }
  },
}

</script>

<style scoped>

  .integrante-card{
    width:300px;
    height:400px;
    text-align: center;
    animation: anime1 1.3s cubic-bezier(0.16, 1, 0.3, 1) 0s backwards alternate;
  }

  @keyframes anime1 {
    0% {
      width: 0px;
      height: 0px;
    }
    25% {
      width: 60px;
      height: 60px;
    }
    50% {
      width: 300px;
      height: 60px;
    }
    100% {
      width: 300px;
      height: 400px;
    }
  }
  .contenido{
    animation: name1 2s cubic-bezier(0.165, 0.84, 0.44, 1) 1s forwards;
    opacity: 0;
  }
  @keyframes name1 {
    0% {
        opacity: 0;
    }
    100% {
        opacity: 1;
    }
  }

</style>