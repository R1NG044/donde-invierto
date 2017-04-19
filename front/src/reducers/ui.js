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

    default:
      return state;

  }
}
