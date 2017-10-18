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

    case t.LOAD_METODOLOGIAS_SUCCESS:
      newState = mergeDeep({}, state);
      newState.metodologias = action.metodologias;
      return newState;

    case t.SAVE_INDICADOR_SUCCESS:
      newState = mergeDeep({}, state);
      newState.indicadores = [...state.indicadores, action.indicadorRes];
      return newState;

    case t.INDICADORES_LOADED:
      newState = mergeDeep({}, state);
      newState.indicadores = action.indicadores || [];
      return newState;

    case 'LOGIN_SUCCESS':
      newState = mergeDeep({}, state);
      newState.loggedIn = true;
      newState.user = action.user;
      return newState;  

    case 'CLEAR_RESULT':
      newState = mergeDeep({}, state);
      newState.resultado = null;
      return newState;  
      
    case t.CUENTAS_LOADED:
      newState = mergeDeep({}, state);
      newState.cuentas = action.cuentas;
      return newState;

    case t.CALCULO_SUCCESS:
      newState = mergeDeep({}, state);
      newState.resultado = action.resultado;
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
      const empresaQuePertenece = newState.empresas.find(empresa => empresa.oid === action.empresaId);
      const periodoQuePertenece = empresaQuePertenece.periodos.find(periodo => periodo.oid === action.periodoId);
      periodoQuePertenece.cuentas.push({
        nombre: action.cuenta.cuenta.descripcion,
        valor: action.cuenta.valor
      });
      return newState;

    default:
      return state;

  }
}
