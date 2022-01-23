<template>
  <div>
    <v-card
      class="info-card"
      color="#F4F4F4"
      v-show="true"
    >
      <v-sheet
        class="v-sheet--offset mx-auto"
        color="#1a1a1a"
        elevation="12"
        max-width="calc(100% - 40px)"
      >
        <v-sparkline
          :value="value"
          :labels="labels"
          :smooth="2"
          :stroke-linecap="lineCap"
          :label-size="4"
          color="#f6f4f5"
          line-width="2"
          padding="16"
          auto-draw
          :key="componentKey"
        ></v-sparkline>
      </v-sheet>

      <v-card-text>
        <div class="text-h6 font-weight-light mb-2">
          <strong>Terremotos detectados en las últimas 24 horas</strong>
          <br/>
          Magnitud promedio: {{magnitudProm}}
        </div>

      </v-card-text>
    </v-card>

    <v-card class="info-card2">
      <v-card-subtitle class="text-h6 font-weight-light mb-2">
        <strong>Consultar terremotos por rango de fecha</strong>
      </v-card-subtitle>
      <v-row class="mx-10">

        <v-col class="my-10">
          <v-menu
            v-model="menu"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="date1"
                label="Fecha de inicio"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="date1"
              color="#1a1a1a"
              :first-day-of-week="1"
              @input="menu = false"
              locale="es-es"
            ></v-date-picker>
          </v-menu>
        </v-col>
        <v-col class="my-10">

          <v-menu
            v-model="menu2"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="date2"
                label="Fecha de término"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="date2"
              color="#1a1a1a"
              locale="es-es"
              :first-day-of-week="1"
              @input="menu2 = false"
            ></v-date-picker>
          </v-menu>

        </v-col>
      </v-row>
    </v-card>

    <v-list three-line color="transparent" :key="terremotosKey">
        <v-list-item v-for="(item) in terremotos" :key="item.id" class="my-9">
          <v-card class="info-card3" width="71%" color="#f6f4f5" elevation="24">
            <v-list-item-content>
              <v-list-item-subtitle><h3>Ubicación: {{item.place}}</h3></v-list-item-subtitle>
              <br/>
              <v-list-item-subtitle><h4>Magnitud: {{item.magnitude}} | Profundidad: {{item.depth}}</h4></v-list-item-subtitle>
              <br/>
              <v-list-item-subtitle>Latitud: {{item.latitude}} | Longintud: {{item.longitude}}</v-list-item-subtitle>
              <br/>
              <v-list-item-subtitle><h4>Fecha: {{item.fecha}} </h4></v-list-item-subtitle>
            </v-list-item-content>
          </v-card>
        </v-list-item>
    </v-list>
    
  </div>
</template>

<script>
  import axios from 'axios'
  axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*'
  
  export default {
    name:'Home',
    data: () => ({
      menu: false,
      menu2: false,
      date1: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      date2: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      show:false,
      label:false,
      earthquakes:[],
      magnitudProm:0,
      lineCap:'round',
      labels: [
        '00',
        '01',
        '02',
        '03',
        '04',
        '05',
        '06',
        '07',
        '08',
        '09',
        '10',
        '11',
        '12',
        '13',
        '14',
        '15',
        '16',
        '17',
        '18',
        '19',
        '20',
        '21',
        '22',
        '23',
      ],
      value: [
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
      ],
      terremotos24:[],
      terremotos:[],
      componentKey: 0,
      terremotosKey: 0
    }),
    methods:{
      getToday() {
        axios.get("http://kafka-java-consumer:8081/earthquakes/today", {headers: {
          // remove headers
        }}).
        then(response => {
          let data = JSON.parse(JSON.stringify(response.data))
          this.terremotos24 = this.transformTimestampToDate(data)
        })
        .catch(e => console.log(e))
        .finally(() => this.contarTerremotos())
      },

      getBetweenDates() {
        axios.get("http://kafka-java-consumer:8081/earthquakes/"+this.date1+"/"+this.date2, {headers: {
          // remove headers
        }}) 
        .then(response => {
          let data = JSON.parse(JSON.stringify(response.data))
          this.terremotos = this.transformTimestampToDate(data)
        })
        .catch(e => console.log(e))
        .finally(() => this.actualizarTerremotosFecha())
      },

      transformTimestampToDate(terremotos) {
        for (let i = 0; i < terremotos.length; i++) {
          let fecha = ""
          let current = new Date(terremotos[i].timestamp*1000)
          fecha = current.getFullYear().toString()+"-"
          if (current.getUTCMonth() <= 8) fecha += "0"+(current.getMonth()+1).toString()+"-"
          else fecha += (current.getMonth()+1).toString()+"-"
          fecha += current.getDate().toString()
          terremotos[i].fecha = fecha
        }
        return terremotos
      },


      generarFecha(){
        var fecha = new Date(Date.now()).getTime();
        return fecha;
      },


      arreglarTiempo(time){
        var date = new Date(time);
        //return date.toLocaleString();
        return date;
      },


      contarTerremotos(){
        let mag = 0;
        for(let i=0; i < 24 ; i++){
          for(let j=0 ; j<this.terremotos24.length ; j++){
            if( i === this.arreglarTiempo(this.terremotos24[j].timestamp).getHours() ){
              this.value[i] = this.value[i] + 1;
            }
            mag = mag + parseInt(this.terremotos24[j].magnitude)
          }
        }
        this.magnitudProm = (mag)/(24*this.terremotos24.length)
        this.actualizarGrafico()
      },

      actualizarGrafico(){
        this.componentKey++
      },

      actualizarTerremotosFecha() {
        this.terremotosKey++
      }
    },
    watch: {
      date1: function () {
        this.getBetweenDates()
      },
      date2: function() {
        this.getBetweenDates()
      }
    },
    mounted() {
      this.getToday()
      this.getBetweenDates(this.date1, this.date2)
    }
  }

</script>


<style>
  .info-card{
    margin: 50px auto;
    width: 70%;
    text-align: center;
    animation: slide-top 0.4s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  }
  @keyframes slide-top {
    0% {
      transform: translateY(1000px);
    }
    100% {
      transform: translateY(0px);
    }
  }

  .info-card2{
    margin: 20px auto;
    width: 70%;
    text-align: center;
    animation: slide-top2 0.7s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  }
  @keyframes slide-top2 {
    0% {
      transform: translateY(1000px);
    }
    100% {
      transform: translateY(0px);
    }
  }

  .info-card3{
    margin: 4px auto;
    text-align: center;
    animation: slide-top3 1s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  }
  @keyframes slide-top3 {
    0% {
      transform: translateY(1000px);
    }
    100% {
      transform: translateY(0px);
    }
  }

  .sheet-text{
    margin: 0px auto;
    color: white;
  }

  .v-sheet--offset {
    top: -24px;
    position: relative;
  }

  h3{
    font-size:115%;
    font-weight: normal;
  }
  h4{
    font-size: 110%;
    color:grey;
    font-weight: normal;
  }
</style>
