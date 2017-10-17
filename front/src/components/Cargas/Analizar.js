import React from 'react';
import {Link, withRouter} from 'react-router';
import './CargaBase.scss';

class Analizar extends React.Component {
    constructor(props) {

        super(props)
        this.goBack = this.goBack.bind(this);
    }

    goBack(e) {
        e.preventDefault();
        this.props.dataActions.clearResult();                
    }

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
                        <div className="analizar-center">{this.props.resultado.resultado}</div>
                        <Link onClick={this.goBack}>Realizar nuevo calculo</Link>
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

export default withRouter(Analizar);