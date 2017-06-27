import React, {PropTypes, Component} from 'react';
import {Grid} from 'react-bootstrap';
import {Link} from 'react-router';
import {LinkContainer} from 'react-router-bootstrap';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import CargaComponent from '../../components/Cargas/Carga';
import MenuComponent from '../../components/commons/MenuComponent';

class Carga extends Component {

  render() {
    const { data, uiActions, dataActions, inputsValues, empresas } = this.props;

    let elementToRender;
    switch(this.props.routeParams.type) {
      case 'newIndicador':
        let indicadoresProps = {
          uiActions,
          dataActions,
          inputsValues,
          empresas,
          type: this.props.routeParams.type
        };

        elementToRender = <CargaComponent.Indicadores {...indicadoresProps} />;
        break;

      case 'deleteIndicador':
        let deleteIndicadorProps = {
          uiActions,
          dataActions,
          inputsValues,
          type: this.props.routeParams.type
        };
        elementToRender = <CargaComponent.DeleteIndicadores {...deleteIndicadorProps} />;
        break;

      case 'cuenta':
        let cuentaProps = {
          empresas: data.empresas,
          uiActions,
          dataActions,
          inputsValues,
          type: this.props.routeParams.type
        };
        elementToRender = <CargaComponent.Cuenta {...cuentaProps} />;
        break;

      case 'metodologia':
        const type = this.props.routeParams.type;
        let metodologiasProps = {
          uiActions,
          dataActions,
          inputsValues,
          type
        };
        elementToRender = <CargaComponent.Metodologias {...metodologiasProps} />;
        break;

      default:
        this.props.router.push("/page-not-found");
    }

    return (
      <CargaComponent>
        {elementToRender}
      </CargaComponent>
    )
  }
}


Carga.propTypes = {
  children: PropTypes.object.isRequired
}

function mapStateToProps(state, props) {
  if(!state.ui.inputsValues[props.routeParams.type]) {
    console.error("No se encontro el atributo %s en initialState", props.routeParams.type);
  }

  return {
    data: state.data,
    inputsValues: state.ui.inputsValues[props.routeParams.type],
    empresas: state.data.empresas
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dataActions: bindActionCreators(dataActions, dispatch),
    uiActions: bindActionCreators(uiActions, dispatch)
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Carga);
