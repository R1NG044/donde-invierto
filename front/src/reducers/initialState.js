
let initialState = {
  ui: {
    loaded: false,
    inputsValues: {
      nombreEmpresa: "",
      nombrePeriodo: "",
      anioPeriodo: "",
      mesInicio: "",
      mesFin: ""
    },
    periodosPorAgregar: [],
    empresaSelected: {}
  },
  data: {
    empresas: []
  }
};


export default initialState;
