import * as types from '../constants/actionTypes';

export function selectEmpresa(empresa) {
  return (dispatch) => {
    dispatch({
      type: types.EMPRESA_SELECTED,
      empresa
    })
  }
}

export function cargarEmpresaInputChange(e) {
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

export function clearInputs() {
  return (dispatch) => {
    dispatch({
      type: types.CLEAR_INPUTS
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
