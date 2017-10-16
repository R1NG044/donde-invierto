import _ from 'lodash';

export const getInitialState = () => _.cloneDeep(initialState);
let initialState = {
  ui: {
    loaded: false,
    showSuccess: false,
    inputsValues: {
      login: {
        username: "",
        password: "",
      },
      analizar: {
        empresaSelected: "",
        periodoSelected: "",
        indicadorSelected: ""
      },
      empresa: {
        nombreEmpresa: "",
        nombrePeriodo: "",
        anioPeriodo: "",
        mesInicio: "",
        mesFin: ""
      },
      cuenta: {
        empresaSelected: "",
        periodoSelected: "",
        nombreCuenta: "",
        valorCuenta: ""
      },
      newIndicador: {
        nombreIndicador: "",
        expresionIndicador: "",
        indicadorSelected: "",
        idIndicador: "",
        empresaSelected: "",
        periodoSelected: ""
      },
      metodologia: {
        nombreMetodologia: "",
        indicadores: []
      },
      deleteIndicador: {
        idIndicador: ""
      }
    },
    periodosPorAgregar: [],
    empresaSelected: {},
    periodoSelected: ""
  },
  data: {
    loggedIn: false,
    empresas: [],
    empresasByMetodologias: [],
    metodologias: [],
    indicadores: [],
    cuentas: [],
    calculo: ""
  }
};

export default initialState;
