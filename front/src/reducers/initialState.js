import _ from 'lodash';

export const getInitialState = () => _.cloneDeep(initialState);
let initialState = {
  ui: {
    loaded: false,
    showSuccess: false,
    inputsValues: {
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
    empresaSelected: {}
  },
  data: {
    empresas: [],
    metodologias: [],
    indicadores: [
        { id: 0, nombre: "Indicador X", expresion: "indicador{indicador1}" },
        { id: 0, nombre: "Indicador Y", expresion: "indicador{indicador2}" }
    ],
    cuentas: [
        { id: 0, nombre: "Cuenta X", periodo: "Q1", valor: "cuenta{cuenta1}" },
        { id: 0, nombre: "Cuenta Y", periodo: "Q2", valor: "cuenta{cuenta2}" }
    ]
  }
};

export default initialState;
