import * as types from '../constants/actionTypes';

export function selectEmpresa(empresa) {
  return (dispatch) => {
    dispatch({
      type: types.EMPRESA_SELECTED,
      empresa
    })
  }
}

export function selectMetodologia(metodologia) {
  return (dispatch) => {
    dispatch({
      type: types.METODOLOGIA_SELECTED,
      metodologia
    })
  }
}

export function inputChanged(section) {
  return (dispatch) => (e) => {

    e.preventDefault();
    dispatch({
      type: types.INPUT_EMPRESA_CHANGED,
      inputModified: e.target.name,
      value: e.target.value,
      section
    });
  }
}

export function cargarCuentaInputChange(e) {
  return (dispatch) => {
    e.preventDefault();

    dispatch({
      type: types.INPUT_EMPRESA_CHANGED,
      inputModified: e.target.name,
      value: e.target.value
    });
  }
}


export function agregarPeriodo(inputs) {
  return (dispatch) => {
    const periodo = {
      nombre: inputs.nombrePeriodo,
      anio: inputs.anioPeriodo,
      rango: [inputs.mesInicio, inputs.mesFin],
      cuentas: []
    };
    dispatch({
      type: types.AGREGAR_PERIODO,
      periodo
    });
  }
}

export function clearInputs(section) {
  return (dispatch) => {
    dispatch({
      type: types.CLEAR_INPUTS,
      section
    })
  }
}

export function clearPeriodosPorAgregar() {
  return (dispatch) => {
    dispatch({
      type: types.CLEAR_PERIODOS_POR_AGREGAR
    })
  }
}

export function addIndicadorAMetodologia(indicadorId) {
  return dispatch => {
    const indicador = {
      id: indicadorId,
      nombre: ""
    }
    dispatch({
      type: types.AGREGAR_INDICADOR_A_METODOLOGIA,
      indicador
    })
  }
}

export function addDataToExpresion(section) {
  return (dispatch, getState) => (e) => {

    dispatch({
        type: types.ADD_DATA_TO_EXPRESSION,
        value: e.target.value,
        section
    })
  }
}


export function showSuccessOnSave() {
  return (dispatch) => {
    dispatch({
        type: types.TOGGLE_SUCCESSFUL_MESSAGE
    });

    setTimeout(() => dispatch({
        type: types.TOGGLE_SUCCESSFUL_MESSAGE
    }), 3000);
  }
}

