import * as types from '../constants/dataTypes';
import Mocker from '../mocks/dataMock';
import {clearInputs, clearPeriodosPorAgregar} from './uiActions';
import {showSuccessOnSave} from './uiActions';

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
    clearInputs('empresa')(dispatch);
    clearPeriodosPorAgregar()(dispatch);
    showSuccessOnSave()(dispatch);

  }
}

export function cargarCuenta(type) { // Nota, type sera siempre cuenta salvo que cambie  routes.js
  return (dispatch, getState) => (e) => {
    e.preventDefault();
    let cuenta;
    let inputs = getState().ui.inputsValues[type];

    dispatch({
      type: types.SAVE_CUENTA_REQUEST
    });

    const empresaId = parseInt(inputs.empresaSelected);
    const periodoId = parseInt(inputs.periodoSelected);

    cuenta = {
      nombre: inputs.nombreCuenta,
      empresaId,
      periodoId,
      valorCuenta: parseInt(inputs.valorCuenta)
    }

    // TODO: Do the request and then...
    dispatch({
      type: types.SAVE_CUENTA_SUCCESS,
      cuenta,
      empresaId,
      periodoId
    });
    clearInputs(type)(dispatch);
    showSuccessOnSave()(dispatch);

  }
}

export function cargarIndicador(type) {
  return (dispatch, getState) => (e) => {
    e.preventDefault();
    let indicador;
    let inputs = getState().ui.inputsValues[type];

    dispatch({
      type: types.SAVE_INDICADOR_REQUEST
    });


    indicador = {
      nombre: inputs.nombreIndicador,
      descripcion: inputs.expresionIndicador
    }

    // TODO: Do the request and then...
    indicador.id = Math.floor(Math.random() * (1000 - 1)) + 1; // TODO: HARDCODED

    dispatch({
      type: types.SAVE_INDICADOR_SUCCESS,
      indicador
    });
    clearInputs(type)(dispatch);
    showSuccessOnSave()(dispatch);

  }
}

export function borrarIndicador(type) {
  return (dispatch, getState) => (e) => {
    e.preventDefault();
    let indicador;
    let inputs = getState().ui.inputsValues[type];

    dispatch({
      type: types.DELETE_INDICADOR_REQUEST
    });


    // TODO: Do the request and then...
    dispatch({
      type: types.DELETE_INDICADOR_SUCCESS,
      indicador: inputs.idIndicador
    });

    clearInputs(type)(dispatch);
    showSuccessOnSave()(dispatch);

  }
}
