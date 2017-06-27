import React from 'react';
import './SelectMetodologia.scss';

export default class SelectMetodologia extends React.Component {
  render() {
    const { metodologias, dataActions } = this.props;
    return (
      <div className="SelectMetodologia">
        <h4>Seleccione la metodologia que desea aplicar</h4>
        <br />
        <select name="metodologiaSelected"
                onChange={ e => dataActions.aplicarMetodologia(metodologias[e.target.value])}>
          <option value="" disabled selected>Seleccione una metodologia</option>
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
