import React from 'react';

export default class Analizar extends React.Component {

    render() {
        return (
            <div>
                <form onSubmit={dataActions.cargarCuenta(type)}>
                    <p className="description"> Seleccione Empresa: </p>
                    <select name="empresaSelected"
                            onChange={uiActions.inputChanged(type)}>
                        {
                        empresas.map(empresa =>
                            <option value={empresa.oid}>{empresa.nombre}</option>
                        )
                        }
                    </select>
                    <p className="description"> Seleccione un periodo: </p>
                    <select name="periodoSelected"
                            onChange={uiActions.inputChanged(type)}>
                        <option selected disabled>Seleccione un periodo</option>
                        {
                        empresaSelected ?
                            empresaSelected.periodos.map(periodo =>
                            <option value={periodo.oid}>{periodo.nombre}</option>
                            )
                            :
                            <option value="undefined">Seleccione primero una empresa</option>
                        }
                    </select>
                </form>
            </div>
        )
    }
}