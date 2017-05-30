import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import {periodosOrdenados} from '../../selectors/DataSelector';
import {Link} from 'react-router';
import './Menu.scss';

const MenuComponent = (props) =>
  <Grid fluid className="Menu">
    <Col lg={6} lgOffset={3}
          md={6} mdOffset={3}
          sm={8} smOffset={2}
          xs={12}>
      <div className="super-gray-box">
        <div className="gray-box">
          <h1 className="header"> { props.title } </h1>
        </div>
        <div className="gray-box">
          <div className="links">

            {props.children}

          </div>
        </div>
      </div>
    </Col>

  </Grid>;


export default MenuComponent;
