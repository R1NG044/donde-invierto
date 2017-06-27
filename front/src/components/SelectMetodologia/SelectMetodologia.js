import React from 'react';

export default class SelectMetodologia extends React.Component {
  render() {
    const { metodologias, dataActions } = this.props;
    return (
      <div>
        <h4>Seleccione la metodologia que desea aplicar</h4>
        <br />
        <select name="metodologiaSelected"
                onChange={ e => dataActions.aplicarMetodologia(metodologias[e.target.value])}>

          {
            metodologias &&
              metodologias.map((metodologia, index) =>
                <option value={index}>{metodologia.nombre}</option>
              )
          }
        </select>
      </div>
    )
  }
}
