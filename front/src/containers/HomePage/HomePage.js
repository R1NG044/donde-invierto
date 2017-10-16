import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import ListaEmpresas from '../../components/ListaEmpresas/ListaEmpresas';
import DetalleEmpresa from '../../components/DetalleEmpresa/DetalleEmpresa';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import {periodosOrdenados} from '../../selectors/DataSelector';

class HomePage extends React.Component {
  componentWillMount() {
    if(this.props.withRanking) {
      this.props.uiActions.selectEmpresa(this.props.empresas[0]);
    }
  }

  render() {
    return (
      <div>
        <Col lg={2} md={3} sm={4} xs={6}>
          <ListaEmpresas empresas={this.props.empresas}
                          selectEmpresa={this.props.uiActions.selectEmpresa}
                          withRanking={this.props.withRanking}/>
        </Col>
        <Col lg={10} md={9} sm={8} xs={6}>
          <DetalleEmpresa empresa={this.props.empresaSelected} selected={this.props.periodoSelected} 
          selectPeriodo={this.props.uiActions.selectPeriodo} />
        </Col>

      </div>
    )
  }
}

function mapStateToProps(state, props) {
  return {
    empresaSelected: state.ui.empresaSelected,
    periodoSelected: state.ui.periodoSelected,
    empresas: periodosOrdenados(state, props.empresas)
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
)(HomePage);
