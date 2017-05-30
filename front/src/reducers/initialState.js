import _ from 'lodash';

export const getInitialState = () => _.cloneDeep(initialState);
let initialState = {
  ui: {
    loaded: false,
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
        descripcionIndicador: "",
        idIndicador: ""
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
