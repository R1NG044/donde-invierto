import * as t from '../constants/actionTypes';
import initialState from './initialState';
import {mergeDeep} from '../utils/UtilHelper';


export default function ui(state = initialState.ui, action) {
  let newState;

  switch (action.type) {
    case t.EMPRESA_SELECTED:
      newState = mergeDeep({}, state);
      newState.empresaSelected = action.empresa;
      return newState;

    case t.INPUT_EMPRESA_CHANGED:
      newState = Object.assign({}, state);
      let inputsValuesCopy = Object.assign({}, state.inputsValues);
      inputsValuesCopy[action.inputModified] = action.value;
      newState.inputsValues = inputsValuesCopy;
      return newState;

    case t.CLEAR_INPUTS:
      newState = Object.assign({}, state);
      newState.inputsValues = {
        nombreEmpresa: "",
        nombrePeriodo: "",
        anioPeriodo: "",
        mesInicio: "",
        mesFin: ""
      };
      return newState;

    case t.CLEAR_PERIODOS_POR_AGREGAR:
      newState = Object.assign({}, state);
      newState.periodosPorAgregar = [];
      return newState

    case t.AGREGAR_PERIODO:
      newState = mergeDeep({}, state);
      newState.periodosPorAgregar.push(action.periodo);
      return newState;

    default:
      return state;

  }
}
