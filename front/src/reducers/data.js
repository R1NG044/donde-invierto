import * as t from '../constants/dataTypes';
import initialState from './initialState';
import {mergeDeep} from '../utils/UtilHelper';


export default function data(state = initialState.data, action) {
  let newState;

  switch (action.type) {
    case t.INITIAL_DATA_SUCCESS:
      newState = mergeDeep({}, state);
      newState.empresas = action.empresas;
      return newState;

    case t.SAVE_EMPRESA_SUCCESS:
      newState = mergeDeep({}, state);
      newState.empresas.push(action.empresa);
      return newState;

    case t.SAVE_CUENTA_SUCCESS:
      newState = mergeDeep({}, state);
      const empresaQuePertenece = newState.empresas.find(empresa => empresa.id === action.empresaId);
      const periodoQuePertenece = empresaQuePertenece.periodos.find(periodo => periodo.id === action.periodoId);
      periodoQuePertenece.cuentas.push({
        nombre: action.cuenta.nombre,
        valor: action.cuenta.valorCuenta
      });
      return newState;

      default:
      return state;

  }
}
