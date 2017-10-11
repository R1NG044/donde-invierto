import React from 'react';
import './CargaBase.scss';

export default class Analizar extends React.Component {

    render() {
        if(!this.props.empresas) {
            return <div>Cargando empresas...</div>
          }
      
          const {empresas, type, inputsValues, dataActions, uiActions, periodos, indicadores = []} = this.props;

        const empresaSelected = empresas
        .find(empresa => empresa.oid === parseInt(inputsValues.empresaSelected));

        return (
            <div className="Carga">
                {
                    this.props.resultado ?
                    <div>
                        <div>{this.props.resultado}</div>
                        <a href="/">Realizar nuevo calculo</a>
                    </div>
                    :
                    <form onSubmit={dataActions.realizarAnalisis(type)}>
                        <p className="description"> Seleccione Indicador: </p>
                        <select name="indicadorSelected"
                                onChange={uiActions.inputChanged(type)}>
                                <option selected disabled>Seleccione un Indicador:</option>

                            {
                            indicadores.map(indicador =>
                                <option value={indicador.oid}>{indicador.nombre}</option>
                            )
                            }
                        </select>
                        <p className="description"> Seleccione Empresa: </p>
                        <select name="empresaSelected"
                                onChange={uiActions.inputChanged(type)}>
                                <option selected disabled>Seleccione una empresa</option>

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
                        <input type="submit" value="Realizar Calculo" />
                    </form>
                }
            </div>
        )
    }
}