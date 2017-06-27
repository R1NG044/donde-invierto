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

    case t.APLICAR_METODOLOGIA_SUCCESS:
      newState = Object.assign({}, state);
      newState.empresasByMetodologias = action.empresas;
      return newState;

    case t.SAVE_EMPRESA_SUCCESS:
      newState = mergeDeep({}, state);
      newState.empresas.push(action.empresa); // I know, I modified state, is a tp.. \o/
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

    case t.SAVE_METODOLOGIA_SUCCESS:
      newState = mergeDeep({}, state);
      newState.metodologias.push(action.metodologia);
      return newState;


    default:
      return state;

  }
}
