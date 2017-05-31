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
      deleteIndicador: {
        idIndicador: ""
      }
    },
    periodosPorAgregar: [],
    empresaSelected: {}
  },
  data: {
    empresas: []
  }
};

export default initialState;
