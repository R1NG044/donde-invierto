import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import ListaMetodologias from '../../components/ListaMetodologias/ListaMetodologias';
import DetalleMetodologia from '../../components/DetalleMetodologia/DetalleMetodologia';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import {periodosOrdenados} from '../../selectors/DataSelector';
import {Link} from 'react-router';

const Metodologias = (props) =>
  <Grid fluid>
    {
      !props.metodologias.length ?
      <div>
        <h3>
          Actualmente no existen metodologias, por favor primero cree una
          <Link to="/cargar/metodologia"> aqui </Link>
        </h3>
      </div>
      :
      <div>
        <Col lg={2} md={2} sm={4} xs={12}>
          <ListaMetodologias metodologias={props.metodologias}
                            selectMetodologia={props.uiActions.selectMetodologia}/>
        </Col>
        <Col lg={10} md={10} sm={6} xs={12}>
          <DetalleMetodologia metodologia={props.metodologiaSelected} />
        </Col>
      </div>
    }

  </Grid>;



function mapStateToProps(state) {
  debugger;
  return {
    metodologiaSelected: state.ui.metodologiaSelected,
    metodologias: state.data.metodologias
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
)(Metodologias);
