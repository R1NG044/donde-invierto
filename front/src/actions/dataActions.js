import * as types from '../constants/dataTypes';
import Mocker from '../mocks/dataMock';
import {clearInputs, clearPeriodosPorAgregar} from './uiActions';

export function loadData(quantity) {
  return (dispatch) => {
    dispatch({
      type: types.INITIAL_DATA_REQUEST
    });

    const empresas = Mocker.generateEmpresas(quantity);

    dispatch({
      type: types.INITIAL_DATA_SUCCESS,
      empresas
    });
  }
}

export function cargarEmpresa(e) {
  return (dispatch, getState) => {
    e.preventDefault();
    let empresa;

    dispatch({
      type: types.SAVE_EMPRESA_REQUEST
    });

    empresa = {
      nombre: getState().ui.inputsValues.nombreEmpresa,
      periodos: getState().ui.periodosPorAgregar,
      id: 10
    } // TODO: Borrar el 10, se va a mandar al server sin id


    dispatch({
      type: types.SAVE_EMPRESA_SUCCESS,
      empresa
    });
    clearInputs()(dispatch);
    clearPeriodosPorAgregar()(dispatch);

  }
}
