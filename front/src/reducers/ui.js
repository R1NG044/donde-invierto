import * as t from '../constants/actionTypes';
import initialState from './initialState';
import {mergeDeep} from '../utils/UtilHelper';
import _ from 'lodash';


export default function ui(state = initialState.ui, action) {
  let newState;

  switch (action.type) {
    case t.EMPRESA_SELECTED:
      newState = mergeDeep({}, state);
      newState.empresaSelected = action.empresa;
      return newState;

    case t.METODOLOGIA_SELECTED:
      newState = mergeDeep({}, state);
      newState.metodologiaSelected = action.metodologia;
      return newState;

    case t.INPUT_EMPRESA_CHANGED:
      newState = Object.assign({}, state);
      let inputsValuesCopy = _.cloneDeep(state.inputsValues);
      inputsValuesCopy[action.section][action.inputModified] = action.value;
      newState.inputsValues = inputsValuesCopy;
      return newState;

    case t.CLEAR_INPUTS:
      newState = Object.assign({}, state);
      const inputsCopy = _.cloneDeep(newState.inputsValues);
      inputsCopy[action.section] = initialState.ui.inputsValues[action.section];
      newState.inputsValues = inputsCopy;
      return newState;

    case t.CLEAR_PERIODOS_POR_AGREGAR:
      newState = Object.assign({}, state);
      newState.periodosPorAgregar = [];
      return newState;

    case t.AGREGAR_PERIODO:
      newState = mergeDeep({}, state);
      newState.periodosPorAgregar.push(action.periodo);
      return newState;

    case t.ADD_DATA_TO_EXPRESSION:
      newState = mergeDeep({}, state);
      let inputsValCopy = _.cloneDeep(state.inputsValues);
      inputsValCopy.newIndicador.expresionIndicador = state.inputsValues.newIndicador.expresionIndicador + action.value;
      newState.inputsValues = inputsValCopy;
      return newState;

    case t.TOGGLE_SUCCESSFUL_MESSAGE:
      newState = mergeDeep({}, state);
      newState.showSuccess = !state.showSuccess;
      return newState;

      default:
      return state;

  }
}
