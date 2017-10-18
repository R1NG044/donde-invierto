import * as types from '../constants/dataTypes';
import * as uiActions from './uiActions';
import Mocker from '../mocks/dataMock';
import {clearInputs, clearPeriodosPorAgregar} from './uiActions';
import {showMessage} from './uiActions';

export function loadData() {
  return (dispatch,getState) => {
    dispatch({
      type: types.INITIAL_DATA_REQUEST
    });
    const user = getState().data.user.oid;    

    //const empresas = Mocker.generateEmpresas(quantity);

    fetch('http://localhost:8081/empresa/')
      .then(res => res.json())
      .then(empresas => {
        dispatch({
          type: types.INITIAL_DATA_SUCCESS,
          empresas
        });
      });

    fetch(`http://localhost:8081/indicador/${user}`)
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
    .then(res => {
      if (res.status !== 200) {
        showMessage('No se pudo obtener las metodologias')(dispatch);
        throw new Error();          
      }
      return res.json();
    })
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

      
    if (!indicadorId) {
      showMessage('Por favor seleccione un indicador')(dispatch);
      return;      
    } else if (!empresaId) {
      showMessage('Por favor seleccione una empresa')(dispatch);
      return;      
    } else if (!periodoId) {
      showMessage('Por favor seleccione un periodo')(dispatch);
      return;      
    } 
	
  
  
    fetch(`http://localhost:8081/indicador/evaluar/${indicadorId}/${empresaId}/${periodoId}`)
    .then(res => {
      if (res.status !== 200) {
        res.json().then(error => {
          if (error && error.message) {
            showMessage(error.message)(dispatch);          
          }
        });

        showMessage('No se pudo realizar el analisis')(dispatch);
        throw new Error();          
      }

      return res.json();
    })
      .then((resultado) => {
        dispatch({
          type: types.CALCULO_SUCCESS,
          resultado
        });
        clearInputs(type)(dispatch);
      });
  }

}

export function clearResult() {
  return (dispatch) => dispatch({type: 'CLEAR_RESULT'});
}

export function aplicarMetodologia(metodologia) {
  return (dispatch) => {
    dispatch({
      type: types.APLICAR_METODOLOGIA_REQUEST
    });
    dispatch(uiActions.selectMetodologia(metodologia))
	
	const metodologiaNombre = metodologia.nombre;
	
    fetch(`http://localhost:8081/metodologia/${metodologiaNombre}`)
    .then(res => {
      if (res.status !== 200) {
        showMessage('No se pudo aplicar la metodologia')(dispatch);
        throw new Error();          
      }
      return res.json();
    })
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
    //showSuccessOnSave()(dispatch);

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
  
    if (!empresaId) {
      showMessage('Por favor seleccione una empresa')(dispatch);
      return;      
    } else if (!periodoId) {
      showMessage('Por favor seleccione un periodo')(dispatch);
      return;      
    } else if (!inputs.nombreCuenta) {
      showMessage('Por favor indique una descripcion de la cuenta')(dispatch);
      return;      
    } else if (!inputs.valorCuenta) {
      showMessage('Por favor indique el valor de la cuenta')(dispatch);
      return;      
    }
	
    const options = {
      method: 'PUT',
	  headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
      },
	  body: JSON.stringify(cuenta)
    };

    fetch(`http://localhost:8081/empresa/${periodoId}`, options)
      .then(res => {
        if (res.status !== 200) {
          showMessage('No se pudo cargar la cuenta')(dispatch);
          throw new Error();          
        }
        return res;
      })
      .then((res) => {
		if (res !== null){
			dispatch({
			  type: types.SAVE_CUENTA_SUCCESS,
			  cuenta: cuenta,
			  empresaId,
			  periodoId
			});
			clearInputs(type)(dispatch);
			showMessage('Se ha cargado la cuenta correctamente')(dispatch);
		}
      });
  }
}

export function cargarIndicador(type) {
  return (dispatch, getState) => (e) => {
    e.preventDefault();
    let indicador;
    let inputs = getState().ui.inputsValues[type];
    const user = getState().data.user.oid;

    dispatch({
      type: types.SAVE_INDICADOR_REQUEST
    });


    indicador = {
      nombre: inputs.nombreIndicador,
      expression: inputs.expresionIndicador
    };

    if (!indicador.nombre) {
      showMessage('Por favor indique nombre del indicador')(dispatch);
      return;      
    } else if (!indicador.expression) {
      showMessage('Por favor ingrese una expresión para el indicador')(dispatch);
      return;      
    } 

    const options = {
      method: 'POST',
      body: JSON.stringify(indicador),
	  headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
      }
    };

    fetch(`http://localhost:8081/indicador/${user}`, options)
      .then((res) => {
        if (res.status === 500) {
          showMessage('El indicador provisto es inválido')(dispatch);
          throw new Error();
        }
        return res.json();
      })
    .then((indicadorRes) => {
      dispatch({
        type: types.SAVE_INDICADOR_SUCCESS,
        indicadorRes
      });
      clearInputs(type)(dispatch);
      showMessage('Se ha cargado el indicador correctamente')(dispatch);
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
    showMessage('Indicador borrado con exito')(dispatch);

  }
}


export function login(e) {
  return (dispatch, getState) => {
    e.preventDefault();
    const username = getState().ui.inputsValues.login.username;
    const password = getState().ui.inputsValues.login.password;
    
    fetch(`http://localhost:8081/usuario/login/${username}/${password}`)
    .then(res => {
      if (res.status !== 200) {
        showMessage('Usuario/contraseña incorrectos')(dispatch);   
        throw new Error();     
      }
      return res.json();
    })
    .then(user => {
      if(user !== null){
        dispatch({type: 'LOGIN_SUCCESS', user});
      }
    })
  }
}