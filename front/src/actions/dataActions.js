import * as types from '../constants/dataTypes';
import * as uiActions from './uiActions';
import Mocker from '../mocks/dataMock';
import {clearInputs, clearPeriodosPorAgregar} from './uiActions';
import {showSuccessOnSave} from './uiActions';

export function loadData() {
  return (dispatch) => {
    dispatch({
      type: types.INITIAL_DATA_REQUEST
    });

    //const empresas = Mocker.generateEmpresas(quantity);

    fetch('http://localhost:8081/empresa/')
      .then(res => res.json())
      .then(empresas => {
        dispatch({
          type: types.INITIAL_DATA_SUCCESS,
          empresas
        });
      });

    fetch('http://localhost:8081/indicador/0')
      .then(res => res.json())
      .then(indicadores => {
        dispatch({
          type: types.INDICADORES_LOADED,
          indicadores
        });
      })
    fetch('http://localhost:8081/cuenta/')
    .then(res => res.json())
    .then(cuentas => {
      dispatch({
        type: types.CUENTAS_LOADED,
        cuentas
      });
    })
  }
}

export function fetchMetodologias() {
  return (dispatch) => {

    fetch('http://localhost:8081/metodologia/')
      .then(res => res.json())
      .then(metodologias => {
        dispatch({
          type: types.LOAD_METODOLOGIAS_SUCCESS,
          metodologias
        });
      });
  }
}

export function realizarAnalisis(type) {
  return (dispatch, getState) => e => {
    e.preventDefault();
    let inputs = getState().ui.inputsValues[type];
  
    dispatch({
      type: types.CALCULO_REQUEST
    });
  
    const indicadorId = parseInt(inputs.indicadorSelected);  
    const empresaId = parseInt(inputs.empresaSelected);
    const periodoId = parseInt(inputs.periodoSelected);
  
  
    fetch(`http://localhost:8081/evaluar/${indicadorId}/${empresaId}/${periodoId}`)
      .then(res => res.json())
      .then((resultado) => {
        dispatch({
          type: types.CALCULO_SUCCESS,
          resultado
        });
        clearInputs(type)(dispatch);
      });
  }

}

export function aplicarMetodologia(metodologia) {
  return (dispatch) => {
    dispatch({
      type: types.APLICAR_METODOLOGIA_REQUEST
    });
    dispatch(uiActions.selectMetodologia(metodologia))
	
	const metodologiaNombre = metodologia.nombre;
	
    fetch(`http://localhost:8081/metodologia/${metodologiaNombre}`)
	.then(res => res.json())
    .then(empresas => {
        dispatch({
			type: types.APLICAR_METODOLOGIA_SUCCESS,
			empresas
		});
	})

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
		cuenta: {descripcion: inputs.nombreCuenta},
		valor: parseFloat(inputs.valorCuenta)
	};
	
    const options = {
      method: 'PUT',
	  headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
      },
	  body: JSON.stringify(cuenta)
    };

    fetch(`http://localhost:8081/empresa/${periodoId}`, options)
      .then(() => {
        dispatch({
          type: types.SAVE_CUENTA_SUCCESS,
		    cuenta: cuenta,
          empresaId,
          periodoId
        });
        clearInputs(type)(dispatch);
        showSuccessOnSave()(dispatch);
      });
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
      expression: inputs.expresionIndicador
    };

    const options = {
      method: 'POST',
      body: JSON.stringify(indicador),
	  headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
      }
    };

    fetch('http://localhost:8081/indicador/', options)
      .then((res) => {
        if (res.status == 500) throw Error('Indicador Invalido');
        return res;
      })
      .then(() => {
        dispatch({
          type: types.SAVE_INDICADOR_SUCCESS,
          indicador
        });
        clearInputs(type)(dispatch);
        showSuccessOnSave()(dispatch);
      });
  };
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
